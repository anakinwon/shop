package com.basic.basicjpa01.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity @Getter @Setter
@Table(name = "ORDERS")
@SequenceGenerator(   /** 개별 시퀀스 전략  */
        name = "ORDER_SEQ_GENERATOR",
        sequenceName = "ORDER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1)  // 시퀀스를 미리 가져오는 메커니즘
public class Order extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SEQ_GENERATOR")   /** 시퀀스 전략 */
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")      // FK가 있는 쪽이 연관관계 주인이 되어야 한다. 여기서 변경이 일어난다.
    @JsonManagedReference              // 순환참조 방지
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)   // cascade  Order 저장 시, OrderItem 도 같이 저장되는 옵션.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)  // EnumType은 반드시 STRING 만들 것...
    private OrderStatus status;   // 주문상태 [ORDER, CANCEL]


    /**
     * 연관관계 편의 매소드
     *
     * */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /**
     * 상품 주문  -  생성 메소드
     * */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem: orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    /**
     * 비지니스 로직
     * 1. 주문 취소
     * */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: orderItems) {
            orderItem.cancel();
        }
    }

    /**
     * 주문 및 취소 후 - 전체 주문가격 조회로직
     * */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}

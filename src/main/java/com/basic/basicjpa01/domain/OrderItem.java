package com.basic.basicjpa01.domain;

import com.basic.basicjpa01.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
public class OrderItem extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ORDER_ITEM_ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;  // 주문당시 가격
    private int count;       // 주문 수량


    // 무분별하게 생성하는 것을 막기 위해서 사용
    // @NoArgsConstructor(access = AccessLevel.PROTECTED) 어노테이션으로 대체 가능.
    //    protected OrderItem() {
    // }

    /**
     * 주문상품   -  생성 메소드
     * */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // 주문한 수량 만큼 재고수량을 빼준다.
        item.removeStock(count);
        return orderItem;
    }


    /** *
     *  취소 시 주문수량 만큼 재고를 원복한다.
     * */
    public void cancel() {
        getItem().addStock(count);
    }

    // 주문 전체 금액 = 상품 가격 * 주문 수량
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

}

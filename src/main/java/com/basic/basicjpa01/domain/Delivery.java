package com.basic.basicjpa01.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity  @Getter @Setter
@SequenceGenerator(   /** 개별 시퀀스 전략  */
        name = "DELIVERY_SEQ_GENERATOR",
        sequenceName = "DELIVERY_SEQ",         // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1)  // 시퀀스를 미리 가져오는 메커니즘
public class Delivery extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_SEQ_GENERATOR")   /** 시퀀스 전략 */
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)    // EnumType은 반드시 STRING 만들 것...
    private DeliveryStatus status;  // 배송상태 : READY, COMP;

}

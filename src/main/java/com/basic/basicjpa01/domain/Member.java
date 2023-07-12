package com.basic.basicjpa01.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@SequenceGenerator(   /** 개별 시퀀스 전략  */
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1)  // 시퀀스를 미리 가져오는 메커니즘
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")   /** 시퀀스 전략 */
    @Column(name="member_id")
    private Long id;

    @Column(name="name", nullable = false, columnDefinition = "varchar(100) default 'noname'")
    private String name;
    //    @JsonIgnore
    // 회원 조회 시 주소정보를 노출하고 싶지 않을 때 쓸 수 있는 옵션.
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")  // 나는 연관관계 주인이 아니라, 거울일뿐이다라는 의미로, 여기서는 변경이 일어나지 않는다.
    @JsonBackReference               // 순환참조 방지
    private List<Order> orders = new ArrayList<>();

}

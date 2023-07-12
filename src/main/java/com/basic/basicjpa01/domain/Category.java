package com.basic.basicjpa01.domain;

import com.basic.basicjpa01.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  순환관계(Recursive) 관계
 *  Parent
 *      ㄴ child
 *
 * */

@Entity @Getter @Setter
@SequenceGenerator(   /** 개별 시퀀스 전략  */
        name = "CATEGORY_SEQ_GENERATOR",
        sequenceName = "CATEGORY_SEQ",         // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1)  // 시퀀스를 미리 가져오는 메커니즘
public class Category extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_SEQ_GENERATOR")   /** 시퀀스 전략 */
    @Column(name="category_id")
    private Long id;
    private String name;

    // @ManyToMany 는 실무에서는 거의 사용하지 않지만
    // 예제로 만들어 봄.
    @ManyToMany
    @JoinTable(name="category_item"
            , joinColumns = @JoinColumn(name="category_id")
            , inverseJoinColumns= @JoinColumn(name="item_id")
    )
    private List<Item> items = new ArrayList<>();


    /**
     * 계층관계 표현방법
     * Parent <> child
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


    /**
     * 연관관계 편의 매소드
     *
     * */
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

}

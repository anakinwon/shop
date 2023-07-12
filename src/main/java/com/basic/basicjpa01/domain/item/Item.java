package com.basic.basicjpa01.domain.item;

import com.basic.basicjpa01.domain.BaseEntity;
import com.basic.basicjpa01.domain.Category;
import com.basic.basicjpa01.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@SequenceGenerator(   /** 개별 시퀀스 전략  */
        name = "ITEM_SEQ_GENERATOR",
        sequenceName = "ITEM_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1)  // 시퀀스를 미리 가져오는 메커니즘
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE")
public abstract class Item extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ_GENERATOR")   /** 시퀀스 전략 */
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;


    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**
     *  <비지니스 로직 처리>
     * 도메인 주도 설계 (DDD)
     *   - 엔티티 자체에서 해결할 수 있는 경우
     *   - 엔티티에 비지니스로직을 넣는 것이 좋다.
     *   -   예시 : 재고 수량 처리
     * */

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 취소 수량이 주문 수량보다 클 때 오류발생시킴.
     *
     * */
    public void removeStock(int quantity) {
        int restStock =this.stockQuantity - quantity;
        if (restStock<0) {
            throw new NotEnoughStockException("Not Enough Stocks~!");
        }
        this.stockQuantity = restStock;
    }
}

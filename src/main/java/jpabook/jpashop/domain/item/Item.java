package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)   //상속관계 설정?
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    //==비즈니스 로직==//
    //재고 수량 더하기
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
    //재고 수량 줄이기
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            //마이너스 재고 이슈 있으면 에러 터뜨리기
            throw new NotEnoughStockException("need more stock");
        }
        //마이너스 재고 이슈가 없으면 재고 줄이기
        this.stockQuantity = restStock;
    }

}

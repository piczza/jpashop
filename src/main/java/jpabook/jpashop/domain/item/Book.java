package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("B") //DB입장에서 구분할수있어야 해서 넣는 값
public class Book extends Item{

    private String artist;
    private String etc;
}

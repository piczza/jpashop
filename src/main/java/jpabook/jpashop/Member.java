package jpabook.jpashop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

//@Entity: JPA가 관리.
@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue //Id: 키를 할당할건데 GeneratedValue: 자동으로 할당함
    private Long id;
    private String username;
}

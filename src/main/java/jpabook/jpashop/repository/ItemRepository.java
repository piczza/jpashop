package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor //롬복. 생성자 주입을 임의의 코드 없이 자동 주입 해줌.
public class ItemRepository {
    private final EntityManager em;
    public void save(Item item) {
        if (item.getId() == null) {
            //새 상품이면 추가
            em.persist(item);
        } else {
            //이미 있는 상품이면 업데이트
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
    public List<Item> findAll() {
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }
}

package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @PersistenceContext//EntityManager를 빈으로 ㅈ주입할 때 사용.
    private EntityManager em;
    public Long save(Member member) {
        em.persist(member); //persist: 영속성 컨테스트에 저장?
        return member.getId();
    }
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}

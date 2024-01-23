package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /** 주문 */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);
        //주문상품 생성
        /*
            아래와같이 생성하지 않고, createOrderItem메서드 방식으로 생성이 통일될수 있게 할것.
            OrderItem orderItem = new OrderItem();
                -> OrderItem클래스에, protected OrderItem(){ } 형식으로 닫아둘것.
                -> OrderItem클래스에, @NoArgsConstructor(access = AccessLevel.PROTECTED) 도 동일한 효과
         */
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        //주문 저장
        /*
            원래라면 딜리버리 리파지토리도 제작해서 거기에 넣어줘야하지만,
            Order클래스의 Delivery 필드에 걸린 cascade옵션때문에 그냥 이렇게 함.
            cascade옵션: 오더를 퍼시스트 하면 오더 아래의 cascade까지 같이 전부 퍼시스트로 날려줌.
            사용 상황: 한군데서만 클래스를 참조할 때, 그 곳에 cascade를 걸어서 사용.
                ->라이프사이클상 동일하게 관리해주는게 의미가 있을 때 사용.

         */
         orderRepository.save(order);

         return order.getId();
    }

    /** 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {
        /*
            원래라면 데이터 수정 있을때마다 일일히 쿼리 날려야 하지만,
            JPA를 활용하면, 엔티티만 수정하면 됨. JPA가 다 해줌.
                ->더티체킹: 변경내역을 감지
                ->디비에 업데이트 쿼리를 날려줌!
         */
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    /** 주문 검색 */
    //public List<Order> findOrders(OrderSearch orderSearch) {
    //return orderRepository.findAll(orderSearch);
    //}

}

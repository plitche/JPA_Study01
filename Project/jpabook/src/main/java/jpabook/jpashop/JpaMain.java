package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        // xml 파일에서 설정한 name
        // emf는 딱 한번만 생성한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello2");

        // 클라이언트가 특정 액션을 할때마다 생성해준다. close로 버리기. => 쓰레드간에 공유X
        EntityManager em = emf.createEntityManager();
        // JPA에서 모든 데이터를 변경하는 작업은 무조건 트랜젝션 필요
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Order order = em.find(Order.class, 1L);
//            Long memberId = order.getMemberId();
//            Member member = em.find(Member.class, memberId);

            Member findMember = order.getMember();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {

        }

    }

}

package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain1 {

    public static void main(String[] args) {
        // xml 파일에서 설정한 name
        // emf는 딱 한번만 생성한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 클라이언트가 특정 액션을 할때마다 생성해준다. close로 버리기. => 쓰레드간에 공유X
        EntityManager em = emf.createEntityManager();
        // JPA에서 모든 데이터를 변경하는 작업은 무조건 트랜젝션 필요
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 8강
            // 비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            // 영속
            System.out.println("=== BEFORE ====");
            em.persist(member);
            System.out.println("=== AFTER ====");

            // 1차 캐시에서 조회하기 때문에 select 쿼리를 날리지 않고 정보를 가져온다.
            Member findMember1 = em.find(Member.class, 101L);
            System.out.println("findMember1.id = " + findMember1.getId());
            System.out.println("findMember1.Name = " + findMember1.getName());

            // 1차 캐시에 없기 때문에 db를 갔다가 1차캐시에 저장 후 가져온다.
            Member findMember2 = em.find(Member.class, 1L);
            System.out.println("findMember2.id = " + findMember2.getId());
            System.out.println("findMember2.Name = " + findMember2.getName());

            // 영속 엔티티의 동일성 보장
            Member findMember3 = em.find(Member.class, 1L);
            Member findMember4 = em.find(Member.class, 1L);

            System.out.println("result = " + (findMember3 == findMember4));

            // 영속
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);
            System.out.println("======================="); // persist하는 순간이 아니라 commit순간에 쿼리가 나감

            // 엔티티 수정
            Member member3 = em.find(Member.class, 150L);
            member3.setName("ZZZZZ");
            // em.persist(member3); : 다시 호출 할 필요가 없다. 컬렉션처럼 생각하라.

/*
            if (member.getName().equals("ZZZZZ")) {
                em.persist(member3);
            }
*/

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

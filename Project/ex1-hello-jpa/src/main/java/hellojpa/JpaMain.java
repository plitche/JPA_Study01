package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

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
            /* 생성 */
            // Member member = new Member();
            // member.setId(2L); // 무슨 값?
            // member.setName("HelloB");
            // em.persist(member);

            /* 조회 (타입, 값)*/
            Member findMember = em.find(Member.class, 1L);

            /* 수정 */
            findMember.setName("HelloJPA");
            // 이 작업 이후에 찾은 맴버를 다시 저장할 필요가 없다. 자바 컬렉션을 다루는 것 처럼 동작하기 때문에
            // jpa를 통해서 entity를 find해오면 관리하기 시작한다. 후에 commit 하는 순간 변경이 되었는지 안되었는지 체크한다.

            /* 삭제 */
            // em.remove(findMember);

            /* JPQL (query, type)*/
            List<Member> result = em.createQuery("select m from Parent as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();
            // 조회하는 대상이 테이블이 아니라 객체이다. : member entity를 선택을 한 것이다 라고 보면 된다.
            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            // =============================================================
            // 비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            // 영속
            System.out.println("=== BEFORE ====");
            em.persist(member);
            System.out.println("=== AFTER ====");

            // 준영속
            em.detach(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

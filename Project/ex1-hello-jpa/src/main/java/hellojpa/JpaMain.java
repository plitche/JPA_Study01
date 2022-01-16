package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        // xml 파일에서 설정한 name
        // emf는 딱 한번만 생성한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 클라이언트가 특정 액션을 할때마다 생성해준다.
        EntityManager em = emf.createEntityManager();
        //code
        Member member = new Member();
        member.setId(1L);
        member.setName("HelloA");
        em.persist(member);


        em.close();

        emf.close();

    }
}

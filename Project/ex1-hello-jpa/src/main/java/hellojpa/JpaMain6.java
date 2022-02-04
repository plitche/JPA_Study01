package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain6 {

    public static void main(String[] args) {
        EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf1.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 19. 일대다 1:N
            Member3 member3 = new Member3();
            member3.setUsername("member1");

            em.persist(member3);

            Team1 team1 = new Team1();
            team1.setName("teamA");
            team1.getMembers().add(member3);

            em.persist(team1);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf1.close();
    }
}

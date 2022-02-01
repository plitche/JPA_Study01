package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain4 {

    public static void main(String[] args) {
        EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf1.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member2 member2 = new Member2();
            member2.setUsername("member1");
//            member2.setTeamId(team.getId());
            member2.setTeam(team); // jpa가 알아서 pk값을 꺼내서 fk값을 사용한다.

            em.persist(member2);
            
            em.flush(); // 영속성 컨텍스트 비우기
            em.close(); // 초기화

            Member2 findMember = em.find(Member2.class, member2.getId());
//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class, findTeamId);
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());

            // ================== 16강 start ==================

            List<Member2> members = findMember.getTeam().getMembers();
            for (Member2 m : members) {
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf1.close();
    }
}

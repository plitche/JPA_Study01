package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain5 {

    public static void main(String[] args) {
        EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf1.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //저장
            Team team = new Team();
            team.setName("TeamA");
            // 양방향 매핑시 연관관계의 주인이 아니기 때문에 insert, update시에는 jpa가 보지 않는다.
            // team.getMembers().add(member2);
            em.persist(team);

            Member2 member2 = new Member2();
            member2.setUsername("member1");
            // member2.setTeam(team); : 로직이 없을 떄
            member2.changeTeam(team); // : 로직이 있을 때
            em.persist(member2);

            // 연관관계 메소드는 야쪽에 다 있으면 문제가 있을 수 있기 때문에 하나만 정해서 써라
            // team.addMember(member2);

            // jpa 입장에서는 위 코드가 맞긴한데,
            // 양방향 매핑은 둘다 넣어주는게 사실 맞긴 하다. : 객체지향적으로 생각했을 때
            // team.getMembers().add(member2); // flush, clear를 안했을때 이걸 안하면 members가 안찍힘
            // 실수를 방지하기 위해서 Member2 클래스 자체에 세팅하라
            
            em.flush(); // 영속성 컨텍스트 비우기
            em.clear(); // 초기화

            //flush, close를 안하면 1차 캐시에 가지고 있는 걸 가져온다.
            Team findTeam = em.find(Team.class, team.getId());
            List<Member2> members = findTeam.getMembers();
            System.out.println("==============");
            for (Member2 m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("==============");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf1.close();
    }
}

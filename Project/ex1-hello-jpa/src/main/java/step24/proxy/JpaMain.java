package step24.proxy;

import hellojpa.Member;
import hellojpa.Member3;
import hellojpa.Team;
import hellojpa.Team1;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member3 member = em.find(Member3.class, 1L);
            printMemberAndTeam(member);
            printMember(member);

            tx.commit();

            Member3 member3 = new Member3();
            member3.setUsername("hello");

            em.persist(member3);

            em.flush();
            em.clear();

            Member3 findMember = em.find(Member3.class, member.getId());
            System.out.println("findMember.id = " + findMember.getId());

            // 처음 getReference를 했을때에는 프록시 객체를 가져오고
            // getName()을 통해서 호출할때 프록시가 영속성 컨텍스트에 요청하고 DB를 조회하여 Member객체를 참조한다.
            Member3 findMember2 = em.getReference(Member3.class, member.getId());
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.username = " + findMember.getUsername());

            // 프록시 인스턴스의 초기화 여부 확인
            emf.getPersistenceUnitUtil().isLoaded(findMember2);

            // 프록시 클래스 확인 방법
            findMember2.getClass().getName(); // 강제 호출

            // 프록시 강제 초기화
            org.hibernate.Hibernate.initialize(findMember2); // (JPA표준은 강제 초기화 없음)


        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void printMemberAndTeam(Member3 member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team1 team = member.getTeam1();
        System.out.println("team = " + team.getName());
    }

    // member만 조회하면 되는데 team까지 select 해버림
    // 이 문제를 해결하기 위해 지연로딩/프록시를 jpa에서 활용한다.
    private static void printMember(Member3 member) {
        String username = member.getUsername();
        System.out.println("username = " + username);
    }
}

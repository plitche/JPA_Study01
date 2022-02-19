package step24.proxy;

import hellojpa.Member;
import hellojpa.Member3;
import hellojpa.Team;
import hellojpa.Team1;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
        } catch (Exception e) {
            tx.rollback();
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

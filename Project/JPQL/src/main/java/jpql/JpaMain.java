package jpql;

import javax.persistence.*;
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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            member.setTeam(team);
            member.setType(MemberType.ADMIN);
            em.persist(member);

            em.flush();
            em.clear();

            // inner 는 생략 가능
            String query = "select m from Member m inner join m.team t";
            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            // outer는 생략 가능
            String query1 = "select m from Member m left outer join m.team t";
            List<Member> result1 = em.createQuery(query1, Member.class)
                    .getResultList();

            // 세타 조인
            String query2 = "select m from Member m, Team t where m.username = t.name";
            List<Member> result2 = em.createQuery(query2, Member.class)
                    .getResultList();
            System.out.println("result2.size = " + result2.size());


            String query3 = "select m from Member m left join m.team t on t.name = 'teamA'";
            List<Member> result3 = em.createQuery(query3, Member.class)
                    .getResultList();

            String query4 = "select m from Member m left join Team t on m.username = t.name";
            List<Member> result4 = em.createQuery(query4, Member.class)
                    .getResultList();

            String query5 = "select (select avg(m1.age) from Member m1) from Member m left join Team t on m.username = t.name";
            List<Member> result5 = em.createQuery(query5, Member.class)
                    .getResultList();

            String query6 = "select m.username, 'HELLO', TRUE From Member m where m.type = :userType";
            List<Object[]> result6 = em.createQuery(query6)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : result6) {
                System.out.println(objects[0]);
                System.out.println(objects[1]);
                System.out.println(objects[2]);
            }

            String query7 = "select m.username, 'HELLO', TRUE From Member m where m.type is not null";
            List<Object[]> result7 = em.createQuery(query7)
                    .getResultList();

            for (Object[] objects : result7) {
                System.out.println(objects[0]);
                System.out.println(objects[1]);
                System.out.println(objects[2]);
            }

            String query8 =
                    "select " +
                            "case when m.age <= 10 then '학생요금' " +
                            "     when m.age >= 60 then '경로요금' " +
                            "     else '일반요금' " +
                            "end " +
                            "from Member m";
            List<String> result8 = em.createQuery(query8, String.class)
                    .getResultList();

            for (String s : result8) {
                System.out.println("s = " + s);
            }

            String query9 = "select coalesce(m.username, '이름 없는 회원') as username from Member m";
            List<String> result9 = em.createQuery(query9, String.class)
                    .getResultList();

            for (String s : result8) {
                System.out.println("s = " + s);
            }

            String query10 = "select nullif(m.username, '관리자') as username from Member m";
            List<String> result10 = em.createQuery(query10, String.class)
                    .getResultList();

            for (String s : result8) {
                System.out.println("s = " + s);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}


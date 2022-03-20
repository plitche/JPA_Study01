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

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            // 반환 타입이 명확할 때 TypedQuery, 명확하지 않을 때 Query
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            Query query3 = em.createQuery("select m.username, m.age from Member m");

            List<Member> resultList = query1.getResultList();
            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }

            TypedQuery<Member> query4 = em.createQuery("select m from Member m where m.id = 10", Member.class);

            Member singleResult = query4.getSingleResult();
            System.out.println("singleResult = " + singleResult);

//            TypedQuery<Member> query5 = em.createQuery("select m from Member m where m.username = :username", Member.class);
//            query5.setParameter("username", "member1");
//            Member singleResult2 = query5.getSingleResult();
//            System.out.println("singleResult2 = " + singleResult2.getUsername());

            em.flush();
            em.clear();

            // 이 Member Entity들은 영속성 컨텍스트들에 의해 관리가 될까 안될까 ?
            // Entity 프로젝션을 하면 select 절에 의한 결과가 모두 관리 된다.
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            Member findMember = result.get(0);
            findMember.setAge(20);

            // join query가 나간다.
            List<Team> result2 = em.createQuery("select m.team from Member m", Team.class)
                    .getResultList();
            // 위 아래는 같은 jpql
            em.createQuery("select t from Member m join m.team t", Team.class).getResultList();

            // 임베디드 타입 프로젝션
            em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            // 스칼라 타입 프로젝션
            List resultList1 = em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();

            // 여러 값 가져오기
            // 1
            Object o = resultList1.get(0);
            Object[] result3 = (Object[]) o;
            System.out.println("username = " + result3[0]);
            System.out.println("age = " + result3[1]);

            // 2
            List<Object[]> resultList2 = em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();

            Object[] result4 = resultList2.get(0);
            System.out.println("username = " + result4[0]);
            System.out.println("age = " + result4[1]);

            // 3 (권장)
            List<MemberDTO> resultList3 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = resultList3.get(0);
            System.out.println("username = " + memberDTO.getUsername());
            System.out.println("age = " + memberDTO.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

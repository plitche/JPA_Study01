package step33.valueTypeCollection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("old1", "street", "2000"));
            member.getAddressHistory().add(new Address("old2", "street", "3000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("============ START ============");
            Member findMember = em.find(Member.class, member.getId());
            // 이렇게 부를때 컬렉션들은 조회하지 않는다 : 지연 로딩
            // homeAddress는 member에 소속된 값 타입이기 때문에 같이 부른다.

            List<Address> addressHistory = findMember.getHomeAddress();
            for (Address address : addressHistory) {
                System.out.println("address = " + address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            tx.commit();

            // 값 타입 수정
            // findMember.getHomeAddress().setCity("newCity"); X
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new Address("old1", "street", "2000"));
            findMember.getAddressHistory().add(new Address("newCity1", "street", "2000"))

            member.getAddressHistory().add(new Address("old1", "street", "2000"));
            member.getAddressHistory().add(new Address("old2", "street", "3000"));
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

}

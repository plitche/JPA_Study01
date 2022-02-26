package step31.valueType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Address address = new Address("city", "street", "10000");

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAddress(address);
            em.persist(member1);

            // 대신에 값(인스턴스)를 복사해서 사용
            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAddress(copyAddress);
            em.persist(member2);

            // member1의 주소만 바꿧는데, member2도 바뀐다.
            // member1.getAddress().setCity("newCity"); // 불변 객체로 만들어서 set을 다 지운다.

            // 그럼 값을 바꾸고 싶을 때는 어떻게 해야 할까??
            Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
            member1.setAddress(newAddress);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

}

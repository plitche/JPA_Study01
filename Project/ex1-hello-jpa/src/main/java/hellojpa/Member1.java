package hellojpa;

import javax.persistence.*;

@Entity
// @SequenceGenerator(name = "member1_seq_generator", sequenceName = "member1_seq")
//@TableGenerator(
//        name = "member1_seq_generator",
//        table = "my_sequences",
//        pkColumnValue = "member1_seq" ,allocationSize = 1
//)

@SequenceGenerator(
        name = "member_Seq_generator",
        sequenceName = "member_seq",
        initialValue = 1, allocationSize = 50
)
public class Member1 {

    @Id //pk가 뭔지 알려준다.
    // AUTO는 DB 방언에 맞춰서 자동으로
    // IDENTITY : 키본키 생성을 데이터베이스에 위임, 내가 기본 값을 세팅하면 안된다.
    // SEQUENCE : oracle에 시퀀스처럼
    // TABLE

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    //@GeneratedValue(strategy = GenerationType.TABLE, generator = "member1_seq_generator")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String username;

    public Member1(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

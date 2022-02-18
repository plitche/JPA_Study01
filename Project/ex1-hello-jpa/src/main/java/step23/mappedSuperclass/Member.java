package step23.mappedSuperclass;

import hellojpa.Locker;
import hellojpa.MemberProduct;
import hellojpa.Team1;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name="USERNAME")
    private String username;

    // 1:N에서도 양방향 매핑을 하고 싶을때 읽기 전용 매핑으로 만든다.
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team1 team1;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    // 다대다 관계에서는 컬랙션을 이용해서 중간 다리가 필요하다. but 쓰지마라
    /*
    @ManyToMany
    @JoinColumn(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<Product>();
    */

    @OneToMany(mappedBy = "member3")
    private List<MemberProduct> memberProducts = new ArrayList<MemberProduct>();

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

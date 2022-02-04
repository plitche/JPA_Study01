package hellojpa;

import javax.persistence.*;

@Entity
public class Member2 {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name="USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    // 이 둘의 관계가 뭔지 jpa에게 알려줘야 한다.
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); // 나자신의 인스턴스를 넣어준다. : 연관관계 편의 메소드
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); // 나자신의 인스턴스를 넣어준다. : 연관관계 편의 메소드
    }


}

package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER") // 관례에 따른 것이 아니라, 특정 테이블을 지칭해줄때
public class Member {

    @Id //pk가 뭔지 알려준다.
    private Long id;
    // @Column(name = "username") 특정 컬럼에 저장 해줄 때
    @Column(name="name", unique = true, length = 10)
    private String name;

    public Member(){}

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

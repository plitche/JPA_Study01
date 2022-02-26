package step30.embeddedType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // 기간 Period
    @Embedded
    private Period period;

    // 주소 Address
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="city",
            column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name="street",
            column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name="zipcode",
            column = @Column(name = "WORK_ZIPCODE"))
    })
    private Address homeAddress;

    // 주소 Address
    @Embedded
    private Address workAddress;

    // 한 엔티티에서 같은 값 타입을 사용하면? @AttributeOverrides를 사용해야 한다.

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

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

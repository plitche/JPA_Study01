package step26.cascade;

import hellojpa.Locker;
import hellojpa.MemberProduct;
import hellojpa.Team1;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Child {

    @Id @GeneratedValue
    private Long id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

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

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}

package back.server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.ref.PhantomReference;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long PID;

    private String userName;

    private String userId;

    private String password;

    private String telephone;

    private String city;

    public void register(String userName, String userId, String password, String telephone, String city) {
        this.userName = userName;
        this.userId = userId;
        this.password = password;
        this.telephone = telephone;
        this.city = city;
    }
}

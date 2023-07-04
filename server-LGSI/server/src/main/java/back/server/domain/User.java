package back.server.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UID;

    private String userName;

    private String userId;

    private String password;

    private String telephone;

    private String city;

    @Builder
    public void register(String userName, String userId, String password, String telephone, String city) {
        this.userName = userName;
        this.userId = userId;
        this.password = password;
        this.telephone = telephone;
        this.city = city;
    }
}

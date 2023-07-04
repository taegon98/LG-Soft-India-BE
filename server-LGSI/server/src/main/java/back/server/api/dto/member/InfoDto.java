package back.server.api.dto.member;

import lombok.Getter;

@Getter
public class InfoDto {
    private String userName;
    private String userId;
    private String password;
    private String telephone;
    private String city;

    public void setPassword(String password) {
        this.password = password;
    }

    //테스트용
    public void set(String userName, String userId, String password, String telephone, String city) {
        this.userName = userName;
        this.userId = userId;
        this.password = password;
        this.telephone = telephone;
        this.city = city;
    }
}

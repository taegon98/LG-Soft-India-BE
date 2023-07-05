package back.server.api.dto.user;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
public class InfoDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String telephone;
    @NotBlank
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

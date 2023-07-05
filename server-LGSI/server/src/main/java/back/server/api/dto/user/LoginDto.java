package back.server.api.dto.user;

import lombok.Data;

@Data
public class LoginDto {
    private String userId;
    private String password;
}

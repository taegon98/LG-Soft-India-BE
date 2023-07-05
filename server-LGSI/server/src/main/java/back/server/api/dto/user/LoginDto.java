package back.server.api.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
}

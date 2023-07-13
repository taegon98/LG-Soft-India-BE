package back.server.api.dto.member;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class MemberLoginRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}

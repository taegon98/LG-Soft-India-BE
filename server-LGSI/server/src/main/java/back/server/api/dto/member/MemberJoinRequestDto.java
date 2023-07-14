package back.server.api.dto.member;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MemberJoinRequestDto {
    @NotBlank
    private String memberName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String cityName;
}

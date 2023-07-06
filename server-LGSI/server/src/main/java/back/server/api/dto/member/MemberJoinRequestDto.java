package back.server.api.dto.member;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class MemberJoinRequestDto {
    @NotBlank
    private String memberName;
    @NotBlank
    private String memberId;
    @NotBlank
    private String password;
    @NotBlank
    private String telephone;
    @NotBlank
    private String city;
}

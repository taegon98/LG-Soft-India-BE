package back.server.api.dto.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberEmail {
    @NotBlank
    private String email;
}

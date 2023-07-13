package back.server.api.dto.email;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class EmailRequestDto {
    @NotBlank
    private String email;
}

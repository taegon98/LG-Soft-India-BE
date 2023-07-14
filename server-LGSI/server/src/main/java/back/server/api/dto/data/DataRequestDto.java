package back.server.api.dto.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DataRequestDto {
    @NotBlank
    private String email;
}

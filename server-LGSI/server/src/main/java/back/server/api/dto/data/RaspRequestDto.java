package back.server.api.dto.data;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class RaspRequestDto {
    @NotBlank
    private String cityName;
    private String waterLevel;
    private String temperature;
    private String ph;
    private String turbidity;
}

package back.server.api.dto.raspberry;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class WaterLevelRequestDto {

    @NotBlank
    private String cityName;
    @NotBlank
    private String waterLevel;
}

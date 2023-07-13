package back.server.api.dto.raspberry;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class DataRequestDto {
    @NotBlank
    private String cityName;
    private String waterLevel;
    private String temperature;
    private String ph;
    private String turbidity;
}

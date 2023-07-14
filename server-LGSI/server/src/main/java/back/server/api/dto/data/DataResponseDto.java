package back.server.api.dto.data;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DataResponseDto {
    private LocalDateTime now;
    private Double waterLevel;
    private Double temperature;
    private Double ph;
    private Double turbidity;
}

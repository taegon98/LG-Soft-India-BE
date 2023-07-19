package back.server.domain.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@RedisHash(value = "token", timeToLive = 36000)
public class DataInfo {
    @Id
    private String cityName;
    private LocalDateTime now;
    private Double waterLevel;
    private Double temperature;
    private Double ph;
    private Double turbidity;
}

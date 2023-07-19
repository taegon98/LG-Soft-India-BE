package back.server.domain.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RedisHash(value = "email")
public class EmailInfo {
    @Id
    private String city;
    private List<String> email = new ArrayList<>();
    private long waterLevel_t;
    private long turbidity_t;

    public EmailInfo() {
        this.waterLevel_t = 0;
        this.turbidity_t = 0;
    }
}

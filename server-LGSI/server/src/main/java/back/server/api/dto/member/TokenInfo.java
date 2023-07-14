package back.server.api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@AllArgsConstructor
@RedisHash(value = "token", timeToLive = 3600)
public class TokenInfo {
    @Id
    private String email;
    private String accessToken;
    private String refreshToken;
}

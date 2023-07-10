package back.server.api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Builder
@Getter
@AllArgsConstructor
@RedisHash(value = "token", timeToLive = 3600)
public class TokenInfo {
    @Id
    private Long userId;
    private String grantType;
    private String accessToken;
    private String refreshToken;
    @TimeToLive
    private Integer expiration;
}

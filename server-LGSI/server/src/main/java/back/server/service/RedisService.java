package back.server.service;

import back.server.api.dto.member.TokenInfo;
import back.server.repository.TokenRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
    private final TokenRedisRepository tokenRedisRepository;

    //토큰 저장
    @Transactional
    public void saveToken(Long userId, TokenInfo token) {
        try {
            tokenRedisRepository.save(TokenInfo.builder()
                    .userId(userId)
                    .grantType(token.getGrantType())
                    .accessToken(token.getAccessToken())
                    .refreshToken(token.getRefreshToken())
                    .expiration(3600)
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // 토큰 조회
    public String getToken(Long userId) {
        try {
            return tokenRedisRepository.findById(userId)
                    .orElseThrow(IllegalArgumentException::new)
                    .getAccessToken();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    // 토큰 삭제
    @Transactional
    public void deleteToken(TokenInfo token) {
        try {
            tokenRedisRepository.delete(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}

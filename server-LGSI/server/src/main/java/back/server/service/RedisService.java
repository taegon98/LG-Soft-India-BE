package back.server.service;

import back.server.api.dto.data.DataInfo;
import back.server.api.dto.email.EmailInfo;
import back.server.api.dto.data.RaspRequestDto;
import back.server.api.dto.member.TokenInfo;
import back.server.api.dto.redis.Redis;
import back.server.domain.Member;
import back.server.repository.DataRedisRepository;
import back.server.repository.EmailRedisService;
import back.server.repository.TokenRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
    private final TokenRedisRepository tokenRedisRepository;

    private final DataRedisRepository dataRedisRepository;

    private final EmailRedisService emailRedisService;

    //지역별 이메일 캐시가 있는지 여부 확인
    public Redis checkCityName(String cityName) {
        Redis redis = new Redis();

        if (cityName.equals("Delhi")) cityName = "DEL";
        else if (cityName.equals("Bengaluru")) cityName = "BEN";

        redis.setCityName(cityName);

        if (emailRedisService.findById(cityName).isEmpty()) redis.setFlag(true);
        else redis.setFlag(false);

        return redis;
    }
    //지역별 이메일 저장
    @Transactional
    public void saveEmail(EmailInfo info) {
        try {
            emailRedisService.save(info);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    //토큰 저장
    @Transactional
    public void saveToken(TokenInfo token) {
        try {
            tokenRedisRepository.save(TokenInfo.builder()
                    .email(token.getEmail())
                    .accessToken(token.getAccessToken())
                    .refreshToken(token.getRefreshToken())
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // 토큰 조회
    public String getToken(String userId) {
        try {
            return tokenRedisRepository.findById(userId)
                    .orElseThrow(IllegalArgumentException::new)
                    .getAccessToken();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
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

    //센서값 저장
    @Transactional
    public void saveData(RaspRequestDto requestDto) {
        try {
            dataRedisRepository.save(DataInfo.builder()
                    .cityName(requestDto.getCityName())
                    .now(LocalDateTime.now())
                    .waterLevel(Double.valueOf(requestDto.getWaterLevel()))
                    .temperature(Double.valueOf(requestDto.getTemperature()))
                    .ph(Double.valueOf(requestDto.getPh()))
                    .turbidity(Double.valueOf(requestDto.getTurbidity()))
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    //센서값 조회
    public DataInfo getData(String cityName) {
        try {
            return dataRedisRepository.findById(cityName).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}

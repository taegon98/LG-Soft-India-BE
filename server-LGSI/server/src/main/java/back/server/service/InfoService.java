package back.server.service;

import back.server.api.dto.raspberry.WaterLevelRequestDto;
import back.server.domain.WaterLevel;
import back.server.repository.InfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InfoService {

    private final InfoRepository infoRepository;

    @Transactional
    public void saveInfo(WaterLevelRequestDto request) {
        log.info(request.getWaterLevel());
        WaterLevel waterLevel = WaterLevel.builder()
                .cityName(request.getCityName())
                .waterLevel(request.getWaterLevel())
                .build();
        try {
            infoRepository.save(waterLevel);
        }
        catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    public WaterLevel getInfo(Long cityId) {
        return infoRepository.findById(cityId).get();
    }
}

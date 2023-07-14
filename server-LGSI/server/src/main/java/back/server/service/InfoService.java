package back.server.service;

import back.server.api.dto.data.RaspRequestDto;
import back.server.domain.DataSet;
import back.server.repository.InfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InfoService {

    private final InfoRepository infoRepository;

    @Transactional
    public void saveInfo(RaspRequestDto request) {
        DataSet dataSet = DataSet.builder()
                .cityName(request.getCityName())
                .waterLevel(request.getWaterLevel())
                .temperature(request.getTemperature())
                .ph(request.getPh())
                .turbidity(request.getTurbidity())
                .build();
        try {
            infoRepository.save(dataSet);
        }
        catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}

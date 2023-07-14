package back.server.service;

import back.server.api.dto.data.RaspRequestDto;
import back.server.domain.DataSet;
import back.server.repository.DataSetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataSetService {

    private final DataSetRepository dataSetRepository;

    @Transactional
    public void updateData(RaspRequestDto request) {
        DataSet curData = dataSetRepository.findByCityName(request.getCityName());
        curData.update(request);
        try {
            dataSetRepository.save(curData);
        }
        catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}

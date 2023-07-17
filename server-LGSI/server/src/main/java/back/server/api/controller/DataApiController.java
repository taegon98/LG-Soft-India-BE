package back.server.api.controller;

import back.server.api.dto.data.DataInfo;
import back.server.api.dto.data.RaspRequestDto;
import back.server.api.dto.data.DataResponseDto;
import back.server.domain.EmailMessage;
import back.server.domain.Member;
import back.server.exception.MethodArgumentNotValidException;
import back.server.repository.MemberRepository;
import back.server.service.EmailSenderService;
import back.server.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
@Slf4j
@CrossOrigin(origins = "*")
public class DataApiController {

    private final RedisService redisService;
    private final MemberRepository memberRepository;

    private final EmailSenderService emailSenderService;

    //private final DataSetService dataSetService;
    @PostMapping("/save")
    public ResponseEntity saveData(@RequestBody @Valid RaspRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException("Field Error");
        }

        if (Double.parseDouble(request.getWaterLevel()) >= 560 ||
                Double.parseDouble(request.getTemperature()) >= 28 ||
                Double.parseDouble(request.getTurbidity()) >= 3 ||
                (Double.parseDouble(request.getPh()) <= 5 || Double.parseDouble(request.getPh()) >= 9)) {

            String city = request.getCityName();
            List<Member> members = memberRepository.findByCityName(city);

            EmailMessage emailMessage = EmailMessage.builder()
                    .subject("TEST")
                    .message("TEST")
                    .build();
            log.info("1");
            for (Member member : members) {
                emailMessage.setTo(member.getEmail());
                emailSenderService.sendEmail(emailMessage);
            }
        }
        redisService.saveData(request);
        //dataSetService.updateData(request);
        return ResponseEntity.ok(200);
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<DataResponseDto> getData(@PathVariable String email)  {
       /**
        * 센서 값 불러오기(DB 사용)

       Member member = memberRepository.findByEmail(email).get();
        DataSet data = member.getCity().getDataSet();

        DataResponseDto response = DataResponseDto.builder()
                .now(LocalDateTime.now())
                .waterLevel(Double.parseDouble(data.getWaterLevel()))
                .temperature(Double.parseDouble(data.getTemperature()))
                .ph(Double.parseDouble(data.getPh()))
                .turbidity(Double.parseDouble(data.getTurbidity()))
                .build();
        **/

        Member member = memberRepository.findByEmail(email).get();
        String cityName = member.getCityName();
        DataInfo data = redisService.getData(cityName);
        DataResponseDto response = DataResponseDto.builder()
                .now(LocalDateTime.now())
                .waterLevel(data.getWaterLevel())
                .temperature(data.getTemperature())
                .ph(data.getPh())
                .turbidity(data.getTurbidity())
                .build();

        return ResponseEntity.ok(response);
    }
}

package back.server.api.controller;

import back.server.domain.redis.DataInfo;
import back.server.domain.redis.EmailInfo;
import back.server.api.dto.data.RaspRequestDto;
import back.server.api.dto.data.DataResponseDto;
import back.server.api.dto.email.EmailMessage;
import back.server.domain.redis.RedisInfo;
import back.server.domain.db.Member;
import back.server.exception.MethodArgumentNotValidException;
import back.server.repository.EmailRedisService;
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

    private final EmailRedisService emailRedisService;

    @PostMapping("/save")
    public ResponseEntity saveData(@RequestBody @Valid RaspRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException("Field Error");
        }

        List<Member> members;
        EmailInfo emails;

        RedisInfo redisInfo = redisService.checkCityName(request.getCityName());
        if (redisInfo.isFlag()) {//캐시가 없으면
            log.info("success");
            members = memberRepository.findByCityName(request.getCityName());

            emails = new EmailInfo();
            emails.setCity(redisInfo.getCityName());

            for (Member member : members) {
                emails.getEmail().add(member.getEmail());
            }

            redisService.saveEmail(emails);
        }
        else {
            emails = emailRedisService.findById(redisInfo.getCityName()).get();
        }

        /*if (Double.parseDouble(request.getWaterLevel()) >= 400 && emails.getWaterLevel_t() % 10 == 0) {
            EmailMessage emailMessage = EmailMessage.builder()
                    .subject("WATER_LEVEL WARNING\n")
                    .message("Warning !\n" +
                             "The water level is too high.\n" +
                             "Please evacuate to a safe area due to the risk of flooding.\n" +
                             "Water Level : " + request.getWaterLevel())
                    .build();

            for (String email : emails.getEmail()) {
                emailMessage.setTo(email);
                emailSenderService.sendEmail(emailMessage);
            }
            redisService.addTime(emails, 1);
        }*/


        if (Double.parseDouble(request.getTurbidity()) >= 4.0 && emails.getTurbidity_t() % 10 == 0) {

            EmailMessage emailMessage = EmailMessage.builder()
                    .subject("TURBIDITY WARNING")
                    .message("Warning !\n" +
                             "The water turbidity is too high.\n" +
                             "The water may be contaminated, so it is recommended to drink purified water\n" +
                             "Turbidity : " + request.getTurbidity())
                    .build();

            for (String email : emails.getEmail()) {
                emailMessage.setTo(email);
                emailSenderService.sendEmail(emailMessage);
            }
            redisService.addTime(emails, 2);
        }

        redisService.saveData(request);
        return ResponseEntity.ok(200);
    }

    @GetMapping("/get/{cityName}")
    public ResponseEntity<DataResponseDto> getData(@PathVariable String cityName)  {

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

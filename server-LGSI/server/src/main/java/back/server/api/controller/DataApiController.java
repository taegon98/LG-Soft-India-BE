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
        if (redisInfo.isFlag()) {
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



        if (Double.parseDouble(request.getWaterLevel()) >= 400 ||
                Double.parseDouble(request.getTurbidity()) >= 3) {

            EmailMessage emailMessage = EmailMessage.builder()
                    .subject("WARNING")
                    .message("HI BIVANSHU")
                    .build();

            for (String email : emails.getEmail()) {
                emailMessage.setTo(email);
                emailSenderService.sendEmail(emailMessage);
            }
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

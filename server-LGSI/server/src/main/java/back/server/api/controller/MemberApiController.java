package back.server.api.controller;

import back.server.api.dto.ResponseDto;
import back.server.api.dto.email.EmailInfo;
import back.server.api.dto.member.MemberEmail;
import back.server.api.dto.member.MemberJoinRequestDto;
import back.server.api.dto.member.MemberLoginRequestDto;
import back.server.api.dto.member.TokenInfo;
import back.server.api.dto.redis.Redis;
import back.server.domain.City;
import back.server.domain.Member;
import back.server.repository.CityRepository;
import back.server.repository.EmailRedisService;
import back.server.service.MemberService;
import back.server.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
@CrossOrigin(origins = "*")
public class MemberApiController {

    private final MemberService memberService;
    private final CityRepository cityRepository;
    private final RedisService redisService;

    private final EmailRedisService emailRedisService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody @Valid MemberJoinRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ResponseDto("Field Error"));
        }

        City findCity = cityRepository.findCityByCityName(dto.getCityName());

        Member member = Member.builder()
                .memberName(dto.getMemberName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .cityName(dto.getCityName())
                .city(findCity)
                .roles(Collections.singletonList("USER"))
                .build();

        Redis redis = redisService.checkCityName(member.getCityName());

        if (redis.isFlag()) {
            EmailInfo emailInfo = new EmailInfo();
            emailInfo.setCity(redis.getCityName());

            emailInfo.getEmail().add(member.getEmail());
            redisService.saveEmail(emailInfo);
        }
        else if (!redis.isFlag()) {
            EmailInfo emailInfo = emailRedisService.findById(redis.getCityName()).get();
            emailInfo.getEmail().add(member.getEmail());
            redisService.saveEmail(emailInfo);
        }


        memberService.join(member);

        return ResponseEntity.ok().body(new ResponseDto("Sign Up Success"));
    }

    @PostMapping("/duplicate")
    public ResponseEntity<Object> duplicationCheck(@RequestBody @Valid MemberEmail email, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ResponseDto("Field Error"));
        }
        if (memberService.validateDuplicateUserId(email.getEmail())) {
            return ResponseEntity.ok().body(1);
        }
        else return ResponseEntity.badRequest().body(0);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Field Error");
        }

        String email = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.login(email, password);
        redisService.saveToken(tokenInfo); //토큰 정보 레디스에 저장
        return ResponseEntity.ok().body(tokenInfo);
    }
}

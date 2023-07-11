package back.server.api.controller;

import back.server.api.dto.ResponseDto;
import back.server.api.dto.member.MemberJoinRequestDto;
import back.server.api.dto.member.MemberLoginRequestDto;
import back.server.api.dto.member.TokenInfo;
import back.server.domain.City;
import back.server.domain.Member;
import back.server.exception.MethodArgumentNotValidException;
import back.server.repository.CityRepository;
import back.server.repository.MemberRepository;
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
@RequestMapping("/members")
@Slf4j
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final CityRepository cityRepository;
    private final RedisService redisService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody @Valid MemberJoinRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException("필드 값 오류");
        }

        //city 이름에 해당하는 city 객체 찾기
        City findCity = cityRepository.findCityByCityName(dto.getCityName());

        Member member = Member.builder()
                .memberName(dto.getMemberName())
                .memberId(dto.getMemberId())
                .password(dto.getPassword())
                .telephone(dto.getTelephone())
                .cityName(dto.getCityName())
                .city(findCity)
                .roles(Collections.singletonList("USER"))
                .build();

        memberService.join(member);

        return ResponseEntity.ok().body(new ResponseDto("success"));
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId, password);
        redisService.saveToken(memberRepository.findByMemberId(memberId).get().getUID(), tokenInfo); //토큰 정보 레디스에 저장
        return tokenInfo;
    }

    @PostMapping("/test")
    public String test() {
        return redisService.getToken(2L);
    }
}

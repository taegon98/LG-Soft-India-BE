package back.server.api.controller;

import back.server.api.dto.ResponseDto;
import back.server.api.dto.member.MemberJoinRequestDto;
import back.server.api.dto.member.MemberLoginRequestDto;
import back.server.api.dto.member.TokenInfo;
import back.server.domain.Member;
import back.server.exception.MethodArgumentNotValidException;
import back.server.service.MemberService;
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
@CrossOrigin
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody @Valid MemberJoinRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException("필드 값 오류");
        }

        Member member = Member.builder()
                .memberName(dto.getMemberName())
                .memberId(dto.getMemberId())
                .password(dto.getPassword())
                .telephone(dto.getTelephone())
                .city(dto.getCity())
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
        return tokenInfo;
    }

    @PostMapping("/test")
    public String test() {
        return "success!";
    }
}

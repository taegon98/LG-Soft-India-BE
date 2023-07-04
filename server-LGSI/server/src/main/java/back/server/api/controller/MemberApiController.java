package back.server.api.controller;

import back.server.api.dto.ResponseDto;
import back.server.api.dto.member.InfoDto;
import back.server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody InfoDto dto, BindingResult bindingResult) {
        memberService.join(dto);
        return ResponseEntity.ok(new ResponseDto("회원가입이 완료되었습니다."));
    }
}

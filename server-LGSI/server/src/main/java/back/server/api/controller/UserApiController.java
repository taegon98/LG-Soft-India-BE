package back.server.api.controller;

import back.server.api.dto.ResponseDto;
import back.server.api.dto.user.InfoDto;
import back.server.service.UserService;
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
public class UserApiController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody InfoDto dto, BindingResult bindingResult) {
        userService.join(dto);
        return ResponseEntity.ok(new ResponseDto("회원가입이 완료되었습니다."));
    }
}

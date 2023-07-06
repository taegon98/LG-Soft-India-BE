package back.server.api.controller;

import back.server.api.dto.ResponseDto;
import back.server.api.dto.user.InfoDto;
import back.server.api.dto.user.LoginDto;
import back.server.domain.User;
import back.server.exception.MethodArgumentNotValidException;
import back.server.exception.VerifyException;
import back.server.repository.UserRepository;
import back.server.security.JwtTokenProvider;
import back.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin
public class UserApiController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody @Valid InfoDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException("필드 값 오류");
        }
        userService.join(dto);

        return ResponseEntity.ok().body(new ResponseDto("success"));
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto dto) {
        User user;
        boolean ok = userRepository.existsByUserId(dto.getUserId());
        if (ok) {
            user = userRepository.findByUserId(dto.getUserId());
        }
        else {
            throw new VerifyException("가입되지 않은 아이디 입니다.");
        }

       if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
           throw new VerifyException("잘못된 비밀번호 입니다.");
       }
       return jwtTokenProvider.createToken(dto.getUserId(), "USER");
    }
}

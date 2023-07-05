package back.server.api.controller;

import back.server.api.dto.ResponseDto;
import back.server.api.dto.user.InfoDto;
import back.server.domain.User;
import back.server.repository.UserRepository;
import back.server.security.JwtTokenProvider;
import back.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    final String NAME = "TEST";
    final String USERID = "TEST";
    final String PASSWORD = "TEST";
    final String TELEPHONE = "TEST";
    final String CITY = "TEST";
    final String TYPE = "USER";


    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody InfoDto dto, BindingResult bindingResult) {
        userService.join(dto);
        return ResponseEntity.ok(new ResponseDto("회원가입이 완료되었습니다."));
    }

    @PostMapping("/login")
    public String login(@RequestBody InfoDto dto) {
       boolean member = userRepository.existsByUserId(dto.getUserId());
       if (member == false)
           throw new IllegalArgumentException("가입되지 않은 아이디 입니다.");
       return jwtTokenProvider.createToken(dto.getUserName(), "USER");
    }
    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}

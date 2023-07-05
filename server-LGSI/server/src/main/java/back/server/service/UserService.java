package back.server.service;

import back.server.api.dto.user.InfoDto;
import back.server.api.dto.user.LoginDto;
import back.server.domain.User;
import back.server.exception.ExistenceException;
import back.server.repository.UserRepository;
import back.server.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    //회원 가입
    public Long join(InfoDto dto) {
        validateDuplication(dto.getUserId());
        dto.setPassword(encoder.encode(dto.getPassword()));

        User user = User.builder()
                .userName(dto.getUserName())
                .userId(dto.getUserId())
                .password(dto.getPassword())
                .telephone(dto.getTelephone())
                .city(dto.getCity())
                .build();


        userRepository.save(user);

        return user.getUID();
    }

    //아이디 중복 검사
    private void validateDuplication(String userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new ExistenceException("동일한 아이디가 이미 존재 합니다.");
        }
    }

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User findUser = userRepository.findByUserId(userId);

        if (findUser == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        else {
            return userRepository.findByUserId(userId);
        }
    }
}

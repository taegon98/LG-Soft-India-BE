package back.server.service;

import back.server.api.dto.user.InfoDto;
import back.server.domain.User;
import back.server.exception.ExistenceException;
import back.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //회원 가입
    public Long join(InfoDto dto) {
        validateDuplication(dto.getUserId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        dto.setPassword(encoder.encode(dto.getPassword()));

        User member = new User();
        member.register(dto.getUserName(), dto.getUserId(), dto.getPassword(), dto.getTelephone(), dto.getCity());
        userRepository.save(member);

        return member.getUID();
    }

    //아이디 중복 검사
    private void validateDuplication(String userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new ExistenceException("동일한 아이디가 이미 존재 합니다.");
        }
    }
}

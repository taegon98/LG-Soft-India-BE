package back.server.service;

import back.server.api.dto.member.InfoDto;
import back.server.domain.Member;
import back.server.exception.ExistenceException;
import back.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //회원 가입
    public Long join(InfoDto dto) {
        validateDuplication(dto.getUserId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        dto.setPassword(encoder.encode(dto.getPassword()));

        Member member = new Member();
        member.register(dto.getUserName(), dto.getUserId(), dto.getPassword(), dto.getTelephone(), dto.getCity());
        memberRepository.save(member);

        return member.getPID();
    }

    //아이디 중복 검사
    private void validateDuplication(String userId) {
        if (memberRepository.existsByUserId(userId)) {
            throw new ExistenceException("동일한 아이디가 이미 존재 합니다.");
        }
    }
}

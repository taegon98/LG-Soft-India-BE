package back.server.service;

import back.server.api.dto.member.TokenInfo;
import back.server.domain.Member;
import back.server.exception.ExistenceException;
import back.server.repository.MemberRepository;
import back.server.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @Transactional
    public Long join(Member member) {

        validateDuplicateUserId(member);
        memberRepository.save(member);

        return member.getUID();
    }

    //아이디 중복 검사
    private void validateDuplicateUserId(Member member) {
        Optional<Member> findMember = memberRepository.findByMemberId(member.getMemberId());
        if (!findMember.isEmpty()) {
            throw new ExistenceException("동일한 아이디가 이미 존재 합니다.");
        }
    }

    @Transactional
    public TokenInfo login(String memberId, String password) {
        // Login ID/PW -> Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
        // 사용자 비밀번호 체크
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }
}

package back.server.service;

import back.server.domain.redis.TokenInfo;
import back.server.domain.db.Member;
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
        memberRepository.save(member);
        return member.getUID();
    }

    //아이디 중복 검사
    public boolean validateDuplicateUserId(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if (!findMember.isEmpty()) {
            return false;
        }
        return true;
    }

    @Transactional
    public TokenInfo login(String email, String password) {
        // Login ID/PW -> Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        // 사용자 비밀번호 체크
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, email);

        return tokenInfo;
    }

}

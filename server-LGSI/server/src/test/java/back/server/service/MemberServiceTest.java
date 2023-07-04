package back.server.service;

import back.server.api.dto.member.InfoDto;
import back.server.domain.Member;
import back.server.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입")
    @Rollback(value = false)
    void 회원가입() {
        InfoDto member1 = new InfoDto();
        InfoDto member2 = new InfoDto();

        member1.set("test1", "test1", "test1", "test1", "test1");
        member2.set("test2", "test2", "test2", "test2", "test2");

        memberService.join(member1);
        memberService.join(member2);
    }
}
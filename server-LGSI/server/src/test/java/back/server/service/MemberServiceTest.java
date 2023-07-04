package back.server.service;

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
        Member member1 = new Member();
        Member member2 = new Member();

        member1.register("test1", "test1", "test1", "test1", "test1");
        member2.register("test2", "test2", "test2", "test2", "test2");

        memberService.join(member1);
        memberService.join(member2);
    }
}
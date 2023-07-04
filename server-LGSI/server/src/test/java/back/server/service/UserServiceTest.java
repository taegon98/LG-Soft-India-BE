package back.server.service;

import back.server.api.dto.user.InfoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원가입")
    @Rollback(value = false)
    void 회원가입() {
        InfoDto member1 = new InfoDto();
        InfoDto member2 = new InfoDto();

        member1.set("test1", "test1", "test1", "test1", "test1");
        member2.set("test2", "test2", "test2", "test2", "test2");

        userService.join(member1);
        userService.join(member2);
    }
}
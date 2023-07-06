package back.server.repository;

import back.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //아이디 중복 검사
    boolean existsByUserId(String userId);
    User findByUserId(String userId);
}

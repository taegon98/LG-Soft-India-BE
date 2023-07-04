package back.server.repository;

import back.server.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    //아이디 중복 검사
    boolean existsByUserId(String userId);
}

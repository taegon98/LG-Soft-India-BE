package back.server.repository;

import back.server.domain.redis.TokenInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRedisRepository extends CrudRepository<TokenInfo, String> {
}

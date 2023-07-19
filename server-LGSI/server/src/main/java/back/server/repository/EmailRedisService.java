package back.server.repository;

import back.server.domain.redis.EmailInfo;
import org.springframework.data.repository.CrudRepository;

public interface EmailRedisService extends CrudRepository<EmailInfo, String> {
}

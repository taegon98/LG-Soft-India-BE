package back.server.repository;

import back.server.api.dto.email.EmailInfo;
import org.springframework.data.repository.CrudRepository;

public interface EmailRedisService extends CrudRepository<EmailInfo, String> {
}

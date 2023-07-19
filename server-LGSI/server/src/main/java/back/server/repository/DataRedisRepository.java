package back.server.repository;

import back.server.domain.redis.DataInfo;
import org.springframework.data.repository.CrudRepository;

public interface DataRedisRepository extends CrudRepository<DataInfo, String> {
}

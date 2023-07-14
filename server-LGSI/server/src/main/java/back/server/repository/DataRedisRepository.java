package back.server.repository;

import back.server.api.dto.data.DataInfo;
import org.springframework.data.repository.CrudRepository;

public interface DataRedisRepository extends CrudRepository<DataInfo, String> {
}

package back.server.repository;

import back.server.domain.WaterLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoRepository extends JpaRepository<WaterLevel, Long> {
}

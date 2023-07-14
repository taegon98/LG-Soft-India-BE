package back.server.repository;

import back.server.domain.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSetRepository extends JpaRepository<DataSet, String> {
    DataSet findByCityName(String cityName);
}

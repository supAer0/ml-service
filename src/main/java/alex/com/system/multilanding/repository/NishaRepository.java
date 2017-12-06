package alex.com.system.multilanding.repository;

import alex.com.system.multilanding.model.Nisha;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NishaRepository extends CrudRepository<Nisha, Long> {
}
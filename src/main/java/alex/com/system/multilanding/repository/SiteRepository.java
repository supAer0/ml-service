package alex.com.system.multilanding.repository;

import alex.com.system.multilanding.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    Site findByUrl(String url);
    Site findByToken(String token);
}
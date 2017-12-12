package alex.com.system.multilanding.repository;

import alex.com.system.multilanding.model.Element;
import alex.com.system.multilanding.model.ElementValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementValueRepository extends JpaRepository<ElementValue, Long> {
    ElementValue findByElement(Element element);
}
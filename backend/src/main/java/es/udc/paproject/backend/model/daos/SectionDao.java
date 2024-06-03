package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Section;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SectionDao extends CrudRepository<Section, Long> {

    @Query("SELECT s FROM Section s WHERE s.pattern.id = :patternId")
    List<Section> findAllByPatternId(Long patternId);

}

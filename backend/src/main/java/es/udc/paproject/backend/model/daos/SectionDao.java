package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Section;
import org.springframework.data.repository.CrudRepository;

public interface SectionDao extends CrudRepository<Section, Long> {
}

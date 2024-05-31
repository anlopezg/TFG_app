package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Yarn;
import org.springframework.data.repository.CrudRepository;

public interface YarnDao extends CrudRepository<Yarn, Long> {
}

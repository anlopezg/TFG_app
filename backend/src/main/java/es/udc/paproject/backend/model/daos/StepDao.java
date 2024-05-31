package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Step;
import org.springframework.data.repository.CrudRepository;

public interface StepDao extends CrudRepository<Step, Long> {
}

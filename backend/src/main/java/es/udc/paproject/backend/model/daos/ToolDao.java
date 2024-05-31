package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Tool;
import org.springframework.data.repository.CrudRepository;

public interface ToolDao extends CrudRepository<Tool, Long > {
}

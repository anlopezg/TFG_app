package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Tool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ToolDao extends CrudRepository<Tool, Long > {

    @Query("SELECT t FROM Tool t WHERE t.pattern.id = :patternId")
    List<Tool> findAllByPatternId(Long patternId);
}

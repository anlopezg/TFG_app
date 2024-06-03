package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Yarn;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface YarnDao extends CrudRepository<Yarn, Long> {

    @Query("SELECT y FROM Yarn y WHERE y.pattern.id = :patternId")
    List<Yarn> findAllByPatternId(Long patternId);
}

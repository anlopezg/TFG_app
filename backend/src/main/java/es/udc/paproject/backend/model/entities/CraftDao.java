package es.udc.paproject.backend.model.entities;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface CraftDao extends CrudRepository<Craft, Long> {

    Optional<Craft> findByCraftName(String craftName);
}

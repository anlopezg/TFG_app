package es.udc.paproject.backend.model.daos;

import java.util.Optional;

import es.udc.paproject.backend.model.entities.Craft;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface CraftDao extends CrudRepository<Craft, Long>, ListPagingAndSortingRepository<Craft, Long> {

    Optional<Craft> findByCraftName(String craftName);
}

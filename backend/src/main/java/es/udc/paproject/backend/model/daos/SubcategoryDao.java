package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Subcategory;
import org.springframework.data.repository.CrudRepository;

public interface SubcategoryDao extends CrudRepository<Subcategory, Long>{
}

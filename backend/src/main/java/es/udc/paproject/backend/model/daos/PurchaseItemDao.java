package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.PurchaseItem;
import org.springframework.data.repository.CrudRepository;
public interface PurchaseItemDao extends CrudRepository<PurchaseItem, Long>{
}

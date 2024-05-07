package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.ShoppingCartItem;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartItemDao extends CrudRepository<ShoppingCartItem, Long>{
}

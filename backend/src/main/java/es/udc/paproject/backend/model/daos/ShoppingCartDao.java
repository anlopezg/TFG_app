package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartDao extends CrudRepository<ShoppingCart, Long>{
}

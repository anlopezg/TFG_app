package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.PurchaseItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PurchaseItemDao extends CrudRepository<PurchaseItem, Long>{

    //@Query("SELECT pi FROM PurchaseItem pi WHERE pi.product.id = :productId AND pi.purchase.user.id = :userId")
    Optional<PurchaseItem> findByProduct_IdAndPurchase_User_Id(Long productId, Long userId);
}

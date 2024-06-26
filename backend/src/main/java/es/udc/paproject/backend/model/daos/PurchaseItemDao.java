package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.PurchaseItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PurchaseItemDao extends CrudRepository<PurchaseItem, Long>{

    //@Query("SELECT pi FROM PurchaseItem pi WHERE pi.product.id = :productId AND pi.purchase.user.id = :userId")
    Optional<PurchaseItem> findByProduct_IdAndPurchase_User_Id(Long productId, Long userId);

    @Query("SELECT pi FROM PurchaseItem pi WHERE pi.purchase.user.id = :userId AND pi.product.id = :patternId")
    List<PurchaseItem> findByUserIdAndPatternId(@Param("userId") Long userId, @Param("patternId") Long patternId);

    @Query("SELECT pi FROM PurchaseItem pi WHERE pi.purchase.user.id = :userId")
    List<PurchaseItem> findByUserId(@Param("userId") Long userId);
}

package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Purchase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseDao extends CrudRepository<Purchase, Long>{
    Slice<Purchase> findByUserIdOrderByDateDesc(Long userId, Pageable pageable);
}

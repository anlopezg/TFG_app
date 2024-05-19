package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.Review;
import es.udc.paproject.backend.model.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

public interface ReviewDao extends CrudRepository<Review, Long> {

    Slice<Review> findByProductIdOrderByDateDesc(Long productId, Pageable pageable);

    Slice<Review> findByUserIdOrderByDateDesc(Long userId, Pageable pageable);

    boolean existsReviewByUserAndProduct(User user, Product product);
}

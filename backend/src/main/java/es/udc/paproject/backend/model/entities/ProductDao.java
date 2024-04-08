package es.udc.paproject.backend.model.entities;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductDao extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long>, CustomizedProductDao {

    Optional<Product> findByTitle(String title);

    @Query("SELECT p FROM Product p WHERE p.user.id = :userId ORDER BY p.creationDate DESC")
    Slice<Product> findAllByUserIdOrderByCreationDateDesc(Long userId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.user.id = :userId AND p.active=true ORDER BY p.creationDate DESC")
    Slice<Product> findAllByUserIdAndActiveOrderByCreationDateDesc(Long userId, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN FETCH p.user WHERE p.id = :productId AND p.active=true ORDER BY p.creationDate DESC")
    Optional<Product> findByIdAndActiveOrderByCreationDateDesc(Long productId);
}

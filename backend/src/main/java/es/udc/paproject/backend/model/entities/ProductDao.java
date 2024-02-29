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
}

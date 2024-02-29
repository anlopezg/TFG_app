package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PhysicalDao extends CrudRepository<Physical, Long>, PagingAndSortingRepository<Physical, Long> {

    @Query("SELECT p FROM Physical p WHERE p.user.id = :userId ORDER BY p.creationDate DESC")
    Slice<Physical> findAllByUserIdOrderByCreationDateDesc(Long userId, Pageable pageable);
}

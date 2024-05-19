package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Pattern;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface PatternDao extends CrudRepository<Pattern, Long>, PagingAndSortingRepository<Pattern, Long> {

    //@Query("SELECT p FROM Pattern p WHERE p.user.id = :userId ORDER BY p.creationDate DESC")
    Slice<Pattern> findByUser_IdOrderByCreationDateDesc(Long userId, Pageable pageable);
}

package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Pattern;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface PatternDao extends CrudRepository<Pattern, Long>, PagingAndSortingRepository<Pattern, Long> {

    //@Query("SELECT p FROM Pattern p WHERE p.user.id = :userId ORDER BY p.creationDate DESC")
    Slice<Pattern> findByUser_IdOrderByCreationDateDesc(Long userId, Pageable pageable);

    @Override
    Optional<Pattern> findById(Long patternId);

    @Query("SELECT p FROM Pattern p " +
            "LEFT JOIN FETCH p.tools t " +
            "LEFT JOIN FETCH p.yarns y " +
            "LEFT JOIN FETCH p.sections s " +
            "LEFT JOIN FETCH s.steps st " +
            "LEFT JOIN FETCH s.images si " +
            "WHERE p.id = :patternId")
    Optional<Pattern> findByIdWithDetails(@Param("patternId") Long patternId);
}

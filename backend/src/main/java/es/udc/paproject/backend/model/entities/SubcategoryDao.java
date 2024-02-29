package es.udc.paproject.backend.model.entities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubcategoryDao extends CrudRepository<Subcategory, Long>{

    @Query("SELECT s FROM Subcategory s WHERE s.category.id = :categoryId ORDER BY s.subcategoryName ASC")
    List<Subcategory> findByCategoryId(@Param("categoryId") Long categoryId);
}

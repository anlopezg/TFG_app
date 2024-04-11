package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Subcategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.Set;


public interface CategoryDao extends CrudRepository<Category, Long>, ListPagingAndSortingRepository<Category, Long> {

    @Query("SELECT c.subcategories FROM Category c WHERE c.id = ?1")
    Set<Subcategory> findSubcategoriesByCategoryId(Long categoryId);
}

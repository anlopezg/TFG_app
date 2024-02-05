package es.udc.paproject.backend.model.entities;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CategoryDao extends CrudRepository<Category, Long>{

    Optional<Category> findByCategoryName(String categoryName);
}

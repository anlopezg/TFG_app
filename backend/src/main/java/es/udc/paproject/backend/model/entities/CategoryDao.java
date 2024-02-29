package es.udc.paproject.backend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;


public interface CategoryDao extends CrudRepository<Category, Long>, ListPagingAndSortingRepository<Category, Long> {

}

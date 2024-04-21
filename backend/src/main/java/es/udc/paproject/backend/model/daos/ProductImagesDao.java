package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.ProductImages;
import org.springframework.data.repository.CrudRepository;

public interface ProductImagesDao extends CrudRepository<ProductImages, Long> {
}

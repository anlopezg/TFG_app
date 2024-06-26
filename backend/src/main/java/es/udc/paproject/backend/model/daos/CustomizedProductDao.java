package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Product;
import org.springframework.data.domain.Slice;

public interface CustomizedProductDao {

    //Slice<Product> find(Long categoryId, String keywords, int page, int size);

    Slice<Product> find(Long craftId, Long subcategoryId, String keywords, Class<?> productType, int page, int size);
}

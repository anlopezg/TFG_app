package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Slice;

public interface CustomizedProductDao {

    Slice<Product> find(Long categoryId, String keywords, int page, int size);
}

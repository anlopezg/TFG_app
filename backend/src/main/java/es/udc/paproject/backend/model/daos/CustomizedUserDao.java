package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.User;
import org.springframework.data.domain.Slice;

public interface CustomizedUserDao {

    Slice<User> findSellers(String username, int page, int size);
}

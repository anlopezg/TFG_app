package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Favorite;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteDao extends CrudRepository<Favorite, Long> {

    List<Favorite> findByUserAndLiked(User user, Boolean liked);
    Optional<Favorite> findByUserAndProduct(User user, Product product);

}

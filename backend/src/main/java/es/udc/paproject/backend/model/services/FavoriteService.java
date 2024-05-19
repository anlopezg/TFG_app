package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Favorite;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.OwnerOfProductException;

import java.util.List;
import java.util.Optional;

public interface FavoriteService {

    /**
     * A user marks a product as a favorite
     *
     * @param userId    The user's id
     * @param productId The product's id
     * @return
     * @throws InstanceNotFoundException  Product or User not found
     * @throws DuplicateInstanceException The product as already been marked as favorite by the user
     * @throws OwnerOfProductException    The user is the owner of the product
     */

    Favorite markAsFavoriteProduct(Long userId, Long productId) throws InstanceNotFoundException, DuplicateInstanceException, OwnerOfProductException;

    List<Product> getFavoriteProducts(Long userId) throws InstanceNotFoundException;

    Optional<Favorite> findFavoriteByUserAndProduct(Long userId, Long productId) throws InstanceNotFoundException;

    Favorite findFavoriteById(Long favoriteId) throws InstanceNotFoundException;

    void removeFavoriteProduct(Long userId, Long productId) throws InstanceNotFoundException;
}

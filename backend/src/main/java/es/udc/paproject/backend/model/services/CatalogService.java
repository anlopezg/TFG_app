package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.OwnerOfProductException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;

import java.util.List;
import java.util.Optional;

public interface CatalogService {


    /**
     *
     * @return All the crafts in alphabetical order
     */
    List<Craft> findAllCrafts();

    /**
     *
     * @return All the categories in alphabetical order and their subcategories
     */
    List<Category> findAllCategories();


    /**
     * Returns all active Products, that match the given criteria
     */
    Block<Product> findProducts(Long craftId, Long subcategoryId, String keywords, String productType, int page, int size);


    /**
     * Returns a specific Product that matches the productId
     */
    Product findProductById(Long productId) throws InstanceNotFoundException;

    void getProductType(Product product);

    /**
     * Returns all active products, whose owner matches the username.
     */
    Block<Product> findSellerProducts(String username, int page, int size) throws InstanceNotFoundException, UserNotSellerException;


    /**
     * Return all users with the seller role, whose username matches the given one.
     */
    Block<User> findSellers(String username, int page, int size);


    Favorite markAsFavoriteProduct(Long userId, Long productId) throws InstanceNotFoundException, DuplicateInstanceException, OwnerOfProductException;

    List<Product> getFavoriteProducts(Long userId) throws InstanceNotFoundException;

    Optional<Favorite> findFavoriteByUserAndProduct(Long userId, Long productId) throws InstanceNotFoundException;

    Favorite findFavoriteById(Long favoriteId) throws InstanceNotFoundException;

    void removeFavoriteProduct(Long userId, Long productId) throws InstanceNotFoundException;

}

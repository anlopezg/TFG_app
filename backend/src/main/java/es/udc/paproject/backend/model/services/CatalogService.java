package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Subcategory;
import es.udc.paproject.backend.model.entities.Craft;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;

import java.util.List;

public interface CatalogService {

    /**
     * Checks a craft exists by its id
     * @param craftId The craft id
     * @return The craft
     * @throws InstanceNotFoundException Craft not found
     */
    Craft checkCraft(Long craftId) throws InstanceNotFoundException;

    /**
     * Checks a category exists by its id
     * @param categoryId The category id
     * @return The category
     * @throws InstanceNotFoundException Category not found
     */
    Subcategory checkSubcategory(Long categoryId) throws InstanceNotFoundException;

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
     * Returns the class of the product type
     */
    Class<?> getProductTypeClass(String productType);


    /**
     * Returns all active Products, that match the given criteria
     */
    Block<Product> findProducts(Long craftId, Long subcategoryId, String keywords, String productType, int page, int size);


    /**
     * Returns a specific Product that matches the productId
     */
    Product findProduct(Long productId) throws InstanceNotFoundException;

    void getProductType(Product product);

    /**
     * Returns all active products, whose owner matches the username.
     */
    Block<Product> findSellerProducts(String username, int page, int size) throws InstanceNotFoundException, UserNotSellerException;


    /**
     * Return all users with the seller role, whose username matches the given one.
     */
    Block<User> findSellers(String username, int page, int size);

}

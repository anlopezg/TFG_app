package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
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
     *
     * @param categoryId The id of the category
     * @return All the subcategories that belong to the passed category
     */
    List<Subcategory> getSubcategoriesByCategory(Long categoryId);


    /**
     * Returns the class of the product type
     */
    Class<?> getProductTypeClass(String productType);

    /**
     * Returns all active Products, that match the given criteria
     */
    Block<Product> findProducts(Long craftId, Long subcategoryId, String keywords, String productType, int page, int size);


    /**
     * Returns all active products, whose owner matches the username
     */
    Block<Product> findUserProducts(String username, int page, int size) throws InstanceNotFoundException, UserNotSellerException;

    Product findProduct(Long productId) throws InstanceNotFoundException;
}

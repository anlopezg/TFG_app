package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;

import java.math.BigDecimal;
import java.util.List;

public interface PublicationService {

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


    Product addProduct(Product product) throws InstanceNotFoundException;

    /**
     *
     * Creates a new Product of type Pattern
     */
    Pattern createPattern(Long userId, Long craftId, Long subcategoryId, String title, String description,
                              BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                              String sizing, int difficultyLevel, String time)
            throws InstanceNotFoundException, UserNotSellerException;

    /*
    void createPatternProduct(Pattern pattern) throws InstanceNotFoundException;*/


    /**
     *
     * Creates a new Product of type Physical
     */
    Physical createPhysical(Long userId, Long craftId, Long subcategoryId, String title, String description,
                         BigDecimal price, Boolean active, int amount, String size, String color,
                         String details) throws InstanceNotFoundException,  UserNotSellerException;
    /*
    void createPhysicalProduct(Physical physical) throws InstanceNotFoundException;*/



    /**
     *
     * @return All the crafts
     */
    List<Craft> findAllCrafts();

    /**
     *
     * @return All the categories and their subcategories
     */
    List<Category> findAllCategories();

    /**
     *
     * @param categoryId The id of the category
     * @return All the subcategories that belong to the passed category
     */
    List<Subcategory> getSubcategoriesByCategory(Long categoryId);


    /**
     * Shows all the products a user has added to its store
     * @param userId
     * @param page
     * @param size
     * @return
     */
    Block<Product> findAddedProducts(Long userId, int page, int size);

    Block<Pattern> findAddedPatters(Long userId, int page, int size);

    Block<Physical> findAddedPhysicals(Long userId, int page, int size);

    Block<Product> findProducts(Long categoryId, String keywords, int page, int size);

    Product findProductById(Long productId) throws InstanceNotFoundException;

    Pattern findPatternById(Long productId) throws InstanceNotFoundException;

}

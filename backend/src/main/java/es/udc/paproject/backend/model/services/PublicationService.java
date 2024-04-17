package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Pattern;
import es.udc.paproject.backend.model.entities.Physical;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.UserNotOwnerException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;

import java.math.BigDecimal;
import java.util.List;

public interface PublicationService {


    /**
     * Checks whether the given user, is the same one who created the product, its the owner
     * @param userId The user's id
     * @param productId The product's id
     * @throws UserNotOwnerException The user is not the owner of the product
     */
    void checkProductOwner(Long userId, Long productId) throws UserNotOwnerException;

    /**
     * Creates a new Product of type Pattern given the following params
     * @throws InstanceNotFoundException User, Craft or Subcategory not found
     * @throws UserNotSellerException The user must be a Seller
     */
    Pattern createPattern(Long userId, Long craftId, Long subcategoryId, String title, String description,
                          BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                          String sizing, int difficultyLevel, String time,
                          String abbreviations, String specialAbbreviations, String tools)
            throws InstanceNotFoundException, UserNotSellerException;


    //Product uploadImages(Long productId, List<String> imagePaths) throws InstanceNotFoundException;


    /**
     * Creates a new Product of type Physical given the following params
     * @throws InstanceNotFoundException User, Craft or Subcategory not found
     * @throws UserNotSellerException The user must be a Seller
     */
    Physical createPhysical(Long userId, Long craftId, Long subcategoryId, String title, String description,
                            BigDecimal price, Boolean active, int amount, String size, String color,
                            String details) throws InstanceNotFoundException,  UserNotSellerException;




    Block<Product> findAddedProducts(Long userId, int page, int size) throws InstanceNotFoundException, UserNotSellerException;

    /**
     * Returns all the patterns a particular user has added to their store
     */
    Block<Pattern> findAddedPatterns(Long userId, int page, int size) throws InstanceNotFoundException, UserNotSellerException;

    /**
     * Returns all the physical products a particular user has added to their store
     */
    Block<Physical> findAddedPhysicals(Long userId, int page, int size) throws InstanceNotFoundException, UserNotSellerException;


    //Block<Product> findProducts(Long categoryId, String keywords, int page, int size);


    /************************ VIEW DETAILS OF PRODUCTS *************************/
    Product findProductById(Long productId) throws InstanceNotFoundException, UserNotOwnerException;
    Pattern findPatternById(Long userId, Long productId) throws InstanceNotFoundException, UserNotOwnerException;
    Physical findPhysicalById(Long userId, Long productId) throws InstanceNotFoundException, UserNotOwnerException;


    /************************ EDIT PRODUCTS *************************/
    Pattern editPattern(Long productId, Long userId, Long craftId, Long subcategoryId, String title, String description,
                        BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                        String sizing, int difficultyLevel, String time,
                        String abbreviations, String specialAbbreviations, String tools) throws InstanceNotFoundException, UserNotOwnerException;

    Physical editPhysical(Long productId, Long userId, Long craftId, Long subcategoryId, String title, String description,
                        BigDecimal price, Boolean active, int amount, String size, String color, String details) throws InstanceNotFoundException, UserNotOwnerException;


    /**
     * Deletes a given product
     * @param userId The user who ows the product
     * @param productId The product's id
     * @throws InstanceNotFoundException No product found
     * @throws UserNotOwnerException Given user is not the owner of the product
     */
    void deleteProduct(Long userId, Long productId) throws InstanceNotFoundException, UserNotOwnerException;

}

package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;

import java.math.BigDecimal;
import java.util.List;

public interface PublicationService {




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


    /**
     * Creates a new Product of type Physical given the following params
     * @throws InstanceNotFoundException User, Craft or Subcategory not found
     * @throws UserNotSellerException The user must be a Seller
     */
    Physical createPhysical(Long userId, Long craftId, Long subcategoryId, String title, String description,
                         BigDecimal price, Boolean active, int amount, String size, String color,
                         String details) throws InstanceNotFoundException,  UserNotSellerException;




    Block<Product> findAddedProducts(Long userId, int page, int size);

    /**
     * Returns all the patterns a particular user has added to their store
     */
    Block<Pattern> findAddedPatterns(Long userId, int page, int size) throws InstanceNotFoundException, UserNotSellerException;

    /**
     * Returns all the physical products a particular user has added to their store
     */
    Block<Physical> findAddedPhysicals(Long userId, int page, int size);


    Block<Product> findProducts(Long categoryId, String keywords, int page, int size);


    /************************ VIEW DETAILS OF PRODUCTS *************************/
    Product findProductById(Long productId) throws InstanceNotFoundException;
    Pattern findPatternById(Long productId) throws InstanceNotFoundException;
    Physical findPhysicalById(Long productId) throws InstanceNotFoundException;

    /************************ EDIT PRODUCTS *************************/
    Pattern editPattern(Long productId, Long userId, Long craftId, Long subcategoryId, String title, String description,
                        BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                        String sizing, int difficultyLevel, String time,
                        String abbreviations, String specialAbbreviations, String tools) throws InstanceNotFoundException;

    Physical editPhysical(Long productId, Long userId, Long craftId, Long subcategoryId, String title, String description,
                        BigDecimal price, Boolean active, int amount, String size, String color, String details) throws InstanceNotFoundException;


    /**
     * Deletes a product, whether it is a Pattern or a Physical product
     * @param productId The product's id
     * @throws InstanceNotFoundException No product found
     */
    void deleteProduct(Long productId) throws InstanceNotFoundException;

    //boolean isSellerTheOwner(Long userId, Long productId) throws InstanceNotFoundException;

}

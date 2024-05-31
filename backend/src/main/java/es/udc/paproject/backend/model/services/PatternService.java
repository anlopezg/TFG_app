package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Pattern;
import es.udc.paproject.backend.model.entities.Section;
import es.udc.paproject.backend.model.entities.Tool;
import es.udc.paproject.backend.model.entities.Yarn;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.exceptions.UserNotOwnerException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;

import java.math.BigDecimal;
import java.util.List;

public interface PatternService {

    /**
     * Creates a new Product of type Pattern given the following params
     * @throws InstanceNotFoundException User, Craft or Subcategory not found
     * @throws UserNotSellerException The user must be a Seller
     */
    Pattern createPattern(Long userId, Long craftId, Long subcategoryId, String title, String description,
                          BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                          String sizing, int difficultyLevel, String time,
                          String abbreviations, String specialAbbreviations,
                          List<String> imagesUrl)
            throws InstanceNotFoundException, UserNotSellerException;


    Pattern createPattern(Long userId, Long craftId, Long subcategoryId, String title, String description,
                          BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                          String sizing, int difficultyLevel, String time, String abbreviations, String specialAbbreviations,
                          List<String> imagesUrl, List<Tool> tools, List<Yarn> yarns, List<Section> sections)
            throws InstanceNotFoundException, UserNotSellerException;


    /**
     * Returns all the patterns a particular user has added to their store
     */
    Block<Pattern> findAddedPatterns(Long userId, int page, int size) throws InstanceNotFoundException, UserNotSellerException;


    Pattern findPatternById(Long userId, Long productId) throws InstanceNotFoundException, UserNotOwnerException, PermissionException;


    Pattern editPattern(Long productId, Long userId, Long craftId, Long subcategoryId, String title, String description,
                        BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                        String sizing, int difficultyLevel, String time,
                        String abbreviations, String specialAbbreviations, List<String> imagesUrl) throws InstanceNotFoundException, UserNotOwnerException, PermissionException;


    void deletePattern(Long userId, Long productId) throws InstanceNotFoundException, PermissionException;

}
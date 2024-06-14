package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Pattern;
import es.udc.paproject.backend.model.entities.Section;
import es.udc.paproject.backend.model.entities.Tool;
import es.udc.paproject.backend.model.entities.Yarn;
import es.udc.paproject.backend.model.exceptions.*;

import java.math.BigDecimal;
import java.util.List;

public interface PatternService {


    Pattern createPattern(Long userId, Long craftId, Long subcategoryId, String title, String description,
                          BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                          String sizing, int difficultyLevel, String time, String abbreviations,
                          String specialAbbreviations, String language,
                          List<String> imagesUrl, List<Tool> tools, List<Yarn> yarns, List<Section> sections)
            throws InstanceNotFoundException, UserNotSellerException, MaxItemsExceededException;


    /**
     * Returns all the patterns a particular user has added to their store
     */
    Block<Pattern> findAddedPatterns(Long userId, int page, int size) throws InstanceNotFoundException, UserNotSellerException;


    Pattern findPatternById(Long userId, Long productId) throws InstanceNotFoundException, UserNotOwnerException, PermissionException;




    Pattern editPattern(Long productId, Long userId, Long craftId, Long subcategoryId, String title, String description,
                        BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                        String sizing, int difficultyLevel, String time, String abbreviations,
                        String specialAbbreviations, String language,
                        List<String> imagesUrl, List<Tool> tools, List<Yarn> yarns, List<Section> sections)
            throws InstanceNotFoundException, PermissionException, MaxItemsExceededException;

    void deletePattern(Long userId, Long productId) throws InstanceNotFoundException, PermissionException;

    Pattern findPurchasedPatternById(Long userId, Long patternId) throws InstanceNotFoundException, PermissionException;

    List<Pattern> findPurchasedPatterns(Long userId) throws InstanceNotFoundException;

}

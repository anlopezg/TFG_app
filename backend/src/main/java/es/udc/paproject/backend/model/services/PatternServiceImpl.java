package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.daos.*;
import es.udc.paproject.backend.model.daos.SectionImagesDao;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PatternServiceImpl implements PatternService{

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PatternDao patternDao;

    @Autowired
    private ProductImagesDao productImagesDao;

    @Autowired
    private ToolDao toolDao;

    @Autowired
    private YarnDao yarnDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private StepDao stepDao;

    @Autowired
    private SectionImagesDao sectionImagesDao;



    @Override
    public Pattern createPattern(Long userId, Long craftId, Long subcategoryId, String title, String description,
                                 BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                                 String sizing, int difficultyLevel, String time, String abbreviations, String specialAbbreviations,
                                 List<String> imagesUrl)
            throws InstanceNotFoundException, UserNotSellerException {

        User user = permissionChecker.checkSellerUser(userId);
        Craft craft = permissionChecker.checkCraft(craftId);
        Subcategory subcategory = permissionChecker.checkSubcategory(subcategoryId);

        LocalDateTime creationDate = LocalDateTime.now();

        Pattern patternCreated = new Pattern(user, craft, subcategory,
                title, description, price, active, creationDate,introduction, notes, gauge, sizing, difficultyLevel, time, abbreviations, specialAbbreviations);

        productDao.save(patternCreated);

        for(String imageUrl : imagesUrl){
            ProductImages productImage = new ProductImages(patternCreated, imageUrl);
            patternCreated.addImage(productImage);
            productImagesDao.save(productImage);
        }


        return patternCreated;
    }

    @Override
    public Pattern createPattern(Long userId, Long craftId, Long subcategoryId, String title, String description,
                                 BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                                 String sizing, int difficultyLevel, String time, String abbreviations, String specialAbbreviations,
                                 List<String> imagesUrl, List<Tool> tools, List<Yarn> yarns, List<Section> sections)
            throws InstanceNotFoundException, UserNotSellerException {

        User user = permissionChecker.checkSellerUser(userId);
        Craft craft = permissionChecker.checkCraft(craftId);
        Subcategory subcategory = permissionChecker.checkSubcategory(subcategoryId);

        LocalDateTime creationDate = LocalDateTime.now();

        Pattern patternCreated = new Pattern(user, craft, subcategory,
                title, description, price, active, creationDate,introduction, notes, gauge, sizing, difficultyLevel, time, abbreviations, specialAbbreviations);

        productDao.save(patternCreated);

        // Add images
        for(String imageUrl : imagesUrl){
            ProductImages productImage = new ProductImages(patternCreated, imageUrl);
            patternCreated.addImage(productImage);
            productImagesDao.save(productImage);
        }

        for(Tool tool : tools){
            tool.setPattern(patternCreated);
            toolDao.save(tool);  // Save each tool
            patternCreated.getTools().add(tool);
        }

        // Add yarns
        for (Yarn yarn : yarns) {
            yarn.setPattern(patternCreated);
            yarnDao.save(yarn);  // Save each yarn
            patternCreated.getYarns().add(yarn);
        }

        // Add sections, and their steps and images
        for (Section section : sections) {
            section.setPattern(patternCreated);
            sectionDao.save(section);  // Save section first to generate its ID
            for (Step step : section.getSteps()) {
                step.setSection(section);
                stepDao.save(step);  // Save each step
            }
            for(SectionImages sectionImages : section.getImages()){
                sectionImages.setSection(section);
                sectionImagesDao.save(sectionImages);  // Save each section image
            }
            patternCreated.getSections().add(section);
        }

        return patternCreated;
    }

    @Override
    @Transactional(readOnly = true)
    public Block<Pattern> findAddedPatterns(Long userId, int page, int size) throws InstanceNotFoundException, UserNotSellerException {

        User user = permissionChecker.checkSellerUser(userId);

        Slice<Pattern> slice = patternDao.findByUser_IdOrderByCreationDateDesc(userId,  PageRequest.of(page, size));

        return new Block<>(slice.getContent(), slice.hasNext());
    }


    @Override
    public Pattern findPatternById(Long userId, Long patternId) throws InstanceNotFoundException, PermissionException {

        Optional<Pattern> pattern = patternDao.findByIdWithDetails(patternId);

        if(!pattern.isPresent()){
            throw new InstanceNotFoundException("project.entities.product", patternId);
        }

        permissionChecker.checkProductExistsAndBelongsTo(patternId, userId);

        return pattern.get();
    }

    @Override
    public Pattern editPattern(Long productId, Long userId, Long craftId, Long subcategoryId, String title, String description,
                               BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                               String sizing, int difficultyLevel, String time, String abbreviations, String specialAbbreviations,
                               List<String> imagesUrl) throws InstanceNotFoundException, PermissionException {


        User user = permissionChecker.checkUser(userId);
        Craft craft  = permissionChecker.checkCraft(craftId);
        Subcategory subcategory= permissionChecker.checkSubcategory(subcategoryId);

        Pattern pattern = findPatternById(userId, productId);

        pattern.setUser(user);
        pattern.setCraft(craft);
        pattern.setSubcategory(subcategory);
        pattern.setTitle(title);
        pattern.setDescription(description);
        pattern.setPrice(price);
        pattern.setActive(active);
        pattern.setIntroduction(introduction);
        pattern.setNotes(notes);
        pattern.setGauge(gauge);
        pattern.setSizing(sizing);
        pattern.setDifficultyLevel(difficultyLevel);
        pattern.setTime(time);
        pattern.setAbbreviations(abbreviations);
        pattern.setSpecialAbbreviations(specialAbbreviations);

        Set<ProductImages> productImages = new HashSet<>();
        for (String imageUrl : imagesUrl) {
            ProductImages productImage = new ProductImages();
            productImage.setImageUrl(imageUrl);
            productImage.setProduct(pattern);
            productImages.add(productImage);
        }
        pattern.setImages(productImages);

        patternDao.save(pattern);

        return pattern;
    }

    @Override
    public void deletePattern(Long userId, Long productId) throws InstanceNotFoundException, PermissionException {

        Product product = permissionChecker.checkProduct(productId);

        permissionChecker.checkProductExistsAndBelongsTo(productId, userId);

        Set<ProductImages> images = product.getImages();

        //Delete all Images associated
        productImagesDao.deleteAll(images);


        if (product instanceof Pattern) {
            Pattern pattern = (Pattern) product;

            // Delete all tools
            Set<Tool> tools = pattern.getTools();
            toolDao.deleteAll(tools);

            // Delete all yarns
            Set<Yarn> yarns = pattern.getYarns();
            yarnDao.deleteAll(yarns);

            // Delete all sections and their associated steps
            Set<Section> sections = pattern.getSections();
            for (Section section : sections) {
                Set<Step> steps = section.getSteps();
                stepDao.deleteAll(steps);
                sectionDao.delete(section);
            }
        }


        productDao.delete(product);
    }


}

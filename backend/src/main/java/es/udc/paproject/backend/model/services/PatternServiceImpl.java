package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.daos.*;
import es.udc.paproject.backend.model.daos.SectionImagesDao;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.MaxItemsExceededException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import es.udc.paproject.backend.rest.dtos.ProductDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private PurchaseItemDao purchaseItemDao;

    @Override
    public Pattern createPattern(Long userId, Long craftId, Long subcategoryId, String title, String description,
                                 BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                                 String sizing, int difficultyLevel, String time, String abbreviations,
                                 String specialAbbreviations, String language,
                                 List<String> imagesUrl, List<Tool> tools, List<Yarn> yarns, List<Section> sections)
            throws InstanceNotFoundException, UserNotSellerException, MaxItemsExceededException {

        User user = permissionChecker.checkSellerUser(userId);
        Craft craft = permissionChecker.checkCraft(craftId);
        Subcategory subcategory = permissionChecker.checkSubcategory(subcategoryId);

        LocalDateTime creationDate = LocalDateTime.now();

        Pattern patternCreated = new Pattern(user, craft, subcategory,
                title, description, price, active, creationDate,introduction, notes, gauge, sizing, difficultyLevel, time, abbreviations, specialAbbreviations, language);

        productDao.save(patternCreated);

        // Add images
        for(String imageUrl : imagesUrl){
            ProductImages productImage = new ProductImages(patternCreated, imageUrl);
            patternCreated.addImage(productImage);
            productImagesDao.save(productImage);
        }

        for(Tool tool : tools){
            patternCreated.addTool(tool);
            toolDao.save(tool);  // Save each tool
        }

        // Add yarns
        for (Yarn yarn : yarns) {
            patternCreated.addYarn(yarn);
            yarnDao.save(yarn);  // Save each yarn
        }

        // Add sections, and their steps and images
        for (Section section : sections) {
            patternCreated.addSection(section);
            sectionDao.save(section);  // Save section first to generate its ID
            for (Step step : section.getSteps()) {
                section.addStep(step);
                stepDao.save(step);  // Save each step
            }
            for(SectionImages sectionImages : section.getImages()){
                section.addSectionImage(sectionImages);
                sectionImagesDao.save(sectionImages);  // Save each section image
            }
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
    @Transactional(readOnly=true)
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
                               String language,
                               List<String> imagesUrl, List<Tool> tools, List<Yarn> yarns, List<Section> sections)
            throws InstanceNotFoundException, PermissionException, MaxItemsExceededException {


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
        pattern.setLanguage(language);

        // Update images
        Set<ProductImages> productImages = new HashSet<>();
        for (String imageUrl : imagesUrl) {
            ProductImages productImage = new ProductImages();
            productImage.setImageUrl(imageUrl);
            productImage.setProduct(pattern);
            productImages.add(productImage);
        }
        pattern.setImages(productImages);

        // Update tools
        Set<Tool> updatedTools = new HashSet<>(tools);
        for (Tool tool : updatedTools) {
            pattern.addTool(tool);
            toolDao.save(tool);  // Save each tool
        }
        pattern.setTools(updatedTools);

        // Update yarns
        Set<Yarn> updatedYarns = new HashSet<>(yarns);
        for (Yarn yarn : updatedYarns) {
            pattern.addYarn(yarn);
            yarnDao.save(yarn);  // Save each yarn

        }
        pattern.setYarns(updatedYarns);

        // Update sections
        Set<Section> updatedSections = new HashSet<>(sections);
        for (Section section : updatedSections) {
            pattern.addSection(section);
            sectionDao.save(section);
            // Update steps for the section
            for (Step step : section.getSteps()) {
                section.addStep(step);
                stepDao.save(step);
            }
            // Update section images
            for (SectionImages sectionImage : section.getImages()) {
                section.addSectionImage(sectionImage);
                sectionImagesDao.save(sectionImage);
            }
        }
        pattern.setSections(updatedSections);

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


    @Transactional(readOnly = true)
    @Override
    public Pattern findPurchasedPatternById(Long userId, Long patternId) throws InstanceNotFoundException, PermissionException {

        permissionChecker.checkUser(userId);
        permissionChecker.checkProduct(patternId);

        Optional<Pattern> pattern = patternDao.findByIdWithDetails(patternId);

        if (!pattern.isPresent()) {
            throw new InstanceNotFoundException("project.entities.product", patternId);
        }

        // Verify the user has bought the pattern and that the payment has succeeded
        List<PurchaseItem> purchaseItems = purchaseItemDao.findByUserIdAndPatternId(userId, patternId);

        boolean hasBoughtPattern = purchaseItems.stream()
                .anyMatch(item -> item.getPayment().getPaymentStatus().equals("succeeded"));

        if (!hasBoughtPattern) {
            throw new PermissionException();
        }

        return pattern.get();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pattern> findPurchasedPatterns(Long userId) throws InstanceNotFoundException {

        permissionChecker.checkUser(userId);

        List<PurchaseItem> purchaseItems = purchaseItemDao.findByUserId(userId);

        List<Pattern> patterns = new ArrayList<>();
        for (PurchaseItem purchaseItem : purchaseItems) {
            Product product = purchaseItem.getProduct();

            Optional<Pattern> pattern = patternDao.findById(product.getId());
            if(pattern.isPresent() && purchaseItem.getPayment().getPaymentStatus().equals("succeeded")){
                patterns.add(pattern.get());
            }
        }

        return patterns;
    }




}

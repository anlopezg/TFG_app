package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PublicationServiceImpl implements PublicationService{

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PatternDao patternDao;

    @Autowired
    private PhysicalDao physicalDao;

    @Override
    public Pattern createPattern(Long userId, Long craftId, Long subcategoryId, String title, String description,
                              BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                              String sizing, int difficultyLevel, String time, String abbreviations, String specialAbbreviations, String tools)
            throws InstanceNotFoundException, UserNotSellerException{

        User user = permissionChecker.checkUser(userId);

        if(user.getRole() != User.RoleType.SELLER){
            throw new UserNotSellerException();
        }

        Craft craft = catalogService.checkCraft(craftId);
        Subcategory subcategory = catalogService.checkSubcategory(subcategoryId);

        LocalDateTime creationDate = LocalDateTime.now();

        Pattern patternCreated = new Pattern(user, craft, subcategory,
                title, description, price, active, creationDate,introduction, notes, gauge, sizing, difficultyLevel, time, abbreviations, specialAbbreviations, tools);

        productDao.save(patternCreated);

        return patternCreated;
    }

    @Override
    public Physical createPhysical(Long userId, Long craftId, Long subcategoryId, String title, String description,
                            BigDecimal price, Boolean active, int amount, String size, String color,
                            String details) throws InstanceNotFoundException, UserNotSellerException{

        User user = permissionChecker.checkUser(userId);

        if(user.getRole() != User.RoleType.SELLER){
            throw new UserNotSellerException();
        }

        Craft craft = catalogService.checkCraft(craftId);
        Subcategory subcategory = catalogService.checkSubcategory(subcategoryId);

        LocalDateTime creationDate = LocalDateTime.now();

        Physical physicalCreated = new Physical(user, craft, subcategory,
                title, description, price, active, creationDate, amount, size, color, details);

        productDao.save(physicalCreated);

        return physicalCreated;
    }



    @Override
    @Transactional(readOnly = true)
    public Block<Product> findAddedProducts(Long userId, int page, int size){

        Slice<Product> slice = productDao.findAllByUserIdOrderByCreationDateDesc(userId, PageRequest.of(page, size));

        return new Block<>(slice.getContent(), slice.hasNext());
    }

    @Override
    @Transactional(readOnly = true)
    public Block<Pattern> findAddedPatterns(Long userId, int page, int size) throws InstanceNotFoundException, UserNotSellerException {

        User user = permissionChecker.checkUser(userId);

        if(user.getRole() != User.RoleType.SELLER){
            throw new UserNotSellerException();
        }

        Slice<Pattern> slice = patternDao.findAllByUserIdOrderByCreationDateDesc(userId,  PageRequest.of(page, size));

        return new Block<>(slice.getContent(), slice.hasNext());
    }

    @Override
    @Transactional(readOnly = true)
    public Block<Physical> findAddedPhysicals(Long userId, int page, int size){

        Slice<Physical> slice = physicalDao.findAllByUserIdOrderByCreationDateDesc(userId, PageRequest.of(page, size) );

        return new Block<>(slice.getContent(), slice.hasNext());
    }


    @Override
    @Transactional(readOnly = true)
    public Block<Product> findProducts(Long categoryId, String keywords, int page, int size){

        Slice<Product> slice = productDao.find(categoryId, keywords, page, size);

        return new Block<>(slice.getContent(), slice.hasNext());
    }


    @Override
    public Product findProductById(Long productId) throws InstanceNotFoundException{

        Optional<Product> product = productDao.findById(productId);

        if(!product.isPresent()){
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        return product.get();
    }

    @Override
    public Pattern findPatternById(Long patternId) throws InstanceNotFoundException{

        Optional<Pattern> pattern = patternDao.findById(patternId);

        if(!pattern.isPresent()){
            throw new InstanceNotFoundException("project.entities.product", patternId);
        }

        return pattern.get();
    }

    @Override
    public Physical findPhysicalById(Long productId) throws InstanceNotFoundException{

        Optional<Physical> physical = physicalDao.findById(productId);

        if(!physical.isPresent()){
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        return physical.get();
    }


    @Override
    public Pattern editPattern(Long productId, Long userId, Long craftId, Long subcategoryId, String title, String description,
                               BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                               String sizing, int difficultyLevel, String time, String abbreviations, String specialAbbreviations, String tools) throws InstanceNotFoundException{

        User user = permissionChecker.checkUser(userId);
        Craft craft  = catalogService.checkCraft(craftId);
        Subcategory subcategory= catalogService.checkSubcategory(subcategoryId);

        Pattern pattern = findPatternById(productId);

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
        pattern.setTools(tools);

        patternDao.save(pattern);

        return pattern;
    }

    @Override
    public Physical editPhysical(Long productId, Long userId, Long craftId, Long subcategoryId, String title, String description,
                          BigDecimal price, Boolean active, int amount, String size, String color,
                          String details) throws InstanceNotFoundException{

        User user = permissionChecker.checkUser(userId);
        Craft craft  = catalogService.checkCraft(craftId);
        Subcategory subcategory= catalogService.checkSubcategory(subcategoryId);

        Physical physical = findPhysicalById(productId);

        physical.setUser(user);
        physical.setCraft(craft);
        physical.setSubcategory(subcategory);
        physical.setTitle(title);
        physical.setDescription(description);
        physical.setPrice(price);
        physical.setActive(active);
        physical.setAmount(amount);
        physical.setSize(size);
        physical.setColor(color);
        physical.setDetails(details);

        physicalDao.save(physical);

        return physical;
    }

    @Override
    public void deleteProduct(Long productId) throws InstanceNotFoundException{

        Product product = findProductById(productId);

        productDao.delete(product);
    }
}

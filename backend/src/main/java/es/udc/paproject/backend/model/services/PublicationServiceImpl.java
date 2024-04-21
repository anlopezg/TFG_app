package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.daos.*;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.UserNotOwnerException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

    @Autowired
    private ProductImagesDao productImagesDao;

    @Override
    public void checkProductOwner(Long userId, Long productId) throws UserNotOwnerException {

        Optional<Product> product = productDao.findById(productId);

        if(!product.get().getUser().getId().equals(userId)){
            throw new UserNotOwnerException();
        }
    }


    @Override
    public Pattern createPattern(Long userId, Long craftId, Long subcategoryId, String title, String description,
                                 BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                                 String sizing, int difficultyLevel, String time, String abbreviations, String specialAbbreviations, String tools,
                                 List<String> imagesUrl)
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

        for(String imageUrl : imagesUrl){
            ProductImages productImage = new ProductImages(patternCreated, imageUrl);
            patternCreated.addImage(productImage);
            productImagesDao.save(productImage);
        }


        return patternCreated;
    }


    @Override
    public void uploadImages(Long productId, List<String> fileNames) throws InstanceNotFoundException{

        Optional<Product> product = productDao.findById(productId);

        if(product.isEmpty()){
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        for(String fileName: fileNames){
            ProductImages productImage = new ProductImages();
            productImage.setProduct(product.get());
            productImage.setImageUrl(fileName);
        }

    }


    @Override
    public Physical createPhysical(Long userId, Long craftId, Long subcategoryId, String title, String description,
                                   BigDecimal price, Boolean active, int amount, String size, String color,
                                   String details, List<String> imagesUrl) throws InstanceNotFoundException, UserNotSellerException{

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


        for(String imageUrl : imagesUrl){
            ProductImages productImage = new ProductImages(physicalCreated, imageUrl);
            physicalCreated.addImage(productImage);
            productImagesDao.save(productImage);
        }

        return physicalCreated;
    }



    @Override
    @Transactional(readOnly = true)
    public Block<Product> findAddedProducts(Long userId, int page, int size) throws InstanceNotFoundException, UserNotSellerException {

        User user = permissionChecker.checkUser(userId);
        if(user.getRole() != User.RoleType.SELLER){
            throw new UserNotSellerException();
        }

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
    public Block<Physical> findAddedPhysicals(Long userId, int page, int size) throws InstanceNotFoundException, UserNotSellerException {

        User user = permissionChecker.checkUser(userId);
        if(user.getRole() != User.RoleType.SELLER){
            throw new UserNotSellerException();
        }

        Slice<Physical> slice = physicalDao.findAllByUserIdOrderByCreationDateDesc(userId, PageRequest.of(page, size) );

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
    public Pattern findPatternById(Long userId, Long patternId) throws InstanceNotFoundException, UserNotOwnerException {

        Optional<Pattern> pattern = patternDao.findById(patternId);

        if(!pattern.isPresent()){
            throw new InstanceNotFoundException("project.entities.product", patternId);
        }

        checkProductOwner(userId, patternId);

        return pattern.get();
    }

    @Override
    public Physical findPhysicalById(Long userId, Long productId) throws InstanceNotFoundException, UserNotOwnerException {

        Optional<Physical> physical = physicalDao.findById(productId);

        if(!physical.isPresent()){
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        checkProductOwner(userId, productId);

        return physical.get();
    }


    @Override
    public Pattern editPattern(Long productId, Long userId, Long craftId, Long subcategoryId, String title, String description,
                               BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                               String sizing, int difficultyLevel, String time, String abbreviations, String specialAbbreviations, String tools) throws InstanceNotFoundException, UserNotOwnerException {


        User user = permissionChecker.checkUser(userId);
        Craft craft  = catalogService.checkCraft(craftId);
        Subcategory subcategory= catalogService.checkSubcategory(subcategoryId);

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
        pattern.setTools(tools);

        patternDao.save(pattern);

        return pattern;
    }

    @Override
    public Physical editPhysical(Long productId, Long userId, Long craftId, Long subcategoryId, String title, String description,
                          BigDecimal price, Boolean active, int amount, String size, String color,
                          String details) throws InstanceNotFoundException, UserNotOwnerException {


        User user = permissionChecker.checkUser(userId);
        Craft craft  = catalogService.checkCraft(craftId);
        Subcategory subcategory= catalogService.checkSubcategory(subcategoryId);

        Physical physical = findPhysicalById(userId, productId);

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
    public void deleteProduct(Long userId, Long productId) throws InstanceNotFoundException, UserNotOwnerException {

        Product product = findProductById(productId);

        checkProductOwner(userId, productId);

        productDao.delete(product);
    }
}

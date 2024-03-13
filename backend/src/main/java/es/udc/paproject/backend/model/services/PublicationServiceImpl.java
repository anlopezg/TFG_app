package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
    private UserDao userDao;

    @Autowired
    private CraftDao craftDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SubcategoryDao subcategoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PatternDao patternDao;

    @Autowired
    private PhysicalDao physicalDao;

    @Override
    @Transactional(readOnly=true)
    public Craft checkCraft(Long craftId) throws InstanceNotFoundException{

        Optional<Craft> craft = craftDao.findById(craftId);

        if(!craft.isPresent()){
            throw new InstanceNotFoundException("project.entities.craft", craftId);
        }

        return craft.get();
    }

    @Override
    @Transactional(readOnly=true)
    public Subcategory checkSubcategory(Long subcategoryId) throws InstanceNotFoundException{

        Optional<Subcategory> subcategory = subcategoryDao.findById(subcategoryId);

        if(!subcategory.isPresent()){
            throw new InstanceNotFoundException("project.entities.subcategory", subcategoryId);
        }

        return subcategory.get();
    }

    @Override
    public Pattern createPattern(Long userId, Long craftId, Long subcategoryId, String title, String description,
                              BigDecimal price, Boolean active, String introduction, String notes, String gauge,
                              String sizing, int difficultyLevel, String time)
            throws InstanceNotFoundException, UserNotSellerException{

        User user = permissionChecker.checkUser(userId);

        if(user.getRole() != User.RoleType.SELLER){
            throw new UserNotSellerException();
        }

        Craft craft = checkCraft(craftId);
        Subcategory subcategory = checkSubcategory(subcategoryId);

        LocalDateTime creationDate = LocalDateTime.now();

        Pattern patternCreated = new Pattern(user, craft, subcategory,
                title, description, price, active, creationDate,introduction, notes, gauge, sizing, difficultyLevel, time);

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

        Craft craft = checkCraft(craftId);
        Subcategory subcategory = checkSubcategory(subcategoryId);

        LocalDateTime creationDate = LocalDateTime.now();

        Physical physicalCreated = new Physical(user, craft, subcategory,
                title, description, price, active, creationDate, amount, size, color, details);

        productDao.save(physicalCreated);

        return physicalCreated;
    }


    @Override
    @Transactional(readOnly=true)
    public List<Craft> findAllCrafts(){
        return craftDao.findAll(Sort.by(Sort.Direction.ASC, "craftName"));
    }

    @Override
    @Transactional(readOnly=true)
    public List<Category> findAllCategories(){
        return categoryDao.findAll(Sort.by(Sort.Direction.ASC, "categoryName"));

    }

    @Override
    @Transactional(readOnly=true)
    public List<Subcategory> getSubcategoriesByCategory(Long categoryId) {
        return subcategoryDao.findByCategoryId(categoryId);

    }


    @Override
    @Transactional(readOnly = true)
    public Block<Product> findAddedProducts(Long userId, int page, int size){

        Slice<Product> slice = productDao.findAllByUserIdOrderByCreationDateDesc(userId, PageRequest.of(page, size));

        return new Block<>(slice.getContent(), slice.hasNext());
    }

    @Override
    @Transactional(readOnly = true)
    public Block<Pattern> findAddedPatterns(Long userId, int page, int size){

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

}

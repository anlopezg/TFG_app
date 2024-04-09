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

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class CatalogServiceImpl implements CatalogService{

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
    private PermissionChecker permissionChecker;


    @Override
    public Craft checkCraft(Long craftId) throws InstanceNotFoundException {

        Optional<Craft> craft = craftDao.findById(craftId);

        if(!craft.isPresent()){
            throw new InstanceNotFoundException("project.entities.craft", craftId);
        }

        return craft.get();
    }

    @Override
    public Subcategory checkSubcategory(Long subcategoryId) throws InstanceNotFoundException{

        Optional<Subcategory> subcategory = subcategoryDao.findById(subcategoryId);

        if(!subcategory.isPresent()){
            throw new InstanceNotFoundException("project.entities.subcategory", subcategoryId);
        }

        return subcategory.get();
    }


    @Override
    public List<Craft> findAllCrafts(){
        return craftDao.findAll(Sort.by(Sort.Direction.ASC, "craftName"));
    }

    @Override
    public List<Category> findAllCategories(){
        return categoryDao.findAll(Sort.by(Sort.Direction.ASC, "categoryName"));

    }

    @Override
    public List<Subcategory> getSubcategoriesByCategory(Long categoryId) {
        return subcategoryDao.findByCategoryId(categoryId);

    }


    @Override
    public Class<?> getProductTypeClass(String productType){
        switch (productType){
            case "physical":
                return Physical.class;
            case "pattern":
                return Pattern.class;
            default:
                return null;
        }
    }

    // Change to show only the active products
    @Override
    public Block<Product> findProducts(Long craftId, Long subcategoryId, String keywords, String productType, int page, int size){

        Class<?> productTypeClass = null;

        //Checks if product type is null
        if (productType != null) {
            productTypeClass = getProductTypeClass(productType);
            if (productTypeClass == null) {
                return null;
            }
        }

        Slice<Product> slice = productDao.find(craftId, subcategoryId, keywords, productTypeClass, page, size);

        return new Block<>(slice.getContent(), slice.hasNext());

    }

    @Override
    public Block<Product> findUserProducts(String username, int page, int size) throws InstanceNotFoundException, UserNotSellerException {

        User userFound = permissionChecker.checkUserName(username);

        if(!userFound.getRole().equals(User.RoleType.SELLER)){
            throw new UserNotSellerException();
        }

        Slice<Product> slice = productDao.findAllByUserIdAndActiveOrderByCreationDateDesc(userFound.getId(), PageRequest.of(page, size));

        return new Block<>(slice.getContent(), slice.hasNext());

    }

    @Override
    public Product findProduct(Long productId) throws InstanceNotFoundException {

        Optional<Product> product = productDao.findByIdAndActiveOrderByCreationDateDesc(productId);

        if(!product.isPresent()){
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        return product.get();
    }
}

package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.daos.CategoryDao;
import es.udc.paproject.backend.model.entities.Subcategory;
import es.udc.paproject.backend.model.daos.SubcategoryDao;
import es.udc.paproject.backend.model.entities.Craft;
import es.udc.paproject.backend.model.daos.CraftDao;
import es.udc.paproject.backend.model.entities.Pattern;
import es.udc.paproject.backend.model.entities.Physical;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.daos.ProductDao;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.daos.UserDao;
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
    public Class<?> getProductTypeClass(String productType){
        return switch (productType) {
            case "physical" -> Physical.class;
            case "pattern" -> Pattern.class;
            default -> null;
        };
    }


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
    public void getProductType(Product product){

        if(product.getClass().equals(Physical.class)){
            product.setProductType("physical");

        }else if(product.getClass().equals(Pattern.class)){
            product.setProductType("pattern");

        }else{
            product.setProductType("");
        }

    }

    @Override
    public Product findProduct(Long productId) throws InstanceNotFoundException {

        Optional<Product> product = productDao.findByIdAndActiveOrderByCreationDateDesc(productId);

        if(!product.isPresent()){
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        getProductType(product.get());

        return product.get();
    }

    @Override
    public Block<Product> findSellerProducts(String username, int page, int size) throws InstanceNotFoundException, UserNotSellerException {

        User userFound = permissionChecker.checkUserName(username);

        if(!userFound.getRole().equals(User.RoleType.SELLER)){
            throw new UserNotSellerException();
        }

        Slice<Product> slice = productDao.findAllByUserIdAndActiveOrderByCreationDateDesc(userFound.getId(), PageRequest.of(page, size));

        return new Block<>(slice.getContent(), slice.hasNext());

    }

    @Override
    public Block<User> findSellers(String username, int page, int size){

        Slice<User> slice = userDao.findSellers(username, page, size);

        return new Block<>(slice.getContent(), slice.hasNext());
    }
}

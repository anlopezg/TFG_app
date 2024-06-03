package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.daos.*;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.OwnerOfProductException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private FavoriteDao favoriteDao;


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

        Optional<Product> product = productDao.findByIdAndActive(productId, true);

        if(!product.isPresent()){
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        getProductType(product.get());

        return product.get();
    }

    @Override
    public Block<Product> findSellerProducts(String username, int page, int size) throws InstanceNotFoundException, UserNotSellerException {

        User userFound = permissionChecker.checkSellerUser(username);

        Slice<Product> slice = productDao.findByUserIdAndActiveTrueOrderByCreationDateDesc(userFound.getId(), PageRequest.of(page, size));

        return new Block<>(slice.getContent(), slice.hasNext());

    }

    @Override
    public Block<User> findSellers(String username, int page, int size){

        Slice<User> slice = userDao.findSellers(username, page, size);

        return new Block<>(slice.getContent(), slice.hasNext());
    }

    @Override
    public Favorite markAsFavoriteProduct(Long userId, Long productId) throws InstanceNotFoundException, DuplicateInstanceException{

        User user = permissionChecker.checkUser(userId);
        Product product = permissionChecker.checkProduct(productId);

        //Check if the product is already mark as favorite
        Optional<Favorite> alreadyFav = favoriteDao.findByUserAndProduct(user, product);

        if(alreadyFav.isPresent()){
            throw new DuplicateInstanceException("project.entities.Favorite", alreadyFav.get());
        }

        Favorite favorite = new Favorite(user, product, true);

        favoriteDao.save(favorite);
        return favorite;
    }

    @Override
    public List<Product> getFavoriteProducts(Long userId) throws InstanceNotFoundException {

        User user = permissionChecker.checkUser(userId);

        List<Favorite> favorites = favoriteDao.findByUserAndLiked(user, true);

        return favorites.stream().map(Favorite::getProduct).collect(Collectors.toList());
    }


    @Override
    public Optional<Favorite> findFavoriteByUserAndProduct(Long userId, Long productId) throws InstanceNotFoundException{

        User user = permissionChecker.checkUser(userId);

        Product product = permissionChecker.checkProduct(productId);

        return favoriteDao.findByUserAndProduct(user, product);
    }

    @Override
    public Favorite findFavoriteById(Long favoriteId) throws InstanceNotFoundException{

        Optional<Favorite> favorite = favoriteDao.findById(favoriteId);

        if(!favorite.isPresent()){
            throw new InstanceNotFoundException("project.entities.favorite", favorite);
        }

        return favorite.get();
    }


    @Override
    public void removeFavoriteProduct(Long userId, Long productId) throws InstanceNotFoundException {

        User user = permissionChecker.checkUser(userId);

        Product product = permissionChecker.checkProduct(productId);

        Optional<Favorite> favorite = favoriteDao.findByUserAndProduct(user, product);

        if(favorite.isEmpty()){
            throw new InstanceNotFoundException("project.entities.favorite", product.getId());
        }

        favoriteDao.deleteById(favorite.get().getId());
    }
}

package es.udc.paproject.backend.test.model.services;


import es.udc.paproject.backend.model.daos.*;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.OwnerOfProductException;
import es.udc.paproject.backend.model.services.FavoriteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FavoriteServiceTest {

    private final Long NON_EXISTENT_ID = Long.valueOf(-1);

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CraftDao craftDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SubcategoryDao subcategoryDao;

    @Autowired
    private FavoriteService favoriteService;


    private User createUser(String username){
        User user = new User(username, username + "@a.com","password", "firstName", "language",
                "country", "region", 1, 2, "long bio");

        user.setRole(User.RoleType.USER);

        userDao.save(user);
        return user;
    }

    private Craft createCraft(String craftName){
        Craft craft = new Craft(craftName);
        craftDao.save(craft);
        return craft;
    }
    private Category createCategory(String categoryName){

        Category category = new Category(categoryName, null);
        categoryDao.save(category);
        return category;
    }
    private Subcategory createSubcategory(String subcategoryName, Category parent){

        Subcategory subcategory= new Subcategory(subcategoryName, parent);
        subcategoryDao.save(subcategory);
        return subcategory;
    }

    private Product createProduct(User user, Craft craft, Subcategory subcategory, String title){

        Product product = new Product(user, craft, subcategory, title, "descrip", BigDecimal.valueOf(30),
                true, LocalDateTime.now());

        productDao.save(product);
        return product;
    }


    @Test
    public void testMarkAsFavoriteProduct() throws InstanceNotFoundException, DuplicateInstanceException, OwnerOfProductException {

        User user1 = createUser("user1");
        User user2 = createUser("user2");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");

        favoriteService.markAsFavoriteProduct(user2.getId(), product1.getId());

        Favorite favorite = favoriteService.findFavoriteByUserAndProduct(user2.getId(), product1.getId());

        assertNotNull(favorite);
        assertEquals(user2.getId(), favorite.getUser().getId());
        assertEquals(product1.getId(), favorite.getProduct().getId());
        assertTrue(favorite.getLiked());
    }

    @Test
    public void testOwnerMarksFavoriteProduct() {

        User user1 = createUser("user1");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");

        assertThrows(OwnerOfProductException.class, () ->
                favoriteService.markAsFavoriteProduct(user1.getId(), product1.getId()));
    }

    @Test
    public void testGetFavoriteProducts() throws InstanceNotFoundException, DuplicateInstanceException, OwnerOfProductException {

        User user1 = createUser("user1");
        User user2 = createUser("user2");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");
        Product product2 = createProduct(user1, craft1, subcategory1, "Product2");

        favoriteService.markAsFavoriteProduct(user2.getId(), product1.getId());
        favoriteService.markAsFavoriteProduct(user2.getId(), product2.getId());

        List<Product> favoriteProducts = favoriteService.getFavoriteProducts(user2.getId());

        assertNotNull(favoriteProducts);
        assertEquals(2, favoriteProducts.size());
        assertTrue(favoriteProducts.contains(product1));
        assertTrue(favoriteProducts.contains(product2));
    }

    @Test
    public void testRemoveFavoriteProduct() throws InstanceNotFoundException, OwnerOfProductException, DuplicateInstanceException {

        User user1 = createUser("user1");
        User user2 = createUser("user2");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");

        favoriteService.markAsFavoriteProduct(user2.getId(), product1.getId());
        Favorite foundFavorite = favoriteService.findFavoriteByUserAndProduct(user2.getId(), product1.getId());

        favoriteService.removeFavoriteProduct(foundFavorite.getId());

        assertThrows(InstanceNotFoundException.class, () ->
                favoriteService.findFavoriteById(foundFavorite.getId()));
    }

    @Test
    public void testRemoveNonExistentFavoriteProduct(){

        assertThrows(InstanceNotFoundException.class, () ->
                favoriteService.removeFavoriteProduct(NON_EXISTENT_ID));
    }

}

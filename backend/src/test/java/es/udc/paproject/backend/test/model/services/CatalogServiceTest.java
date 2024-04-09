package es.udc.paproject.backend.test.model.services;


import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CatalogServiceTest {

    private final Long NON_EXISTENT_ID = Long.valueOf(-1);

    @Autowired
    private CatalogService catalogService;

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


    /* Object Creation */

    private User createUser(String username){
        User user = new User(username, username + "@a.com","password", "firstName", "language",
                "country", 1, 2, "long bio");

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


    /* TESTS */

    @Test
    public void testCheckCraft() throws InstanceNotFoundException {

        Craft craft = createCraft("Crochet");

        assertEquals(craft,  catalogService.checkCraft(craft.getId()));
    }

    @Test
    public void testFindAllCrafts(){

        Craft craft1 = createCraft("Crochet");
        Craft craft2 = createCraft("Knitting");

        assertEquals(Arrays.asList(craft1, craft2), catalogService.findAllCrafts());
    }

    @Test
    public void testCheckNonExistentCraft(){

        assertThrows(InstanceNotFoundException.class, () ->
                catalogService.checkCraft(NON_EXISTENT_ID));
    }

    @Test
    public void testFindAllCategories(){

        Category category1 = createCategory("Tops");
        Category category2 = createCategory("Bottoms");

        assertEquals(Arrays.asList(category2, category1), catalogService.findAllCategories());
    }

    @Test
    public void testCheckSubcategory() throws InstanceNotFoundException{

        Category category = createCategory("Tops");

        Subcategory subcategory = createSubcategory("Jacket", category);

        assertEquals(subcategory,  catalogService.checkSubcategory(subcategory.getId()));
    }

    @Test
    public void testCheckNonExistentSubcategory(){
        assertThrows(InstanceNotFoundException.class, ()->
                catalogService.checkSubcategory(NON_EXISTENT_ID));
    }


    /* PRODUCT SEARCH */
    @Test
    public void testFindProductsByCraft(){

        User user1 = createUser("user1");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);

        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");
        Product product2 = createProduct(user1, craft1, subcategory1, "Product2");

        Block<Product> expectedBlock = new Block<>(Arrays.asList(product1, product2), false);
        assertEquals(expectedBlock, catalogService.findProducts(craft1.getId(), null, null, null, 0, 2));

    }

    @Test
    public void testFindProductsBySubcategories(){

        User user1 = createUser("user1");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");

        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Subcategory subcategory2 = createSubcategory("Vest", category1);

        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");
        Product product2 = createProduct(user1, craft1, subcategory2, "Product2");

        Block<Product> expectedBlock = new Block<>(Arrays.asList(product1), false);
        assertEquals(expectedBlock, catalogService.findProducts(null, subcategory1.getId(), null, null,0, 1));

    }

    @Test
    public void testFindProductsByUser() throws InstanceNotFoundException, UserNotSellerException {

        User user1 = createUser("user1");
        user1.setRole(User.RoleType.SELLER);
        User user2 = createUser("user2");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);

        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");
        Product product2 = createProduct(user2, craft1, subcategory1, "Product2");

        Block<Product> expectedBlock = new Block<>(Arrays.asList(product1), false);
        assertEquals(expectedBlock, catalogService.findUserProducts(user1.getUserName(), 0, 1));

    }

    @Test
    public void testFindProductsByNotSellerUser(){

        User user1 = createUser("user1");

        assertThrows(UserNotSellerException.class, ()->
                catalogService.findUserProducts(user1.getUserName(), 0, 1));
    }

    @Test
    public void testFindProductsByUnknownUser() {

        assertThrows(InstanceNotFoundException.class, ()->
                catalogService.findUserProducts("NOTUSER", 0, 1));

    }

    @Test
    public void testFindNonActiveProducts(){

        User user1 = createUser("user1");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);

        // Set Product as Not Active
        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");
        product1.setActive(false);

        Block<Product> expectedBlock = new Block<>(Collections.emptyList(), false);
        assertEquals(expectedBlock, catalogService.findProducts(null, null, null, null, 0, 1));
    }

    @Test
    public void testFindProduct() throws InstanceNotFoundException{

        User user1 = createUser("user1");

        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);

        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");

        assertEquals(product1, catalogService.findProduct(product1.getId()));

    }
}

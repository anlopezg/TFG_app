package es.udc.paproject.backend.test.model.services;


import es.udc.paproject.backend.model.daos.*;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.OwnerOfProductException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.model.services.PermissionChecker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CatalogServiceTest {

    private final Long NON_EXISTENT_ID = Long.valueOf(-1);

    @Autowired
    private CatalogService catalogService;

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


    /* Object Creation */

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

    private Pattern createPattern(User user, Craft craft, Subcategory subcategory, String title){

        Pattern pattern = new Pattern(user, craft, subcategory, title, "descrip", BigDecimal.valueOf(30),
                true, LocalDateTime.now(),
                "intro", "notes", "gauge", "sizing", 1,
                "time", "abbr", "special");

        patternDao.save(pattern);
        return pattern;
    }


    /* TESTS */

    @Test
    public void testCheckCraft() throws InstanceNotFoundException {

        Craft craft = createCraft("Crochet");

        assertEquals(craft,  permissionChecker.checkCraft(craft.getId()));
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
                permissionChecker.checkCraft(NON_EXISTENT_ID));
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

        assertEquals(subcategory,  permissionChecker.checkSubcategory(subcategory.getId()));
    }

    @Test
    public void testCheckNonExistentSubcategory(){
        assertThrows(InstanceNotFoundException.class, ()->
                permissionChecker.checkSubcategory(NON_EXISTENT_ID));
    }


    /**
     *  Test for the method {@link CatalogService#findProducts(Long craftId, Long, String, String, int, int)}
     *  to verify product search works using a {@link Craft} as a filter
     */
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

    /**
     *  Test for the method {@link CatalogService#findProducts(Long, Long subcategoryId, String, String, int, int)}
     *  to verify product search works using a {@link Subcategory} as a filter
     */
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


    /**
     *  Test for the method {@link CatalogService#findProducts(Long, Long, String, String, int, int)}
     *  to verify product search works using a Pattern as the Product Type
     */
    @Test
    public void testFindProductsPatterns(){

        User user1 = createUser("user1");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");

        Subcategory subcategory1 = createSubcategory("Jacket", category1);

        Pattern pattern1 = createPattern(user1, craft1, subcategory1, "Product1");

        Block<Pattern> expectedBlock = new Block<>(Arrays.asList(pattern1), false);
        assertEquals(expectedBlock, catalogService.findProducts(null, null, null,
                "pattern", 0, 1));
    }

    /**
     *  Test for the method {@link CatalogService#findProducts(Long, Long, String, String, int, int)}
     *  to verify product search works with all parameters
     */
    @Test
    public void testFindProductsByAll(){

        User user1 = createUser("user1");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");

        Subcategory subcategory1 = createSubcategory("Jacket", category1);

        Pattern pattern1 = createPattern(user1, craft1, subcategory1, "Product1");

        Block<Pattern> expectedBlock = new Block<>(Arrays.asList(pattern1), false);

        assertEquals(expectedBlock, catalogService.findProducts(craft1.getId(), subcategory1.getId(),
                "Prod", "pattern", 0, 1));
    }

    /**
     *  Test for the method {@link CatalogService#findProducts(Long, Long, String, String, int, int)}
     *  to verify product search only returns the products marked as active
     */
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



    /**
     * Test for the method {@link CatalogService#findSellerProducts(String username, int, int)}
     * to verify the product search when given a User's username
     *
     * @throws InstanceNotFoundException The given username doesn't belong to an existing user
     * @throws UserNotSellerException The searched user doesn't have the seller role
     */
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
        assertEquals(expectedBlock, catalogService.findSellerProducts(user1.getUsername(), 0, 1));

    }

    @Test
    public void testFindProductsByNotSellerUser(){

        User user1 = createUser("user1");

        assertThrows(UserNotSellerException.class, ()->
                catalogService.findSellerProducts(user1.getUsername(), 0, 1));
    }

    @Test
    public void testFindProductsByUnknownUser() {

        assertThrows(InstanceNotFoundException.class, ()->
                catalogService.findSellerProducts("NOTUSER", 0, 1));

    }



    /**
     *  Test for the method {@link CatalogService#findProduct(Long)}
     */
    @Test
    public void testFindProduct() throws InstanceNotFoundException{

        User user1 = createUser("user1");

        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);

        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");

        assertEquals(product1, catalogService.findProduct(product1.getId()));
    }

    @Test
    public void testFindProductByNonExistentId(){
        assertThrows(InstanceNotFoundException.class, ()->
                catalogService.findProduct(NON_EXISTENT_ID));
    }

    /**
     *  Test for the method {@link CatalogService#findSellers(String, int, int)}
     */
    @Test
    public void testFindUsersByUsername(){

        User user1 = createUser("seller1");
        user1.setRole(User.RoleType.SELLER);

        User user2 = createUser("seller2");
        user2.setRole(User.RoleType.SELLER);

        Block<User> expectedBlock = new Block<>(Arrays.asList(user1, user2), false);

        assertEquals(expectedBlock, catalogService.findSellers("seller", 0, 2));

    }

    @Test
    public void testMarkAsFavoriteProduct() throws InstanceNotFoundException, DuplicateInstanceException, OwnerOfProductException {

        User user1 = createUser("user1");
        User user2 = createUser("user2");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");

        catalogService.markAsFavoriteProduct(user2.getId(), product1.getId());

        Optional<Favorite> favorite = catalogService.findFavoriteByUserAndProduct(user2.getId(), product1.getId());

        assertNotNull(favorite);
        assertEquals(user2.getId(), favorite.get().getUser().getId());
        assertEquals(product1.getId(), favorite.get().getProduct().getId());
        assertTrue(favorite.get().getLiked());
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

        catalogService.markAsFavoriteProduct(user2.getId(), product1.getId());
        catalogService.markAsFavoriteProduct(user2.getId(), product2.getId());

        List<Product> favoriteProducts = catalogService.getFavoriteProducts(user2.getId());

        assertNotNull(favoriteProducts);
        assertEquals(2, favoriteProducts.size());
        assertTrue(favoriteProducts.contains(product1));
        assertTrue(favoriteProducts.contains(product2));
    }

    @Test
    public void testFindFavorite() throws InstanceNotFoundException, DuplicateInstanceException, OwnerOfProductException {

        User user1 = createUser("user1");
        User user2 = createUser("user2");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");

        catalogService.markAsFavoriteProduct(user2.getId(), product1.getId());

        Optional<Favorite> resultFav = catalogService.findFavoriteByUserAndProduct(user2.getId(), product1.getId());

        assertTrue(resultFav.get().getLiked());
    }

    @Test
    public void testRemoveFavoriteProduct() throws InstanceNotFoundException, OwnerOfProductException, DuplicateInstanceException {

        User user1 = createUser("user1");
        User user2 = createUser("user2");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product1 = createProduct(user1, craft1, subcategory1, "Product1");

        catalogService.markAsFavoriteProduct(user2.getId(), product1.getId());
        Optional<Favorite> foundFavorite = catalogService.findFavoriteByUserAndProduct(user2.getId(), product1.getId());

        catalogService.removeFavoriteProduct(foundFavorite.get().getUser().getId(),foundFavorite.get().getProduct().getId());

        assertThrows(InstanceNotFoundException.class, () ->
                catalogService.findFavoriteById(foundFavorite.get().getId()));
    }

    @Test
    public void testRemoveNonExistentFavoriteProduct(){

        assertThrows(InstanceNotFoundException.class, () ->
                catalogService.removeFavoriteProduct(NON_EXISTENT_ID, NON_EXISTENT_ID));
    }
}

package es.udc.paproject.backend.test.model.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.stripe.exception.StripeException;
import es.udc.paproject.backend.model.daos.*;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PurchaseServiceTest {

    private final Long NON_EXISTENT_ID = Long.valueOf(-1);

    @Autowired
    private UserService userService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private CraftDao craftDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private SubcategoryDao subcategoryDao;


    @Autowired
    private ProductDao productDao;

    @Autowired
    private ShoppingCartItemDao shoppingCartItemDao;

    @Autowired
    private PurchaseDao purchaseDao;

    @Autowired
    private PurchaseItemDao purchaseItemDao;

    private User signUpUser(String username) {

        User user = new User(username, username + "@a.com","password", "firstName", "language",
                "country", "region", 1, 2, "long bio");

        try {
            userService.signUp(user);
        } catch (DuplicateInstanceException e) {
            throw new RuntimeException(e);
        }

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

    private Purchase createPurchase(User user, Product product, LocalDateTime date) {

        String postalAddress = "Postal Address";
        String postalCode = "12345";
        String locality = "Locality";
        String region = "Region";
        String country = "Country";
        Purchase purchase = new Purchase(user, date, postalAddress, locality, region, country, postalCode);
        PurchaseItem item = new PurchaseItem(product, product.getPrice(), 1);

        purchaseDao.save(purchase);
        purchase.addItem(item);
        purchaseItemDao.save(item);

        return purchase;

    }

    private StripeAccount createStripeAccount(User user) {
        StripeAccount stripeAccount = new StripeAccount();
        stripeAccount.setStripeAccountId("acct_1PMy8yPC211iYDLj");
        user.setStripeAccount(stripeAccount);
        return stripeAccount;
    }

    @Test
    public void testAddToEmptyShoppingCart() throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException,
            MaxItemsExceededException {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        int quantity = 2;

        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product.getId(), quantity);

        ShoppingCart shoppingCart = user.getShoppingCart();
        Optional<ShoppingCartItem> item = shoppingCart.getItem(product.getId());

        assertEquals(1, shoppingCart.getItems().size());
        assertTrue(item.isPresent());
        assertEquals(quantity, item.get().getQuantity());

    }

    @Test
    public void testAddNewProductToNonEmptyShoppingCart() throws InstanceNotFoundException, PermissionException,
            MaxQuantityExceededException, MaxItemsExceededException {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product1 = createProduct(user, craft1, subcategory1, "Product1");
        Product product2 = createProduct(user, craft1, subcategory1, "Product2");
        int quantity1 = 1;
        int quantity2 = 2;

        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product1.getId(), quantity1);
        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product2.getId(), quantity2);

        ShoppingCart shoppingCart = user.getShoppingCart();

        assertEquals(2, shoppingCart.getItems().size());

        Optional<ShoppingCartItem> item1 = shoppingCart.getItem(product1.getId());
        Optional<ShoppingCartItem> item2 = shoppingCart.getItem(product2.getId());

        assertTrue(item1.isPresent());
        assertEquals(item1.get().getQuantity(), quantity1);
        assertTrue(item2.isPresent());
        assertEquals(item2.get().getQuantity(), quantity2);

    }

    @Test
    public void testAddExistingProductToShoppingCart() throws InstanceNotFoundException, PermissionException,
            MaxQuantityExceededException, MaxItemsExceededException {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");
        int quantity1 = 1;
        int quantity2 = 2;

        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product.getId(), quantity1);
        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product.getId(), quantity2);

        ShoppingCart shoppingCart = user.getShoppingCart();
        Optional<ShoppingCartItem> item = shoppingCart.getItem(product.getId());

        assertEquals(1, shoppingCart.getItems().size());
        assertTrue(item.isPresent());
        assertEquals(quantity1 + quantity2, item.get().getQuantity());


    }

    @Test
    public void testAddToNonExistingShoppingCart() {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");
        
        assertThrows(InstanceNotFoundException.class,
                () -> purchaseService.addToCart(user.getId(), NON_EXISTENT_ID, product.getId(), 1));

    }

    @Test
    public void testAddNonExistingProductToShoppingCart() {

        User user = signUpUser("user");

        assertThrows(InstanceNotFoundException.class, () ->
                purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), NON_EXISTENT_ID, 1));

    }

    @Test
    public void testAddToAnotherShoppingCart() {

        User user1 = signUpUser("user1");
        User user2 = signUpUser("user2");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user1, craft1, subcategory1, "Product1");

        assertThrows(PermissionException.class, () ->
                purchaseService.addToCart(user1.getId(), user2.getShoppingCart().getId(), product.getId(), 1));

    }

    @Test
    public void testAddToShoppingCartWithNonExistentUserId() {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        assertThrows(PermissionException.class,	() ->
                purchaseService.addToCart(NON_EXISTENT_ID, user.getShoppingCart().getId(), product.getId(), 1));

    }

    @Test
    public void testAddNewProductToShoppingCartMaxQuantityExceededException() {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");
        int quantity = ShoppingCartItem.MAX_QUANTITY + 1;

        MaxQuantityExceededException exception = assertThrows(MaxQuantityExceededException.class, () ->
                purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product.getId(), quantity));

        assertEquals(ShoppingCartItem.MAX_QUANTITY, exception.getMaxAllowedIncrement());
        assertEquals(0, user.getShoppingCart().getItems().size());

    }

    @Test
    public void testAddExistingProductToShoppingCartMaxQuantityExceededException() throws InstanceNotFoundException,
            MaxQuantityExceededException, PermissionException, MaxItemsExceededException {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");
        int quantity1 = 1;
        int quantity2 = ShoppingCartItem.MAX_QUANTITY;

        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product.getId(), quantity1);

        MaxQuantityExceededException exception = assertThrows(MaxQuantityExceededException.class, () ->
                purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product.getId(), quantity2));

        assertEquals(ShoppingCartItem.MAX_QUANTITY-quantity1, exception.getMaxAllowedIncrement());

        ShoppingCart shoppingCart = user.getShoppingCart();
        Optional<ShoppingCartItem> item = shoppingCart.getItem(product.getId());

        assertEquals(1, shoppingCart.getItems().size());
        assertTrue(item.isPresent());
        assertEquals(quantity1, item.get().getQuantity());

    }

    @Test
    public void testAddToShoppingCartMaxItemsExceededException() throws InstanceNotFoundException,
            PermissionException, MaxQuantityExceededException, MaxItemsExceededException {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);

        for (int i=0; i<ShoppingCart.MAX_ITEMS; i++) {
            Product product = createProduct(user, craft1, subcategory1, "Product1");
            purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product.getId(), 1);
        }

        Product product = createProduct(user, craft1, subcategory1, "Product1"+ShoppingCart.MAX_ITEMS);

        assertThrows(MaxItemsExceededException.class,	() ->
                purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product.getId(), 1));

        assertEquals(ShoppingCart.MAX_ITEMS, user.getShoppingCart().getItems().size());

    }

    @Test
    public void testUpdateShoppingCartItemQuantity() throws InstanceNotFoundException, PermissionException,
            MaxQuantityExceededException, MaxItemsExceededException {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");
        int quantity1 = 1;
        int quantity2 = 2;

        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product.getId(), quantity1);
        purchaseService.updateCartItem(user.getId(),
                user.getShoppingCart().getId(), product.getId(), quantity2);

        Optional<ShoppingCartItem> item = user.getShoppingCart().getItem(product.getId());

        assertEquals(quantity2, item.get().getQuantity());

    }

    @Test
    public void testUpdateShoppingCartItemQuantityWithNonExistentShoppingCartId() {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        assertThrows(InstanceNotFoundException.class, () ->
                purchaseService.updateCartItem(user.getId(), NON_EXISTENT_ID, product.getId(), 2));

    }

    @Test
    public void testUpdateShoppingCartItemQuantityWithNonExistentProductId() {

        User user = signUpUser("user");

        assertThrows(InstanceNotFoundException.class, () ->
                purchaseService.updateCartItem(user.getId(), user.getShoppingCart().getId(), NON_EXISTENT_ID, 1));

    }

    @Test
    public void testUpdateShoppingCartItemQuantityWithNonExistentUserId() {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        assertThrows(PermissionException.class,	() ->
                purchaseService.updateCartItem(NON_EXISTENT_ID, user.getShoppingCart().getId(), product.getId(), 1));

    }

    @Test
    public void testUpdateShoppingCartItemQuantityToAnotherShoppingCart() throws InstanceNotFoundException,
            PermissionException, MaxQuantityExceededException, MaxItemsExceededException {

        User user1 = signUpUser("user1");
        User user2 = signUpUser("user2");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user1, craft1, subcategory1, "Product1");

        purchaseService.addToCart(user2.getId(), user2.getShoppingCart().getId(), product.getId(), 1);

        assertThrows(PermissionException.class,	() ->
                purchaseService.updateCartItem(user1.getId(), user2.getShoppingCart().getId(), product.getId(), 2));

    }

    @Test
    public void testUpdateShoppingCartItemQuantityMaxQuantityExceededException() throws InstanceNotFoundException,
            PermissionException, MaxQuantityExceededException, MaxItemsExceededException {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product.getId(), 1);

        assertThrows(MaxQuantityExceededException.class, () ->
                purchaseService.updateCartItem(user.getId(), user.getShoppingCart().getId(), product.getId(), ShoppingCartItem.MAX_QUANTITY+1));

    }

    @Test
    public void removeCartItem() throws InstanceNotFoundException, PermissionException,
            MaxQuantityExceededException, MaxItemsExceededException {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product1 = createProduct(user, craft1, subcategory1, "Product1");
        Product product2 = createProduct(user, craft1, subcategory1, "Product2");

        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product1.getId(), 1);
        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product2.getId(), 1);
        purchaseService.removeCartItem(user.getId(), user.getShoppingCart().getId(), product1.getId());

        ShoppingCart shoppingCart = user.getShoppingCart();

        assertFalse(shoppingCart.getItem(product1.getId()).isPresent());
        assertTrue(shoppingCart.getItem(product2.getId()).isPresent());
        assertFalse(shoppingCartItemDao.findById(product1.getId()).isPresent());

    }

    @Test
    public void removeNonExistentShoppingCartItem() {

        User user = signUpUser("user");

        assertThrows(InstanceNotFoundException.class, () ->
                purchaseService.removeCartItem(user.getId(), user.getShoppingCart().getId(), NON_EXISTENT_ID));

    }

    @Test
    public void testRemoveShoppingCartItemWithNonExistentUserId() {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        assertThrows(PermissionException.class, () ->
                purchaseService.removeCartItem(NON_EXISTENT_ID, user.getShoppingCart().getId(), product.getId()));

    }

    @Test
    public void removeItemFromAnotherShoppingCart() {

        User user1 = signUpUser("user1");
        User user2 = signUpUser("user2");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user1, craft1, subcategory1, "Product1");

        assertThrows(PermissionException.class, () ->
                purchaseService.removeCartItem(user1.getId(), user2.getShoppingCart().getId(), product.getId()));

    }

    @Test
    public void testBuyAndFindPurchase() throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException,
            MaxItemsExceededException, EmptyShoppingCartException, StripeException {

        // Crear usuario y cuenta de Stripe
        User user = signUpUser("user");
        StripeAccount stripeAccount = createStripeAccount(user);
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product1 = createProduct(user, craft1, subcategory1, "Product1");
        Product product2 = createProduct(user, craft1, subcategory1, "Product2");
        int quantity1 = 1;
        int quantity2 = 2;
        String postalAddress = "Postal Address";
        String locality = "Locality";
        String region = "Region";
        String country = "Country";
        String postalCode = "12345";

        // Agregar productos al carrito
        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product1.getId(), quantity1);
        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product2.getId(), quantity2);

        // Realizar la compra con un método de pago de prueba válido
        Purchase purchase = purchaseService.createPurchase(user.getId(), user.getShoppingCart().getId(), locality, region, country, postalAddress, postalCode);
        Purchase foundPurchase = purchaseService.findPurchase(user.getId(), purchase.getId());

        // Verificar la compra
        assertEquals(purchase, foundPurchase);
        assertEquals(user, purchase.getUser());
        assertEquals(postalAddress, purchase.getPostalAddress());
        assertEquals(postalCode, purchase.getPostalCode());
        assertEquals(2, purchase.getItems().size());

        // Verificar los items de la compra
        Optional<PurchaseItem> item1 = purchase.getItem(product1.getId());
        Optional<PurchaseItem> item2 = purchase.getItem(product2.getId());

        assertTrue(item1.isPresent());
        assertEquals(product1.getPrice(), item1.get().getProductPrice());
        assertEquals(quantity1, item1.get().getQuantity());
        assertTrue(item2.isPresent());
        assertEquals(product2.getPrice(), item2.get().getProductPrice());
        assertEquals(quantity2, item2.get().getQuantity());
        assertTrue(user.getShoppingCart().isEmpty());

    }

    @Test
    public void testBuyWithNonExistingShoppingCart() {

        User user = signUpUser("user");

        assertThrows(InstanceNotFoundException.class, () ->
                purchaseService.createPurchase(user.getId(), NON_EXISTENT_ID, "Postal Address", "Locality",
                        "Region", "Country", "12345"));

    }

    @Test
    public void testBuyAnotherShoppingCart() {

        User user1 = signUpUser("user1");
        User user2 = signUpUser("user2");

        assertThrows(PermissionException.class, () ->
                purchaseService.createPurchase(user1.getId(), user2.getShoppingCart().getId(),
                        "Postal Address", "Locality", "Region", "Country","12345"));

    }

    @Test
    public void testBuyWithNonExistentUserId() {

        User user = signUpUser("user");

        assertThrows(PermissionException.class, () ->
                purchaseService.createPurchase(NON_EXISTENT_ID, user.getShoppingCart().getId(), "Postal Address",
                        "Locality", "Region", "Country","12345"));

    }

    @Test
    public void testBuyEmptyShoppingCart() {

        User user = signUpUser("user");

        assertThrows(EmptyShoppingCartException.class, () ->
                purchaseService.createPurchase(user.getId(), user.getShoppingCart().getId(), "Postal Address",
                        "Locality", "Region", "Country","12345"));

    }

    @Test
    public void testFindNonExistentPurchase() {

        User user = signUpUser("user");

        assertThrows(InstanceNotFoundException.class, () ->	purchaseService.findPurchase(user.getId(), NON_EXISTENT_ID));

    }

    @Test
    public void testFindPurchaseOfAnotherUser() throws InstanceNotFoundException, PermissionException,
            MaxQuantityExceededException, MaxItemsExceededException, EmptyShoppingCartException, StripeException {

        User user1 = signUpUser("user1");
        User user2 = signUpUser("user2");
        StripeAccount stripeAccount = createStripeAccount(user1);
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user1, craft1, subcategory1, "Product1");

        purchaseService.addToCart(user1.getId(), user1.getShoppingCart().getId(), product.getId(), 1);

        Purchase order = purchaseService.createPurchase(user1.getId(), user1.getShoppingCart().getId(), "Postal Address",
                "Locality", "Region", "Country","12345");

        assertThrows(PermissionException.class, () -> purchaseService.findPurchase(user2.getId(), order.getId()));

    }

    @Test
    public void testFindPurchaseWithNonExistingUserId() throws InstanceNotFoundException, PermissionException,
            MaxQuantityExceededException, MaxItemsExceededException, EmptyShoppingCartException, StripeException {

        User user = signUpUser("user");
        StripeAccount stripeAccount = createStripeAccount(user);
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product.getId(), 1);

        Purchase order = purchaseService.createPurchase(user.getId(), user.getShoppingCart().getId(),
                "Postal Address", "Locality", "Region", "Country","12345");

        assertThrows(PermissionException.class, () -> purchaseService.findPurchase(NON_EXISTENT_ID, order.getId()));

    }

    @Test
    public void testFindNoPurchases() {

        User user = signUpUser("user");
        Block<Purchase> expectedPurchases = new Block<>(new ArrayList<>(), false);

        assertEquals(expectedPurchases, purchaseService.findPurchases(user.getId(), 0, 1));

    }

    @Test
    public void testFindPurchases() {

        User user = signUpUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        Purchase order1 = createPurchase(user, product, LocalDateTime.of(2017, 10, 1, 10, 2, 3));
        Purchase order2 = createPurchase(user, product, LocalDateTime.of(2018, 11, 1, 10, 2, 3));
        Purchase order3 = createPurchase(user, product, LocalDateTime.of(2018, 12, 1, 10, 2, 3));

        Block<Purchase> expectedBlock = new Block<>(Arrays.asList(order3, order2), true);
        assertEquals(expectedBlock, purchaseService.findPurchases(user.getId(), 0, 2));

        expectedBlock = new Block<>(Arrays.asList(order1), false);
        assertEquals(expectedBlock, purchaseService.findPurchases(user.getId(), 1, 2));

    }


    @Test
    public void testProcessPaymentForPurchase() throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException,
            MaxItemsExceededException, EmptyShoppingCartException, StripeException, PaymentProcessingException {

        User user = signUpUser("user");
        StripeAccount stripeAccount = createStripeAccount(user);
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product1 = createProduct(user, craft1, subcategory1, "Product1");
        Product product2 = createProduct(user, craft1, subcategory1, "Product2");
        int quantity1 = 1;
        int quantity2 = 2;
        String postalAddress = "Postal Address";
        String locality = "Locality";
        String region = "Region";
        String country = "Country";
        String postalCode = "12345";

        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product1.getId(), quantity1);
        purchaseService.addToCart(user.getId(), user.getShoppingCart().getId(), product2.getId(), quantity2);

        Purchase purchase = purchaseService.createPurchase(user.getId(), user.getShoppingCart().getId(), locality, region, country, postalAddress, postalCode);

        // Process the Payment for the purchase made
        purchaseService.processPaymentForPurchase( "pm_card_visa", purchase);

        Purchase foundPurchase = purchaseService.findPurchase(user.getId(), purchase.getId());

        for (PurchaseItem purchaseItem : foundPurchase.getItems()) {
            assertNotNull(purchaseItem.getPayment());
            assertEquals("pm_card_visa", purchaseItem.getPayment().getPaymentMethod());
            assertEquals("eur", purchaseItem.getPayment().getCurrency());
            assertNotNull(purchaseItem.getPayment().getPaymentDate());
            assertEquals(purchaseItem.getProduct().getUser().getStripeAccount().getStripeAccountId(), purchaseItem.getPayment().getStripeAccountId());
            assertEquals(purchaseItem.getTotalPrice().multiply(new BigDecimal(100)), purchaseItem.getPayment().getAmount());
        }

        // Verify the sum of totalAmount of each Item, is the same as the total Price of the Purchase
        BigDecimal totalAmount = foundPurchase.getItems().stream()
                .map(item -> item.getPayment().getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(foundPurchase.getTotalPrice().multiply(new BigDecimal(100)), totalAmount);
    }


}
package es.udc.paproject.backend.test.model.services;


import es.udc.paproject.backend.model.daos.*;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.PermissionChecker;
import es.udc.paproject.backend.model.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReviewServiceTest {

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
    private PurchaseDao purchaseDao;

    @Autowired
    private PurchaseItemDao purchaseItemDao;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private PermissionChecker permissionChecker;

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

    private Purchase createPurchase(User user, Product product, LocalDateTime date) throws MaxItemsExceededException {

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

    @Test
    public void testPublishReview() throws NotPurchasedProductException, CantReviewTwiceException, InstanceNotFoundException, MaxItemsExceededException {

        User user = createUser("username");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        Purchase purchase = createPurchase(user, product, LocalDateTime.of(2017, 10, 1, 10, 2, 3));

        Review createdReview = reviewService.createReview(user.getId(), product.getId(), 3, "Comment");

        assertNotNull(createdReview);
        assertEquals(3, createdReview.getRating());
        assertEquals("Comment", createdReview.getComment());
        assertEquals(user.getId(), createdReview.getUser().getId());
        assertEquals(product.getId(), createdReview.getProduct().getId());

        double expectedAvgRating = 3.0;
        assertEquals(expectedAvgRating, product.getAvgRating(), 0.001);
        assertTrue(reviewDao.findById(createdReview.getId()).isPresent());
        assertEquals(1, product.getReviews().size());
    }

    @Test
    public void testPublishDoubleReview() throws NotPurchasedProductException, CantReviewTwiceException, InstanceNotFoundException, MaxItemsExceededException {

        User user = createUser("username");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        Purchase purchase = createPurchase(user, product, LocalDateTime.of(2017, 10, 1, 10, 2, 3));

        Review createdReview1 = reviewService.createReview(user.getId(), product.getId(), 3, "Comment");

        // Attempt second review
        assertThrows(CantReviewTwiceException.class, () -> reviewService.createReview(user.getId(), product.getId(), 3, "Comment"));
    }

    @Test
    public void testPublishReviewProductNotPurchased() {

        User user = createUser("username");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        // Attempt to review a non purchased product
        assertThrows(NotPurchasedProductException.class, () -> reviewService.createReview(user.getId(), product.getId(), 3, "Comment"));
    }

    @Test
    public void testFindProductReviews() throws InstanceNotFoundException, NotPurchasedProductException, CantReviewTwiceException, MaxItemsExceededException {

        User user = createUser("username");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        Purchase purchase = createPurchase(user, product, LocalDateTime.of(2017, 10, 1, 10, 2, 3));

        Review createdReview1 = reviewService.createReview(user.getId(), product.getId(), 3, "Comment");

        Block<Review> expectedBlock = new Block<>(Arrays.asList(createdReview1), false);

        assertEquals(expectedBlock, reviewService.findProductReviews(product.getId(), 0, 1));
    }

    @Test
    public void testFindUserReviews() throws InstanceNotFoundException, NotPurchasedProductException, CantReviewTwiceException, MaxItemsExceededException {

        User user = createUser("username");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        Purchase purchase = createPurchase(user, product, LocalDateTime.of(2017, 10, 1, 10, 2, 3));

        Review createdReview1 = reviewService.createReview(user.getId(), product.getId(), 3, "Comment");

        Block<Review> expectedBlock = new Block<>(Arrays.asList(createdReview1), false);

        assertEquals(expectedBlock, reviewService.findUserReviews(user.getId(), 0, 1));
    }

    @Test
    public void testEditReview() throws InstanceNotFoundException, PermissionException, NotPurchasedProductException, CantReviewTwiceException, MaxItemsExceededException {

        User user = createUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        Purchase purchase = createPurchase(user, product, LocalDateTime.of(2017, 10, 1, 10, 2, 3));
        Review review = reviewService.createReview(user.getId(), product.getId(), 5, "Initial comment");

        System.out.println("Avg rating before edit: " + product.getAvgRating());
        // Edit the published review
        int newRating = 4;
        String newComment = "Updated comment";
        Review updatedReview = reviewService.editReview(user.getId(), review.getId(), newRating, newComment);

        // Check edit changes
        assertEquals(newRating, updatedReview.getRating());
        assertEquals(newComment, updatedReview.getComment());
        assertEquals(product.getId(), updatedReview.getProduct().getId());
    }

    @Test
    public void testDeleteReview() throws InstanceNotFoundException, PermissionException, NotPurchasedProductException, CantReviewTwiceException, MaxItemsExceededException {

        User user = createUser("user");
        Craft craft1 = createCraft("Crochet");
        Category category1 = createCategory("Tops");
        Subcategory subcategory1 = createSubcategory("Jacket", category1);
        Product product = createProduct(user, craft1, subcategory1, "Product1");

        Purchase purchase = createPurchase(user, product, LocalDateTime.of(2017, 10, 1, 10, 2, 3));
        Review review = reviewService.createReview(user.getId(), product.getId(), 5, "Initial comment");

        // Check the average rating before removing the review
         product.calculateAvgRating();
         Double avgRatingBefore  = product.getAvgRating();

        // Delete Review
        reviewService.deleteReview(user.getId(), review.getId());

        // Check review was deleted
        assertThrows(InstanceNotFoundException.class, () -> {
            permissionChecker.checkReviewExistsAndBelongsTo(review.getId(), user.getId());
        });

    }

    @Test
    public void testCalculateAvgRatingWithDecimalValues() {
        Product product = new Product();
        product.addReview(new Review(4, "Comment 1", LocalDateTime.of(2017, 10, 1, 10, 2, 3)));
        product.addReview(new Review(3, "Comment 2", LocalDateTime.of(2017, 10, 1, 10, 2, 3)));
        product.calculateAvgRating();
        assertEquals(3.5, product.getAvgRating(), 0.01);
    }

}

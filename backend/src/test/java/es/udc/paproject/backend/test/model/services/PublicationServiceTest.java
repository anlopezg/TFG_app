package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.daos.CategoryDao;
import es.udc.paproject.backend.model.entities.Subcategory;
import es.udc.paproject.backend.model.daos.SubcategoryDao;
import es.udc.paproject.backend.model.entities.Craft;
import es.udc.paproject.backend.model.daos.CraftDao;
import es.udc.paproject.backend.model.entities.Pattern;
import es.udc.paproject.backend.model.entities.Physical;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.UserAlreadySellerException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import es.udc.paproject.backend.model.services.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PublicationServiceTest {

    private final Long NON_EXISTENT_ID = Long.valueOf(-1);

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private UserService userService;

    @Autowired
    private CraftDao craftDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private SubcategoryDao subcategoryDao;

    /*   ENTITY CREATION  */

    private User createSellerUser(String userName) throws DuplicateInstanceException, UserAlreadySellerException, InstanceNotFoundException {
        User user = new User(userName, userName + "@a.com","password", "firstName", "language",
                "country", 1, 2, "long bio");

        userService.signUp(user);

        userService.userBecomesSeller(user.getId());

        return user;
    }

    private User createNormalUser(String userName) throws DuplicateInstanceException{
        User user = new User(userName, userName + "@a.com","password", "firstName", "language",
                "country", 1, 2, "long bio");

        userService.signUp(user);

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

    private Pattern createPattern(User user, Craft craft, Subcategory subcategory, String title, BigDecimal price, LocalDateTime creationDate){
        return new Pattern(user, craft, subcategory, title, "Description", price,true, creationDate,
                "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "5mm Crochet Hook x1");
    }

    private Physical createPhysical(User user, Craft craft, Subcategory subcategory, String title, BigDecimal price, LocalDateTime creationDate){
        return new Physical(user, craft, subcategory, title, "Description", price,true, creationDate,
                3, "Size", "Color", "Details");
    }



    /*   SERVICE'S TESTS  */

    @Test
    public void createPattern() throws InstanceNotFoundException, UserNotSellerException, DuplicateInstanceException, UserAlreadySellerException {

        User user = createSellerUser("username");
        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        String title = "title";
        BigDecimal price = BigDecimal.valueOf(10);
        Pattern expectedPattern = createPattern(user, craft, subcategory, title, price, LocalDateTime.now());

        Pattern createdPattern = publicationService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "Hook");



        assertEquals(expectedPattern.getUser(),createdPattern.getUser());
        assertEquals(expectedPattern.getCraft(), createdPattern.getCraft());
        assertEquals(expectedPattern.getSubcategory(), createdPattern.getSubcategory());
        assertEquals(expectedPattern.getTitle(), createdPattern.getTitle());
        assertEquals(expectedPattern.getPrice(), createdPattern.getPrice());
    }

    @Test
    public void NonSellerCreatesPattern() throws DuplicateInstanceException{

        //Non seller user
        User user = createNormalUser("username");
        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        assertThrows(UserNotSellerException.class, ()->
                publicationService.createPattern(user.getId(), craft.getId(), subcategory.getId(), "title", "Description",
                        BigDecimal.valueOf(10), true, "Introduction", "Notes", "Gauge",
                        "Sizing", 1, "10 hours", "US Standard", "None", "Hook"));
    }

    @Test
    public void NonExistentUserCreatesPattern() {
        assertThrows(InstanceNotFoundException.class, ()->
                publicationService.createPattern(NON_EXISTENT_ID, NON_EXISTENT_ID, NON_EXISTENT_ID, "title", "Description",
                        BigDecimal.valueOf(10), true, "Introduction", "Notes", "Gauge",
                        "Sizing", 1, "10 hours", "US Standard", "None", "Hook"));
    }

    @Test
    public void createPhysical() throws InstanceNotFoundException, UserNotSellerException, DuplicateInstanceException, UserAlreadySellerException{

        User user = createSellerUser("username");
        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        String title = "title";
        BigDecimal price = BigDecimal.valueOf(10);
        Physical expectedPhysical = createPhysical(user, craft, subcategory, title, price, LocalDateTime.now());

        Physical physical = publicationService.createPhysical(user.getId(), craft.getId(),subcategory.getId(), title, "Description",
                price, true, 3, "Size", "Color", "Details");

        assertEquals(expectedPhysical.getUser(),physical.getUser());
        assertEquals(expectedPhysical.getCraft(), physical.getCraft());
        assertEquals(expectedPhysical.getSubcategory(), physical.getSubcategory());
        assertEquals(expectedPhysical.getTitle(), physical.getTitle());
        assertEquals(expectedPhysical.getPrice(), physical.getPrice());
    }

    @Test
    public void findAddedPatterns() throws DuplicateInstanceException, UserAlreadySellerException, InstanceNotFoundException, UserNotSellerException{

        User user = createSellerUser("username");

        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        String title1 = "pattern1";
        String title2 = "pattern2";
        BigDecimal price = BigDecimal.valueOf(10);

        Pattern pattern1 = publicationService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title1, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "Hook");

        Pattern pattern2 = publicationService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title2, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "Hook");

        Block<Pattern> expectedBlock = new Block<>(Arrays.asList(pattern1, pattern2), false);
        Block<Pattern> foundPatterns = publicationService.findAddedPatterns(user.getId(), 0, 2);

        assertEquals(expectedBlock, foundPatterns);
    }

    @Test
    public void findAddedPatternsByPages() throws DuplicateInstanceException, UserAlreadySellerException, InstanceNotFoundException, UserNotSellerException{

        User user = createSellerUser("username");

        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        String title1 = "pattern1";
        String title2 = "pattern2";
        String title3 = "pattern3";
        BigDecimal price = BigDecimal.valueOf(10);

        Pattern pattern1 = publicationService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title1, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "Hook");

        Pattern pattern2 = publicationService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title2, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "Hook");

        Pattern pattern3 = publicationService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title3, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "Hook");


        Block<Pattern> firstBlock = new Block<>(Arrays.asList(pattern1, pattern2), true);
        assertEquals(firstBlock, publicationService.findAddedPatterns(user.getId(), 0,2));

        Block<Pattern> secondBlock = new Block<>(Arrays.asList(pattern3), false);
        assertEquals(secondBlock, publicationService.findAddedPatterns(user.getId(), 1,2));
    }

    @Test
    public void findAddedPatternsEmpty() throws DuplicateInstanceException, UserAlreadySellerException, InstanceNotFoundException, UserNotSellerException{

        User user = createSellerUser("username");

        //Empty block
        Block<Pattern> expectedBlock = new Block<>(new ArrayList<>(), false);
        Block<Pattern> foundPatterns = publicationService.findAddedPatterns(user.getId(), 0, 1);

        assertEquals(expectedBlock, foundPatterns);
    }

    @Test
    public void findPatternById() throws DuplicateInstanceException, InstanceNotFoundException, UserAlreadySellerException, UserNotSellerException{

        User user = createSellerUser("username");

        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        Pattern pattern1 = publicationService.createPattern(user.getId(), craft.getId(), subcategory.getId(), "Title1", "Description",
                BigDecimal.valueOf(50), true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "Hook");

        assertEquals(pattern1, publicationService.findPatternById(pattern1.getId()));
    }

    @Test
    public void findPatternByNonExistentId(){
        assertThrows(InstanceNotFoundException.class, ()->
                publicationService.findPatternById(NON_EXISTENT_ID));
    }

    @Test
    public void findPhysicalById() throws DuplicateInstanceException, InstanceNotFoundException, UserAlreadySellerException, UserNotSellerException{

        User user = createSellerUser("username");

        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        Physical physical1 = publicationService.createPhysical(user.getId(), craft.getId(), subcategory.getId(), "Title1", "Description",
                BigDecimal.valueOf(50), true, 3, "Size", "Color", "Details");

        assertEquals(physical1, publicationService.findPhysicalById(physical1.getId()));
    }


    @Test
    public void findPhysicalByNonExistentId(){
        assertThrows(InstanceNotFoundException.class, ()->
                publicationService.findPhysicalById(NON_EXISTENT_ID));
    }


    @Test
    public void editPattern() throws DuplicateInstanceException, InstanceNotFoundException, UserAlreadySellerException, UserNotSellerException{
        User user = createSellerUser("username");

        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        Pattern pattern1 = publicationService.createPattern(user.getId(), craft.getId(), subcategory.getId(), "Title1", "Description",
                BigDecimal.valueOf(50), true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "Hook");

        String updatedTitle = "titleUpdated";
        String updatedDescr = "descripUpdated";

        pattern1.setTitle(updatedTitle);
        pattern1.setDescription(updatedDescr);

        Pattern updatedPattern = publicationService.editPattern(pattern1.getId(), user.getId(), craft.getId(), subcategory.getId(), updatedTitle, updatedDescr,
                BigDecimal.valueOf(50), true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "Hook");

        assertEquals(pattern1, updatedPattern);
    }

}

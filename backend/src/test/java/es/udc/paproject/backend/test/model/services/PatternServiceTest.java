package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.daos.CategoryDao;
import es.udc.paproject.backend.model.daos.CraftDao;
import es.udc.paproject.backend.model.daos.SubcategoryDao;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.PatternService;
import es.udc.paproject.backend.model.services.PermissionChecker;
import es.udc.paproject.backend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PatternServiceTest {

    private final Long NON_EXISTENT_ID = Long.valueOf(-1);

    @Autowired
    private PatternService patternService;

    @Autowired
    private UserService userService;

    @Autowired
    private CraftDao craftDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SubcategoryDao subcategoryDao;


    /*   ENTITY CREATION  */

    private User createSellerUser(String userName) throws Exception {
        User user = new User(userName, userName + "@a.com","password", "firstName", "language",
                "country", "region", 1, 2, "long bio");

        userService.signUp(user);

        userService.userBecomesSeller(user.getId());

        return user;
    }

    private User createNormalUser(String userName) throws DuplicateInstanceException{
        User user = new User(userName, userName + "@a.com","password", "firstName", "language",
                "country", "region", 1, 2, "long bio");

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
                "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None");
    }
    private Pattern createFullPattern() throws Exception {

        User user = createSellerUser("username");
        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        return patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), "Title", "Description",
                BigDecimal.valueOf(20), true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", Collections.emptyList());
    }


    /*   SERVICE'S TESTS  */
    @Test
    public void testCreatePattern() throws Exception {

        User user = createSellerUser("username");
        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        String title = "title";
        BigDecimal price = BigDecimal.valueOf(10);
        Pattern expectedPattern = createPattern(user, craft, subcategory, title, price, LocalDateTime.now());

        Pattern createdPattern = patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None",  Collections.emptyList());


        assertEquals(expectedPattern.getUser(),createdPattern.getUser());
        assertEquals(expectedPattern.getCraft(), createdPattern.getCraft());
        assertEquals(expectedPattern.getSubcategory(), createdPattern.getSubcategory());
        assertEquals(expectedPattern.getTitle(), createdPattern.getTitle());
        assertTrue(expectedPattern.getPrice().compareTo(createdPattern.getPrice()) == 0);
    }

    @Test
    public void testNonSellerCreatesPattern() throws DuplicateInstanceException{

        //Non seller user
        User user = createNormalUser("username");
        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        assertThrows(UserNotSellerException.class, ()->
                patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), "title", "Description",
                        BigDecimal.valueOf(10), true, "Introduction", "Notes", "Gauge",
                        "Sizing", 1, "10 hours", "US Standard", "None", Collections.emptyList()));
    }

    @Test
    public void testNonExistentUserCreatesPattern() {
        assertThrows(InstanceNotFoundException.class, ()->
                patternService.createPattern(NON_EXISTENT_ID, NON_EXISTENT_ID, NON_EXISTENT_ID, "title", "Description",
                        BigDecimal.valueOf(10), true, "Introduction", "Notes", "Gauge",
                        "Sizing", 1, "10 hours", "US Standard", "None", Collections.emptyList()));
    }

    @Test
    public void testFindAddedPatterns() throws Exception {

        User user = createSellerUser("username");

        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        String title1 = "pattern1";
        String title2 = "pattern2";
        BigDecimal price = BigDecimal.valueOf(10);

        Pattern pattern1 = patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title1, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", Collections.emptyList());

        Pattern pattern2 = patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title2, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", Collections.emptyList());

        Block<Pattern> expectedBlock = new Block<>(Arrays.asList(pattern1, pattern2), false);
        Block<Pattern> foundPatterns = patternService.findAddedPatterns(user.getId(), 0, 2);

        assertEquals(expectedBlock, foundPatterns);
    }

    @Test
    public void testFindAddedPatternsByPages() throws Exception {

        User user = createSellerUser("username");

        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        String title1 = "pattern1";
        String title2 = "pattern2";
        String title3 = "pattern3";
        BigDecimal price = BigDecimal.valueOf(10);

        Pattern pattern1 = patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title1, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", Collections.emptyList());

        Pattern pattern2 = patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title2, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", Collections.emptyList());

        Pattern pattern3 = patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title3, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", Collections.emptyList());


        Block<Pattern> firstBlock = new Block<>(Arrays.asList(pattern1, pattern2), true);
        assertEquals(firstBlock, patternService.findAddedPatterns(user.getId(), 0,2));

        Block<Pattern> secondBlock = new Block<>(Arrays.asList(pattern3), false);
        assertEquals(secondBlock, patternService.findAddedPatterns(user.getId(), 1,2));
    }

    @Test
    public void testFindAddedPatternsEmpty() throws Exception {

        User user = createSellerUser("username");

        //Empty block
        Block<Pattern> expectedBlock = new Block<>(new ArrayList<>(), false);
        Block<Pattern> foundPatterns = patternService.findAddedPatterns(user.getId(), 0, 1);

        assertEquals(expectedBlock, foundPatterns);
    }

    @Test
    public void testFindPatternById() throws Exception {

        User user = createSellerUser("username");

        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        Pattern pattern1 = patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), "Title1", "Description",
                BigDecimal.valueOf(50), true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", Collections.emptyList());

        assertEquals(pattern1, patternService.findPatternById(user.getId(), pattern1.getId()));
    }

    @Test
    public void testFindPatternByNonExistentId(){
        assertThrows(InstanceNotFoundException.class, ()->
                patternService.findPatternById(NON_EXISTENT_ID, NON_EXISTENT_ID));
    }

    @Test
    public void testEditPattern() throws Exception {

        Pattern pattern1 = createFullPattern();

        String updatedTitle = "titleUpdated";
        String updatedDescr = "descripUpdated";

        pattern1.setTitle(updatedTitle);
        pattern1.setDescription(updatedDescr);

        Pattern updatedPattern = patternService.editPattern(pattern1.getId(), pattern1.getUser().getId(), pattern1.getCraft().getId(), pattern1.getSubcategory().getId(), updatedTitle, updatedDescr,
                BigDecimal.valueOf(50), true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None",  Collections.emptyList());

        assertEquals(pattern1, updatedPattern);
    }

    @Test
    public void testEditPatternNotOwner() throws Exception {

        User notOwner = createSellerUser("notOwner");
        Pattern pattern1 = createFullPattern();

        assertThrows(PermissionException.class, ()->
                patternService.editPattern(pattern1.getId(), notOwner.getId(), pattern1.getCraft().getId(), pattern1.getSubcategory().getId(), pattern1.getTitle(), pattern1.getDescription(),
                        BigDecimal.valueOf(50), true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", Collections.emptyList()));
    }

}

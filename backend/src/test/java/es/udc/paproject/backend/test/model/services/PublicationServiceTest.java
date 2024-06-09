package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.daos.CategoryDao;
import es.udc.paproject.backend.model.daos.SubcategoryDao;
import es.udc.paproject.backend.model.daos.CraftDao;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

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

    private Physical createPhysical(User user, Craft craft, Subcategory subcategory, String title, BigDecimal price, LocalDateTime creationDate){
        return new Physical(user, craft, subcategory, title, "Description", price,true, creationDate,
                3, "Size", "Color", "Details");
    }

    private Physical createFullPhysical() throws Exception {

        User user = createSellerUser("username");
        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        return publicationService.createPhysical(user.getId(), craft.getId(), subcategory.getId(), "Title", "Description",
                BigDecimal.valueOf(20), true, 5, "Size", "Color", "Details", Collections.emptyList());
    }



    /*   SERVICE'S TESTS  */
    @Test
    public void testCreatePhysical() throws Exception {

        User user = createSellerUser("username");
        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        String title = "title";
        BigDecimal price = BigDecimal.valueOf(10);
        Physical expectedPhysical = createPhysical(user, craft, subcategory, title, price, LocalDateTime.now());

        Physical physical = publicationService.createPhysical(user.getId(), craft.getId(),subcategory.getId(), title, "Description",
                price, true, 3, "Size", "Color", "Details", Collections.emptyList());

        assertEquals(expectedPhysical.getUser(),physical.getUser());
        assertEquals(expectedPhysical.getCraft(), physical.getCraft());
        assertEquals(expectedPhysical.getSubcategory(), physical.getSubcategory());
        assertEquals(expectedPhysical.getTitle(), physical.getTitle());
        assertTrue(expectedPhysical.getPrice().compareTo(physical.getPrice()) == 0);
    }



    @Test
    public void testFindPhysicalById() throws Exception {

        User user = createSellerUser("username");

        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        Physical physical1 = publicationService.createPhysical(user.getId(), craft.getId(), subcategory.getId(), "Title1", "Description",
                BigDecimal.valueOf(50), true, 3, "Size", "Color", "Details", Collections.emptyList());

        assertEquals(physical1, publicationService.findPhysicalById(user.getId(), physical1.getId()));
    }


    @Test
    public void testFindPhysicalByNonExistentId(){
        assertThrows(InstanceNotFoundException.class, ()->
                publicationService.findPhysicalById(NON_EXISTENT_ID, NON_EXISTENT_ID));
    }

    @Test
    public void testDeleteNonExistentProduct(){
        assertThrows(InstanceNotFoundException.class, ()->
                publicationService.deletePhysical(NON_EXISTENT_ID, NON_EXISTENT_ID));
    }

    @Test
    public void testDeleteProductNotOwner() throws Exception {

        Physical physical = createFullPhysical();

        assertThrows(PermissionException.class, ()->
                publicationService.deletePhysical(NON_EXISTENT_ID, physical.getId()));

    }

    @Test
    public void testDeleteProduct() throws Exception {

        Physical physical = createFullPhysical();

        publicationService.deletePhysical(physical.getUser().getId(), physical.getId());

        assertThrows(InstanceNotFoundException.class, ()->
                publicationService.findProductById(physical.getId()));

    }

}

package es.udc.paproject.backend.test.model.services;

import com.stripe.exception.StripeException;
import es.udc.paproject.backend.model.daos.*;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.PatternService;
import es.udc.paproject.backend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private ToolDao toolDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private YarnDao yarnDao;

    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private PurchaseItemDao purchaseItemDao;
    @Autowired
    private PaymentDao paymentDao;


    /*   ENTITY CREATION  */

    private User createSellerUser(String userName) throws DuplicateInstanceException, StripeException, UserAlreadySellerException, InstanceNotFoundException, IOException {
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
                "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "ES");
    }

    private Pattern createPatternExtended() throws StripeException, UserAlreadySellerException, InstanceNotFoundException, IOException, DuplicateInstanceException, UserNotSellerException, MaxItemsExceededException {
        User user = createSellerUser("username");
        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        String title = "Pattern Title";
        String description = "Pattern Description";
        BigDecimal price = BigDecimal.valueOf(10.99);
        Boolean active = true;
        String introduction = "Introduction";
        String notes = "Notes";
        String gauge = "Gauge";
        String sizing = "Sizing";
        int difficultyLevel = 2;
        String time = "2 hours";
        String abbreviations = "abbrev";
        String specialAbbreviations = "specialAbbrev";
        String language = "es";
        List<String> imagesUrl = Arrays.asList("image1.jpg", "image2.jpg");

        Tool tool1 = new Tool("Tool 1", 1);
        Tool tool2 = new Tool("Tool 2", 2);
        List<Tool> tools = Arrays.asList(tool1, tool2);

        Yarn yarn1 = new Yarn("Brand", "Yarn 1", "Color", "Amount", "fiber content", "weight", "length");
        Yarn yarn2 = new Yarn("Brand", "Yarn 2", "Color", "Amount", "fiber content", "weight", "length");
        List<Yarn> yarns = Arrays.asList(yarn1, yarn2);

        Section section1 = new Section("Section 1", "Content 1", 1);
        Section section2 = new Section("Section 2", "Content 2", 2);

        Step step1 = new Step("Row 1 ","Step 1", 1);
        step1.setSection(section1);
        Step step2 = new Step("Row 2 ","Step 2", 2);
        step2.setSection(section2);

        SectionImages sectionImage1 = new SectionImages("sectionImage1.jpg");
        sectionImage1.setSection(section1);
        SectionImages sectionImage2 = new SectionImages("sectionImage2.jpg");
        sectionImage2.setSection(section2);


        List<Section> sections = Arrays.asList(section1, section2);

        return patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title, description,
                price, active, introduction, notes, gauge, sizing, difficultyLevel, time, abbreviations, specialAbbreviations, language,
                imagesUrl, tools, yarns, sections);
    }


    /*   SERVICE'S TESTS  */


    // ELIMINAR
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
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "ES" , Collections.emptyList());


        assertEquals(expectedPattern.getUser(),createdPattern.getUser());
        assertEquals(expectedPattern.getCraft(), createdPattern.getCraft());
        assertEquals(expectedPattern.getSubcategory(), createdPattern.getSubcategory());
        assertEquals(expectedPattern.getTitle(), createdPattern.getTitle());
        assertTrue(expectedPattern.getPrice().compareTo(createdPattern.getPrice()) == 0);
    }

    @Test
    public void testCreatePatternExtended() throws InstanceNotFoundException, UserNotSellerException, StripeException, UserAlreadySellerException, IOException, DuplicateInstanceException, MaxItemsExceededException {

        User user = createSellerUser("username");
        Craft craft = createCraft("Crochet");
        Category category = createCategory("Accesories");
        Subcategory subcategory= createSubcategory("Ring", category);

        String title = "Pattern Title";
        String description = "Pattern Description";
        BigDecimal price = BigDecimal.valueOf(10.99);
        Boolean active = true;
        String introduction = "Introduction";
        String notes = "Notes";
        String gauge = "Gauge";
        String sizing = "Sizing";
        int difficultyLevel = 2;
        String time = "2 hours";
        String abbreviations = "abbrev";
        String specialAbbreviations = "specialAbbrev";
        String language = "ES";
        List<String> imagesUrl = Arrays.asList("image1.jpg", "image2.jpg");

        Tool tool1 = new Tool("Tool 1", 1);
        Tool tool2 = new Tool("Tool 2", 2);
        List<Tool> tools = Arrays.asList(tool1, tool2);

        Yarn yarn1 = new Yarn("Brand", "Yarn 1", "Color", "Amount", "fiber content", "weight", "length");
        Yarn yarn2 = new Yarn("Brand", "Yarn 2", "Color", "Amount", "fiber content", "weight", "length");
        List<Yarn> yarns = Arrays.asList(yarn1, yarn2);

        Section section1 = new Section("Section 1", "Content 1", 1);
        Section section2 = new Section("Section 2", "Content 2", 2);

        Step step1 = new Step("Row 1 ","Step 1", 1);
        step1.setSection(section1);
        Step step2 = new Step("Row 2 ","Step 2", 2);
        step2.setSection(section2);

        SectionImages sectionImage1 = new SectionImages("sectionImage1.jpg");
        sectionImage1.setSection(section1);
        SectionImages sectionImage2 = new SectionImages("sectionImage2.jpg");
        sectionImage2.setSection(section2);


        List<Section> sections = Arrays.asList(section1, section2);

        Pattern pattern = patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title, description,
                price, active, introduction, notes, gauge, sizing, difficultyLevel, time, abbreviations, specialAbbreviations, language,
                imagesUrl, tools, yarns, sections);

        assertNotNull(pattern);
        assertEquals(title, pattern.getTitle());
        assertEquals(description, pattern.getDescription());
        assertEquals(price, pattern.getPrice());
        assertEquals(active, pattern.getActive());
        assertEquals(introduction, pattern.getIntroduction());
        assertEquals(notes, pattern.getNotes());
        assertEquals(gauge, pattern.getGauge());
        assertEquals(sizing, pattern.getSizing());
        assertEquals(difficultyLevel, pattern.getDifficultyLevel());
        assertEquals(time, pattern.getTime());
        assertEquals(abbreviations, pattern.getAbbreviations());
        assertEquals(specialAbbreviations, pattern.getSpecialAbbreviations());
        assertEquals(imagesUrl.size(), pattern.getImages().size());
        assertEquals(tools.size(), pattern.getTools().size());
        assertEquals(yarns.size(), pattern.getYarns().size());
        assertEquals(sections.size(), pattern.getSections().size());
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
                        "Sizing", 1, "10 hours", "US Standard", "None", "US", Collections.emptyList()));
    }

    @Test
    public void testNonExistentUserCreatesPattern() {
        assertThrows(InstanceNotFoundException.class, ()->
                patternService.createPattern(NON_EXISTENT_ID, NON_EXISTENT_ID, NON_EXISTENT_ID, "title", "Description",
                        BigDecimal.valueOf(10), true, "Introduction", "Notes", "Gauge",
                        "Sizing", 1, "10 hours", "US Standard", "None", "US", Collections.emptyList()));
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
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "US",Collections.emptyList());

        Pattern pattern2 = patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title2, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "US",Collections.emptyList());

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
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "US",Collections.emptyList());

        Pattern pattern2 = patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title2, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "US",Collections.emptyList());

        Pattern pattern3 = patternService.createPattern(user.getId(), craft.getId(), subcategory.getId(), title3, "Description",
                price, true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "US",Collections.emptyList());


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
                BigDecimal.valueOf(50), true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None", "US",Collections.emptyList());

        assertEquals(pattern1, patternService.findPatternById(user.getId(), pattern1.getId()));
    }

    @Test
    public void testFindPatternByNonExistentId(){
        assertThrows(InstanceNotFoundException.class, ()->
                patternService.findPatternById(NON_EXISTENT_ID, NON_EXISTENT_ID));
    }


    // ELIMINAR
    @Test
    public void testEditPattern() throws Exception {

        Pattern pattern1 = createPatternExtended();

        String updatedTitle = "titleUpdated";
        String updatedDescr = "descripUpdated";

        pattern1.setTitle(updatedTitle);
        pattern1.setDescription(updatedDescr);

        Pattern updatedPattern = patternService.editPattern(pattern1.getId(), pattern1.getUser().getId(), pattern1.getCraft().getId(), pattern1.getSubcategory().getId(), updatedTitle, updatedDescr,
                BigDecimal.valueOf(50), true, "Introduction", "Notes", "Gauge", "Sizing", 1, "10 hours", "US Standard", "None",  "US", Collections.emptyList());

        assertEquals(pattern1, updatedPattern);
    }

    @Test
    public void testEditPatternNotOwner() throws Exception {

        User notOwner = createSellerUser("notOwner");
        Pattern pattern1 = createPatternExtended();

        assertThrows(PermissionException.class, ()->
                patternService.editPattern(pattern1.getId(), notOwner.getId(), pattern1.getCraft().getId(), pattern1.getSubcategory().getId(), pattern1.getTitle(), pattern1.getDescription(),
                        BigDecimal.valueOf(50), true, "Introduction", "Notes", "Gauge", "Sizing", 1,
                        "10 hours", "US Standard", "None", "US", Collections.emptyList()));
    }


    @Test
    public void testEditPatternExtended() throws InstanceNotFoundException, UserNotSellerException, StripeException, UserAlreadySellerException, IOException, DuplicateInstanceException, PermissionException, MaxItemsExceededException {

        Pattern pattern = createPatternExtended();

        assertNotNull(pattern);

        String newTitle = "Updated Pattern Title";
        String newDescription = "Updated Pattern Description";
        BigDecimal newPrice = BigDecimal.valueOf(12.99);
        Boolean newActive = false;
        String newIntroduction = "Updated Introduction";
        String newNotes = "Updated Notes";
        String newGauge = "Updated Gauge";
        String newSizing = "Updated Sizing";
        int newDifficultyLevel = 3;
        String newTime = "3 hours";
        String newAbbreviations = "updatedAbbrev";
        String newSpecialAbbreviations = "updatedSpecialAbbrev";
        String newLanguage = "CAN";
        List<String> newImagesUrl = Arrays.asList("newImage1.jpg", "newImage2.jpg");

        Tool newTool1 = new Tool("Updated Tool 1", 1);
        Tool newTool2 = new Tool("Updated Tool 2", 2);
        List<Tool> newTools = Arrays.asList(newTool1, newTool2);

        Yarn newYarn1 = new Yarn("Updated Brand", "Updated Yarn 1", "Updated Color", "Updated Amount", "updated fiber content", "updated weight", "updated length");
        Yarn newYarn2 = new Yarn("Updated Brand", "Updated Yarn 2", "Updated Color", "Updated Amount", "updated fiber content", "updated weight", "updated length");
        List<Yarn> newYarns = Arrays.asList(newYarn1, newYarn2);

        Section newSection1 = new Section("Updated Section 1", "Updated Content 1", 1);
        Section newSection2 = new Section("Updated Section 2", "Updated Content 2", 2);

        Step newStep1 = new Step("Updated Row 1 ", "Updated Step 1", 1);
        newStep1.setSection(newSection1);
        Step newStep2 = new Step("Updated Row 2 ", "Updated Step 2", 2);
        newStep2.setSection(newSection2);

        SectionImages newSectionImage1 = new SectionImages("updatedSectionImage1.jpg");
        newSectionImage1.setSection(newSection1);
        SectionImages newSectionImage2 = new SectionImages("updatedSectionImage2.jpg");
        newSectionImage2.setSection(newSection2);

        List<Section> newSections = Arrays.asList(newSection1, newSection2);

        // Edit pattern
        Pattern updatedPattern = patternService.editPattern(pattern.getId(), pattern.getUser().getId(), pattern.getCraft().getId(), pattern.getSubcategory().getId(), newTitle, newDescription,
                newPrice, newActive, newIntroduction, newNotes, newGauge, newSizing, newDifficultyLevel, newTime, newAbbreviations, newSpecialAbbreviations, newLanguage,
                newImagesUrl, newTools, newYarns, newSections);

        // Verify changes
        assertNotNull(updatedPattern);
        assertEquals(newTitle, updatedPattern.getTitle());
        assertEquals(newDescription, updatedPattern.getDescription());
        assertEquals(newPrice, updatedPattern.getPrice());
        assertEquals(newActive, updatedPattern.getActive());
        assertEquals(newIntroduction, updatedPattern.getIntroduction());
        assertEquals(newNotes, updatedPattern.getNotes());
        assertEquals(newGauge, updatedPattern.getGauge());
        assertEquals(newSizing, updatedPattern.getSizing());
        assertEquals(newDifficultyLevel, updatedPattern.getDifficultyLevel());
        assertEquals(newTime, updatedPattern.getTime());
        assertEquals(newAbbreviations, updatedPattern.getAbbreviations());
        assertEquals(newSpecialAbbreviations, updatedPattern.getSpecialAbbreviations());
        assertEquals(newImagesUrl.size(), updatedPattern.getImages().size());
        assertEquals(newTools.size(), updatedPattern.getTools().size());
        assertEquals(newYarns.size(), updatedPattern.getYarns().size());
        assertEquals(newSections.size(), updatedPattern.getSections().size());
    }


    @Test
    public void testDeletePattern() throws Exception {

        Pattern pattern = createPatternExtended();

        assertNotNull(pattern);

        patternService.deletePattern(pattern.getUser().getId(), pattern.getId());

        // Assert that the pattern and all associated entities have been deleted
        assertThrows(InstanceNotFoundException.class, () -> patternService.findPatternById(pattern.getUser().getId(), pattern.getId()));
        assertTrue(toolDao.findAllByPatternId(pattern.getId()).isEmpty());
        assertTrue(yarnDao.findAllByPatternId(pattern.getId()).isEmpty());
        assertTrue(sectionDao.findAllByPatternId(pattern.getId()).isEmpty());
    }


    @Test
    public void testFindBoughtPatternById() throws InstanceNotFoundException, PermissionException, UserNotSellerException, StripeException, UserAlreadySellerException, IOException, NotPurchasedProductException, DuplicateInstanceException, MaxItemsExceededException {

        User buyer = createNormalUser("buyer");
        Pattern pattern = createPatternExtended();

        assertNotNull(pattern);

        // Simulate a purchase
        Purchase purchase = new Purchase();
        purchase.setUser(buyer);
        purchase.setDate(LocalDateTime.now());
        purchase.setPostalAddress("Postal Address");
        purchase.setLocality("Locality");
        purchase.setRegion("Region");
        purchase.setCountry("Country");
        purchase.setPostalCode("12345");

        purchaseDao.save(purchase);


        PurchaseItem purchaseItem = new PurchaseItem(pattern, pattern.getPrice(), 1 );
        purchaseItem.setPurchase(purchase);

        purchaseItemDao.save(purchaseItem);

        // Create Payment
        Payment payment = new Payment();
        payment.setPaymentId("paymentId");
        payment.setPaymentMethod("pm_card_visa");
        payment.setPaymentStatus("succeeded");
        payment.setAmount(pattern.getPrice().multiply(new BigDecimal(100)));
        payment.setCurrency("eur");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStripeAccountId(pattern.getUser().getStripeAccount().getStripeAccountId());
        payment.setStripeTransactionId("transactionId");

        payment.setPurchaseItem(purchaseItem);
        paymentDao.save(payment);

        // Asociate Payment to PurchaseItem
        purchaseItem.setPayment(payment);
        purchaseItemDao.save(purchaseItem);

        // Test  method
        Pattern boughtPattern = patternService.findPurchasedPatternById(buyer.getId(), pattern.getId());

        assertNotNull(boughtPattern);
        assertEquals(pattern.getId(), boughtPattern.getId());
    }

    @Test
    public void testFindPurchasedPatterns() throws InstanceNotFoundException, UserNotSellerException, StripeException, UserAlreadySellerException, IOException, NotPurchasedProductException, DuplicateInstanceException, MaxItemsExceededException {

        User buyer = createNormalUser("buyer");
        Pattern pattern = createPatternExtended();

        assertNotNull(pattern);

        // Simulate a purchase
        Purchase purchase = new Purchase();
        purchase.setUser(buyer);
        purchase.setDate(LocalDateTime.now());
        purchase.setPostalAddress("Postal Address");
        purchase.setLocality("Locality");
        purchase.setRegion("Region");
        purchase.setCountry("Country");
        purchase.setPostalCode("12345");

        purchaseDao.save(purchase);


        PurchaseItem purchaseItem = new PurchaseItem(pattern, pattern.getPrice(), 1);
        purchaseItem.setPurchase(purchase);

        purchaseItemDao.save(purchaseItem);

        // Create Payment
        Payment payment = new Payment();
        payment.setPaymentId("paymentId");
        payment.setPaymentMethod("pm_card_visa");
        payment.setPaymentStatus("succeeded");
        payment.setAmount(pattern.getPrice().multiply(new BigDecimal(100)));
        payment.setCurrency("eur");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStripeAccountId(pattern.getUser().getStripeAccount().getStripeAccountId());
        payment.setStripeTransactionId("transactionId");

        payment.setPurchaseItem(purchaseItem);
        paymentDao.save(payment);

        // Asociate Payment to PurchaseItem
        purchaseItem.setPayment(payment);
        purchaseItemDao.save(purchaseItem);

        // Test  method
        List<Pattern> boughtPattern = patternService.findPurchasedPatterns(buyer.getId());

        assertNotNull(boughtPattern);
        assertEquals(1, boughtPattern.size());
        assertTrue(boughtPattern.contains(pattern));
    }



}

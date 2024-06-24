package es.udc.paproject.backend.test.rest;

import es.udc.paproject.backend.model.daos.CategoryDao;
import es.udc.paproject.backend.model.daos.CraftDao;
import es.udc.paproject.backend.model.daos.ProductDao;
import es.udc.paproject.backend.model.daos.UserDao;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.rest.controllers.CatalogController;
import es.udc.paproject.backend.rest.dtos.FavoriteDto;
import es.udc.paproject.backend.rest.dtos.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CraftDao craftDao;

    @Autowired
    private CategoryDao categoryDao;



    private Craft createCraft(String craftName){
        Craft craft = new Craft(craftName);
        craftDao.save(craft);
        return craft;
    }

    private Category createCategory(String categoryName){
        Category category = new Category(categoryName, new HashSet<>());
        categoryDao.save(category);
        return category;
    }

    @Test
    public void testFindAllCrafts() throws Exception{

        Craft craft1 = createCraft("craft1");
        Craft craft2 = createCraft("craft2");

        mockMvc.perform(get("/catalog/crafts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].craftName").value(craft1.getCraftName()))
                .andExpect(jsonPath("$[1].craftName").value(craft2.getCraftName()));
    }

    @Test
    public void testFindAllCategories() throws Exception{

        Category category1 = createCategory("category1");
        Category category2 = createCategory("category2");

        mockMvc.perform(get("/catalog/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryName").value(category1.getCategoryName()))
                .andExpect(jsonPath("$[1].categoryName").value(category2.getCategoryName()));
    }

    /*
    @Test
    public void testFindProducts() throws Exception {
        // Mock response
        Product product = new Product();
        product.setId(1L);
        Block<Product> productBlock = new Block<>(Arrays.asList(product), false);
        when(catalogService.findProducts(any(), any(), any(), any(), eq(0), eq(9))).thenReturn(productBlock);

        // Perform GET request and validate response
        mockMvc.perform(get("/catalog/products?page=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.existMoreItems").value(false))
                .andExpect(jsonPath("$.items[0].id").value(1L));
    }

    @Test
    public void testFindProductById() throws Exception {
        // Mock response
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Title");
        product.setDescription("Description");
        product.setPrice(new BigDecimal("10.00"));

        when(catalogService.findProductById(1L)).thenReturn(product);

        // Perform GET request and validate response
        mockMvc.perform(get("/catalog/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    public void testFindUserProducts() throws Exception {
        // Mock response
        Product product = new Product();
        product.setId(1L);
        Block<Product> productBlock = new Block<>(Arrays.asList(product), false);
        when(catalogService.findSellerProducts(eq("username"), eq(0), eq(9))).thenReturn(productBlock);

        // Perform GET request and validate response
        mockMvc.perform(get("/catalog/users/username/products?page=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.existMoreItems").value(false))
                .andExpect(jsonPath("$.items[0].id").value(1L));
    }

    @Test
    public void testFindUsers() throws Exception {
        // Mock response
        User user = new User();
        user.setId(1L);
        Block<User> userBlock = new Block<>(Arrays.asList(user), false);
        when(catalogService.findSellers(any(), eq(0), eq(10))).thenReturn(userBlock);

        // Perform GET request and validate response
        mockMvc.perform(get("/catalog/users?page=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.existMoreItems").value(false))
                .andExpect(jsonPath("$.items[0].id").value(1L));
    }

    @Test
    public void testMarkAsFavorite() throws Exception {
        // Mock response
        Favorite favorite = new Favorite();
        favorite.setId(1L);
        FavoriteDto favoriteDto = new FavoriteDto(1L, 1L, false);
        when(catalogService.markAsFavoriteProduct(eq(1L), eq(1L))).thenReturn(favorite);

        // Perform POST request and validate response
        mockMvc.perform(post("/catalog/favorites")
                        .header("userId", "1")
                        .contentType("application/json")
                        .content("{\"productId\": 1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testGetFavorites() throws Exception {
        // Mock response
        Product product = new Product();
        product.setId(1L);
        List<Product> products = Arrays.asList(product);
        when(catalogService.getFavoriteProducts(eq(1L))).thenReturn(products);

        // Perform GET request and validate response
        mockMvc.perform(get("/catalog/favorites").header("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    public void testFindFavoriteByUserAndProduct() throws Exception {
        // Mock response
        Favorite favorite = new Favorite();
        favorite.setId(1L);
        when(catalogService.findFavoriteByUserAndProduct(eq(1L), eq(1L))).thenReturn(Optional.of(favorite));

        // Perform GET request and validate response
        mockMvc.perform(get("/catalog/favorites/1").header("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testRemoveFavorite() throws Exception {
        // Mock the service call
        doNothing().when(catalogService).removeFavoriteProduct(eq(1L), eq(1L));

        // Perform DELETE request and validate response
        mockMvc.perform(delete("/catalog/favorites/1").header("userId", "1"))
                .andExpect(status().isNoContent());
    }*/

}

package es.udc.paproject.backend.test.rest;

import es.udc.paproject.backend.model.daos.CategoryDao;
import es.udc.paproject.backend.model.daos.CraftDao;
import es.udc.paproject.backend.model.daos.ProductDao;
import es.udc.paproject.backend.model.daos.UserDao;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Craft;
import es.udc.paproject.backend.rest.controllers.CatalogController;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CatalogController catalogController;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

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
}

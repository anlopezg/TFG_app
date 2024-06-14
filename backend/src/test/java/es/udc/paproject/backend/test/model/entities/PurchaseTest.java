package es.udc.paproject.backend.test.model.entities;

import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.entities.PurchaseItem;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.MaxItemsExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PurchaseTest {

    private User user;
    private Purchase purchase;
    @BeforeEach
    public void setUp() {
        user = new User();
        purchase = new Purchase(user, LocalDateTime.now(), "123 Street", "City", "Region", "Country", "12345");
    }

    @Test
    public void testGetTotalPrice() {
        Product product1 = new Product();
        product1.setPrice(new BigDecimal("10.00"));
        PurchaseItem item1 = new PurchaseItem(product1, product1.getPrice(), 2);

        Product product2 = new Product();
        product2.setPrice(new BigDecimal("20.00"));
        PurchaseItem item2 = new PurchaseItem(product2, product2.getPrice(), 1);

        Set<PurchaseItem> items = new HashSet<>();
        items.add(item1);
        items.add(item2);
        purchase.setItems(items);

        assertEquals(new BigDecimal("40.00"), purchase.getTotalPrice());
    }

    @Test
    public void testAddItem_MaxItemsExceededException() {
        for (int i = 0; i < Purchase.MAX_ITEMS; i++) {
            purchase.getItems().add(new PurchaseItem());
        }

        assertThrows(MaxItemsExceededException.class, () -> {
            purchase.addItem(new PurchaseItem());
        });
    }

    @Test
    public void testAddItem() throws MaxItemsExceededException {

        Product product1 = new Product();
        product1.setPrice(new BigDecimal("10.00"));
        PurchaseItem item1 = new PurchaseItem(product1, product1.getPrice(), 1);
        purchase.addItem(item1);
        assertEquals(1, purchase.getItems().size());
    }
}

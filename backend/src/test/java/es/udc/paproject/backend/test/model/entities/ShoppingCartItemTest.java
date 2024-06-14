package es.udc.paproject.backend.test.model.entities;

import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ShoppingCart;
import es.udc.paproject.backend.model.entities.ShoppingCartItem;
import es.udc.paproject.backend.model.exceptions.MaxQuantityExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ShoppingCartItemTest {

    private Product product;
    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setPrice(new BigDecimal("10.00"));

        shoppingCart = new ShoppingCart();
    }

    @Test
    public void testGetTotalPrice() throws MaxQuantityExceededException {
        ShoppingCartItem item = new ShoppingCartItem(product, shoppingCart, 5);
        assertEquals(new BigDecimal("50.00"), item.getTotalPrice());
    }

    @Test
    public void testSetQuantity_MaxQuantityExceededException() {
        assertThrows(MaxQuantityExceededException.class, () -> {
            new ShoppingCartItem(product, shoppingCart, 101);
        });
    }

    @Test
    public void testIncrementQuantity_MaxQuantityExceededException() throws MaxQuantityExceededException {
        ShoppingCartItem item = new ShoppingCartItem(product, shoppingCart, 95);
        assertThrows(MaxQuantityExceededException.class, () -> {
            item.incrementQuantity(10);
        });
    }

    @Test
    public void testIncrementQuantity() throws MaxQuantityExceededException {
        ShoppingCartItem item = new ShoppingCartItem(product, shoppingCart, 5);
        item.incrementQuantity(10);
        assertEquals(15, item.getQuantity());
        assertEquals(new BigDecimal("150.00"), item.getTotalPrice());
    }
}

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
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setUp() {
        shoppingCart = new ShoppingCart();
    }

    @Test
    public void testGetTotalQuantity_NoItems() {
        assertEquals(0, shoppingCart.getTotalQuantity());
    }

    @Test
    public void testGetTotalPrice_NoItems() {
        assertEquals(BigDecimal.ZERO, shoppingCart.getTotalPrice());
    }

    @Test
    public void testGetTotalQuantity_WithItems() throws MaxQuantityExceededException {
        Product product1 = new Product();
        product1.setPrice(new BigDecimal(10));
        ShoppingCartItem item1 = new ShoppingCartItem();
        item1.setProduct(product1);
        item1.setQuantity(2);

        Product product2 = new Product();
        product2.setPrice(new BigDecimal(20));
        ShoppingCartItem item2 = new ShoppingCartItem();
        item2.setProduct(product2);
        item2.setQuantity(3);

        Set<ShoppingCartItem> items = new HashSet<>();
        items.add(item1);
        items.add(item2);
        shoppingCart.setItems(items);

        assertEquals(5, shoppingCart.getTotalQuantity());
    }

    @Test
    public void testGetTotalPrice_WithItems() throws MaxQuantityExceededException {
        Product product1 = new Product();
        product1.setPrice(new BigDecimal("10.00"));
        ShoppingCartItem item1 = new ShoppingCartItem();
        item1.setProduct(product1);
        item1.setQuantity(1);

        Product product2 = new Product();
        product2.setPrice(new BigDecimal("20.00"));
        ShoppingCartItem item2 = new ShoppingCartItem();
        item2.setProduct(product2);
        item2.setQuantity(1);

        Set<ShoppingCartItem> items = new HashSet<>();
        items.add(item1);
        items.add(item2);
        shoppingCart.setItems(items);

        assertEquals(new BigDecimal("30.00"), shoppingCart.getTotalPrice());
    }
}

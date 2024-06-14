package es.udc.paproject.backend.test.model.entities;

import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProductTest {

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
    }

    @Test
    public void testCalculateAvgRating_NoReviews() {
        product.calculateAvgRating();
        assertEquals(0.0, product.getAvgRating());
    }

    @Test
    public void testCalculateAvgRating_WithReviews() {
        Review review1 = new Review(5, "comment1", LocalDateTime.now());
        Review review2 = new Review(3, "comment2", LocalDateTime.now());
        Review review3 = new Review(4, "comment3", LocalDateTime.now());
        Set<Review> reviews = new HashSet<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        product.setReviews(reviews);

        product.calculateAvgRating();
        assertEquals(4.0, product.getAvgRating());
    }
}

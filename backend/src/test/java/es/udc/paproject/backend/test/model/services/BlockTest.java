package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.services.Block;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BlockTest {

    private Block<String> block;

    @BeforeEach
    public void setUp() {
        List<String> elements = Arrays.asList("Element1", "Element2", "Element3");
        boolean hasMoreElements = true;
        block = new Block<>(elements, hasMoreElements);
    }

    @Test
    public void testGetElements() {
        List<String> expectedElements = Arrays.asList("Element1", "Element2", "Element3");
        assertEquals(expectedElements, block.getItems());
    }

    @Test
    public void testHasMoreElements() {
        assertTrue(block.getExistMoreItems());
    }

    @Test
    public void testBlockInitialization_NoMoreElements() {
        List<String> elements = Arrays.asList("Element4", "Element5");
        Block<String> blockNoMoreElements = new Block<>(elements, false);

        assertEquals(elements, blockNoMoreElements.getItems());
        assertFalse(blockNoMoreElements.getExistMoreItems());
    }
}

package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.Product;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTest {

    Product product;

    @BeforeAll
    static void initAll() {
        System.out.println("== Starting Product Test Suite ==");
    }

    @BeforeEach
    void setup() {
        product = new Product("Laptop", 200);
        System.out.println("-- Test Initialized --");
    }

    @AfterEach
    void cleanup() {
        System.out.println("-- Test Finished --");
    }

    @AfterAll
    static void endAll() {
        System.out.println("== All Tests Executed ==");
    }

    @Test
    @DisplayName("Valid discount is applied correctly")
    void validDiscountTest() {
        product.applyDiscount(15);
        assertEquals(170, product.getFinalPrice());
    }

    @Test
    @DisplayName("Invalid discount (negative) triggers exception")
    void negativeDiscountShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(-12));
    }

    @ParameterizedTest
    @ValueSource(doubles = {5, 12.5, 30})
    @DisplayName("Check discounted price with various percentages")
    void testVariousDiscounts(double discount) {
        double expected = 200 - (discount / 100.0 * 200);
        product.applyDiscount(discount);
        assertEquals(expected, product.getFinalPrice(), 0.001);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @DisplayName("Ensure applyDiscount runs quickly")
    void discountPerformanceTest() {
        product.applyDiscount(8);
        assertEquals(184, product.getFinalPrice());
    }

    @Test
    @DisplayName("Failing test with incorrect expected price")
    void forcedFailureTest() {
        product.applyDiscount(25);
        assertEquals(150, product.getFinalPrice());
    }

    @Test
    //@Disabled()
    @DisplayName("Confirm product name retrieval")
    void getNameTest() {
        assertEquals("Laptop", product.getName());
    }

    @Test
    @DisplayName("Confirm original price is stored correctly")
    void getOriginalPriceTest() {
        assertEquals(200, product.getPrice());
    }


@Test
	@DisplayName("test getName method")
	void testGetName() {
		assertEquals("Laptop", product.getName());
	}
	
	@Test
	@DisplayName("test getPrice method")
	void testGetPrice() {
		assertEquals(200, product.getPrice());
	}
	
	@Test
	@DisplayName("test getDicount method")
	void testGetDiscount() {
		assertEquals(0, product.getDiscount());
}}
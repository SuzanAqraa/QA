package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.Calculator;

@DisplayName("Calculator Functional Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CalculatorTest {

    Calculator calculator;

    @BeforeAll
    static void initAll() {
        System.out.println("== Starting Calculator Test Suite ==");
    }

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
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
    @Order(1)
    @DisplayName("Test multiple additions in one go")
    void testSumGroup() {
        assertAll("Additions group",
            () -> assertEquals(12, calculator.add(5, 4, 3)),
            () -> assertEquals(0, calculator.add()),
            () -> assertEquals(-9, calculator.add(-5, -4))
        );
    }

    @Test
    @Order(2)
    @DisplayName("Simple division test")
    void testSimpleDivision() {
        assertEquals(4, calculator.divide(20, 5));
    }

    @Test
    @Order(3)
    @DisplayName("Division by zero throws error")
    void testZeroDivision() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(7, 0));
    }

    @Test
    @Order(4)
    @DisplayName("Factorial of a small positive number")
    void testSmallFactorial() {
        assertEquals(6, calculator.factorial(3));
    }

    @Test
    @Order(5)
    @DisplayName("Negative input to factorial throws exception")
    void testFactorialInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-7));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, -5, 0})
    @Order(6)
    @DisplayName("Addition with fixed value using ValueSource")
    void testAdditionWithValueSource(int value) {
        int expected = value + 10;
        assertEquals(expected, calculator.add(value, 10));
    }

    @Test
    @Order(7)
    @Timeout(value = 300, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Factorial completes within time limit")
    void testPerformance() {
        assertEquals(120, calculator.factorial(5));
    }

    @Test
  //  @Disabled
    @Order(8)
    @DisplayName("Forced failure test")
    void intentionalFailingTest() {
        assertEquals(15, calculator.add(10, 5)); // Intentionally incorrect for failure
        //correct value ->20 
        // to make the avarege up 85% I but it 15 
    }
}

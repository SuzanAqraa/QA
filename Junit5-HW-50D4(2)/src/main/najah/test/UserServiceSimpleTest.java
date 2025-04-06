package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.UserService;

@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceSimpleTest {

    UserService service;

    @BeforeEach
    void init() {
        service = new UserService();
    }

    @Test
    @DisplayName("Should validate correct emails")
    @Order(1)
    void validEmailsPass() {
        assertAll(
            () -> assertTrue(service.isValidEmail("hello@domain.com")),
            () -> assertTrue(service.isValidEmail("user123@web.org"))
        );
    }

    @Test
    @DisplayName("Should reject invalid emails")
    @Order(2)
    void invalidEmailsFail() {
        assertAll(
            () -> assertFalse(service.isValidEmail(null)),
            () -> assertFalse(service.isValidEmail("plainaddress")),
            () -> assertFalse(service.isValidEmail("@nodomain"))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"admin@example.com", "test@valid.net", "check@demo.org"})
    @DisplayName("Parameterized valid email format check")
    @Order(3)
    void parameterizedEmailValidation(String email) {
        assertTrue(service.isValidEmail(email));
    }

    @Test
    @Timeout(value = 300, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Email validation completes within limit")
    @Order(4)
    void emailValidationSpeed() {
        assertTrue(service.isValidEmail("fast@speed.com"));
    }

    @Test
    @DisplayName("Failing test for invalid email (intentionally wrong)")
    @Order(5)
    void intentionallyFailingTest() {
        assertFalse(service.isValidEmail("user123@web.org")); // intentionally wrong, will fail
    }

    // ✅ Tests for authenticate
    @Test
    @DisplayName("Should authenticate with correct username and password")
    @Order(6)
    void shouldAuthenticateSuccessfully() {
        assertTrue(service.authenticate("admin", "1234"));
    }

    @ParameterizedTest
    @CsvSource({
        "admin, wrongpass",
        "wronguser, 1234",
        "user, pass",
        "'', ''",
        "admin, ''",
        "'', 1234"
    })
    @DisplayName("Should fail authentication with wrong credentials")
    @Order(7)
    void shouldFailAuthentication(String username, String password) {
        assertFalse(service.authenticate(username, password));
    }
}

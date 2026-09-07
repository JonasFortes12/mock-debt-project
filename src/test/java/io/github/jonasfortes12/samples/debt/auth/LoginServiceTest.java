package io.github.jonasfortes12.samples.debt.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {

    private LoginService loginService;

    @BeforeEach
    void setUp() {
        loginService = new LoginService();
    }

    @Test
    void shouldReturnFalseForNullUsername() {
        assertFalse(loginService.authenticate(null, "password"));
    }

    @Test
    void shouldReturnFalseForEmptyUsername() {
        assertFalse(loginService.authenticate("", "password"));
    }

    @Test
    void shouldReturnTrueForValidCredentials() {
        assertTrue(loginService.authenticate("user", "pass"));
    }
}

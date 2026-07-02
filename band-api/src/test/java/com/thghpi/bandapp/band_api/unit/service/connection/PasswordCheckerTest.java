package com.thghpi.bandapp.band_api.unit.service.connection;
import com.thghpi.bandapp.band_api.service.connection.PasswordChecker;
import com.thghpi.bandapp.band_api.config.properties.PasswordProperties;
import com.thghpi.bandapp.band_api.service.exception.InvalidPasswordException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for the PasswordChecker class, which checks the strength of passwords
 * against a defined regex pattern. These tests ensure that the PasswordChecker correctly
 * validates password strength according to the specified criteria.
 */
public class PasswordCheckerTest {
    /**
     * The PasswordChecker instance to be tested, initialized with specific password properties
    */
    private PasswordChecker passwordChecker;

    /**
     * Sets up the test environment before each test method is executed.
     */
    @BeforeEach
    void setUp() {

        PasswordProperties properties = new PasswordProperties(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).*([^\s]){8,20}$",
            8L,
            128L,
            true,
            true,
            true,
            true
        );

        passwordChecker = new PasswordChecker(properties);
    }

    /**
     * Tests that the PasswordChecker correctly rejects weak passwords.
     */
    @Test
    void shouldRejectWeakPassword() {
        String weak1 = "Weakpass1"; // Missing special character
        String weak2 = "weakpass1!"; // Missing uppercase letter
        String weak3 = "WEAKPASS1!"; // Missing lowercase letter
        String weak4 = "Weakpass!"; // Missing digit
        String weak5 = "Weak1!"; // Too short
        assertThrows(InvalidPasswordException.class, () -> passwordChecker.checkPasswordStrength(weak1));
        assertThrows(InvalidPasswordException.class, () -> passwordChecker.checkPasswordStrength(weak2));
        assertThrows(InvalidPasswordException.class, () -> passwordChecker.checkPasswordStrength(weak3));
        assertThrows(InvalidPasswordException.class, () -> passwordChecker.checkPasswordStrength(weak4));
        assertThrows(InvalidPasswordException.class, () -> passwordChecker.checkPasswordStrength(weak5));
    }

    /**
     * Tests that the PasswordChecker correctly accepts strong passwords.
     */
    @Test
    void shouldAcceptStrongPassword() {
        String strong = "StrongPass1!";
        assertDoesNotThrow(() -> passwordChecker.checkPasswordStrength(strong)); // Should not throw an exception
    }
}

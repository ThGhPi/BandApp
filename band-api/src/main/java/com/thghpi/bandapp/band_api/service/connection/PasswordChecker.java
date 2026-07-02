package com.thghpi.bandapp.band_api.service.connection;
import com.thghpi.bandapp.band_api.config.properties.PasswordProperties;
import com.thghpi.bandapp.band_api.service.exception.InvalidPasswordException;

import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

/**
 * A service class for checking the strength of passwords
 * to be used when changing or creating a password for a user
 * PasswordChecker
 */
@Service
@RequiredArgsConstructor
public class PasswordChecker {
    private final PasswordProperties passwordProperties;

    /**
     * Checks the strength of the provided password
     * against the regex defined in the PasswordProperties
     * @param password the password to check
     * @throws InvalidPasswordException if the password does not meet the required criteria
     */
    public void checkPasswordStrength(String password) {
        Pattern pattern = Pattern.compile(passwordProperties.regex());
        if (!pattern.matcher(password).matches()) {
            throw new InvalidPasswordException(
                "Can't use this password. It does not meet the required criteria."
            );
        }
    }
}

package com.thghpi.bandapp.band_api.service.connection;
import com.thghpi.bandapp.band_api.config.properties.PasswordProperties;

import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordChecker {
    private final PasswordProperties passwordProperties;

    protected void checkPasswordStrength(String password) {
        Pattern pattern = Pattern.compile(passwordProperties.regex());
        if (!pattern.matcher(password).matches()) {
            throw new IllegalArgumentException("Password does not meet the required criteria.");
        }
    }
}

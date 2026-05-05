package com.thghpi.bandapp.band_api.config;
import com.thghpi.bandapp.band_api.config.properties.JwtProperties;
import com.thghpi.bandapp.band_api.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

import com.thghpi.bandapp.band_api.config.properties.CorsProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({
    JwtProperties.class,
    CorsProperties.class
})
public class AppConfiguration {


}

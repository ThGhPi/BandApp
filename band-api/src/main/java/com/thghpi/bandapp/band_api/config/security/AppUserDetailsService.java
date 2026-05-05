package com.thghpi.bandapp.band_api.config.security;
import com.thghpi.bandapp.band_api.repository.PersonRepository;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService {
    private final PersonRepository personRepository;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> personRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

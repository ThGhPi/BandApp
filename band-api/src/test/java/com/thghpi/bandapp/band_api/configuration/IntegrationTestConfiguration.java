package com.thghpi.bandapp.band_api.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;

import org.testcontainers.containers.PostgreSQLContainerProvider;

@TestConfiguration(proxyBeanMethods = false)
public class IntegrationTestConfiguration {
    @Bean
    @ServiceConnection
    PostgreSQLContainerProvider postgresContainer() {
        return new PostgreSQLContainerProvider();
    }
}

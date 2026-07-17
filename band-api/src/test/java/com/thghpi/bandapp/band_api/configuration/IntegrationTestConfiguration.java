package com.thghpi.bandapp.band_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import org.testcontainers.postgresql.PostgreSQLContainer;

/**
 * IntegrationTestConfiguration is a Spring Test Configuration class that sets up a PostgreSQLContainer for integration testing.
 * It provides a PostgreSQLContainer bean that can be used to run integration tests against a real Postgre database
 */
@TestConfiguration(proxyBeanMethods = false)
public class IntegrationTestConfiguration {

    /**
     * Provides a PostgreSQLContainer bean for integration testing.
     * This container is configured to use the "postgres:18" Docker image.
     * @return a PostgreSQLContainer instance
     */
    @Bean
    @ServiceConnection
    PostgreSQLContainer postgresContainer() {
        return new PostgreSQLContainer("postgres:18");
    }
}

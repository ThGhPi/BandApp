package com.thghpi.bandapp.band_api.integration;
import com.thghpi.bandapp.band_api.configuration.IntegrationTestConfiguration;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Import(IntegrationTestConfiguration.class)
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

}

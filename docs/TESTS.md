# Testing

## 1. Testing strategy

BandApp uses a layered testing strategy to verify the reliability of its backend application. Tests are organized according to the components being tested and the level of integration required.

The main objectives are to:
- Verify that business rules and application logic behave as expected.
- Ensure that application components interact correctly.
- Validate database operations and data persistence.
- Check that the REST API returns the expected responses.
- Verify that authentication and authorization mechanisms protect restricted resources.

Two main categories of automated tests are used:

- **Unit testing**: focused on individual components in isolation to verify service-layer business logic, entity behaviour and data transformation performed by mappers.
- **Integration testing**: focused on interaction of border components within Spring application context to test REST API endpoints, repository operations and interactions with the PostgreSQL database. Testcontainers provides an isolated database environment for tests that require a real database.

### Security testing

Security-related behaviour is tested to ensure that authentication and authorization rules are correctly enforced. These tests cover the protection of API resources and access restrictions associated with user roles.

Security tests may be performed at both unit and integration levels, depending on the component and behaviour being tested.

This approach aims to identify errors at different levels of the application while keeping tests understandable, maintainable and reproducible.

## 2. Test technologies

The backend testing environment relies on several libraries from the Java and Spring ecosystems. These tools provide support for unit testing, mocking, application integration testing, HTTP request simulation and database testing.

### 2.1 JUnit 5 (JUnit Jupiter)

JUnit Jupiter is used as the main testing framework.

It is used to define test methods, initialize test environments and verify expected results using its annotations and assertion methods.

### 2.2 Mockito

Mockito is used to create mock objects and isolate components during unit testing.

It allows dependencies such as repositories and other services to be replaced with simulated objects whose behaviour can be configured for each test.

Dependencies such as repositories and other services can be replaced with simulated objects whose behaviour is configured for each test.

The project uses:
- `@Mock` and `@InjectMocks` to create mock dependencies and inject mock them into the component being tested.
- `MockitoExtension` to integrate Mockito with JUnit Jupiter.
- Methods such as `when()` and `doNothing()` to configure mock behaviour.

This allows the testing of business logic independently of external dependencies.

### 2.3 Spring Boot Test and Spring Test

Spring Boot Test and Spring Test provide tools for testing Spring applications.

They are used at different levels of the testing process:
- `@SpringBootTest` loads the Spring application context for integration tests.
- `@ContextConfiguration` allows specific application components to be loaded into a restricted test context, as used in mapper tests.
- `SpringExtension` integrates the Spring TestContext framework with JUnit Jupiter.
- `@TestConfiguration` and `@Import` allow dedicated configuration to be provided for tests.

These tools make it possible to test Spring-managed components without requiring the same configuration for every test.

### 2.4 Spring MVC Test (MockMvc)

MockMvc, provided by Spring Test, is used to test the REST API by simulating HTTP requests without starting a real HTTP server.

It is used in controller integration tests to:
- Simulate HTTP methods such as GET, POST, PUT and DELETE.
- Verify HTTP response status codes.
- Inspect JSON response bodies using JSONPath expressions.
- Check returned values and collections using Hamcrest matchers.
- Print request and response details to facilitate debugging.

`ObjectMapper`, from the Jackson library, is also used to process JSON data in tests.

### 2.5 Hamcrest and JSONPath

Hamcrest provides matchers that make assertions more expressive.

JSONPath is used to navigate JSON response bodies and verify individual properties or collections of values returned by API endpoints.

### 2.6 Spring Security Test

Spring Security Test provides utilities for testing security-related behaviour.

It allows authentication contexts to be simulated and helps verify access restrictions and authorization rules.

### 2.7 Testcontainers and relateed Postgres module

Testcontainers provides temporary containerized services for automated integration tests.

The project uses Testcontainers to start a PostgreSQL database in an isolated container environment. This allows database integration tests to run against the same database engine as the application rather than relying exclusively on mocked repositories.

The PostgreSQL container is declared as a Spring bean in a dedicated test configuration and annotated with `@ServiceConnection`, allowing Spring Boot to automatically configure the database connection for the test environment.

This approach facilitates reproducible database integration tests without requiring a separately installed PostgreSQL instance.

## 3. Test organization

## 4. Unit tests

### 4.1 Services
### 4.2 Mappers
### 4.3 Other components

## 5. Integration tests

### 5.1 Repository tests
### 5.2 Controller/API tests
### 5.3 Database integration

## 6. Authentication and authorization tests

## 7. Test data and test configuration

## 8. Running the tests

### 8.1 All tests
### 8.2 Unit tests only

First launch the Docker environment for executing api unit tests (for app context test)

```bash
docker compose -f docker-compose.test.yml up -d
```

Then launch api unit tests

```bash
cd band-api
mvn test
```

### 8.3 Integration tests only

Launch api integration tests

```bash
cd band-api
mvn failsafe:integration-test failsafe:verify
```

### 8.4 Specific test class
### 8.5 Specific test method

## 9. Test coverage

## 10. Known limitations

## 11. Future improvements

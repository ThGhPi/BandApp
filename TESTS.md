# Testing

## 1. Testing strategy

## 2. Test technologies

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

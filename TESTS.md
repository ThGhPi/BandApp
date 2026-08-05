## Docker environment for executing api unit tests (for app context test)

```bash
docker compose -f docker-compose.test.yml up -d
```

## Launch api unit tests

```bash
cd band-api
mvn test
```

## Launch api integration tests

```bash
cd band-api
mvn failsafe:integration-test failsafe:verify
```

## Complete build

```bash
cd band-api
mvn verify
```

## Skip testing

```bash
cd band-api
mvn verify -Dskiptests
```
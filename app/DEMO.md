# Demo: greeter (Spring Boot)

**Framework:** Spring Boot 3.5 on Java 21, with Maven. **Moving parts locally:** none. It is stateless.

One endpoint, plus Prometheus metrics. Argo Rollouts and OpenCost need metrics to judge a canary, and Spring Boot's Actuator provides them with one dependency.

## Run it

```bash
cd app
mvn test                  # first run downloads dependencies, which takes a few minutes
mvn spring-boot:run       # http://localhost:8080
```

## Try it

```bash
curl 'localhost:8080/greeting?name=Sam'               # Hello Sam from demo
TENANT_NAME=payments-team mvn spring-boot:run          # now says "from payments-team"
curl localhost:8080/actuator/prometheus | head         # metrics in Prometheus format
curl localhost:8080/actuator/health
```

Make a few `/greeting` calls, then look for `http_server_requests_seconds_count` in the metrics output. That is the series a canary analysis queries.

## Read the code in this order

1. `application.properties`: exposes the actuator endpoints and reads `TENANT_NAME` from the environment.
2. `GreetingController`: `@Value` injects config, `@RequestParam` reads the query string.
3. `GreetingControllerTest`: includes a check that the metrics endpoint is exposed.

## Docker

```bash
docker build -t greeter . && docker run -p 8080:8080 -e TENANT_NAME=team-a greeter
```

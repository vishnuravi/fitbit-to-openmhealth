# Fitbit to Open mHealth Converter
Spring Boot microservice that converts data obtained from Fitbit's Web API into [Open mHealth](https://openmhealth.org) compliant JSON. Based on [Shimmer](https://github.com/openmhealth/shimmer).
Currently supports intraday heart rate and physical activity data.

## Installation and Usage
- Requires [Java 11](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed.

To build and run application:
1. `./gradlew build`
2. `./gradlew bootRun`

The application should be running on `http://localhost:8080`

### Converting Intraday Heart Rate
Execute a **POST** request with body containing JSON obtained from the Fitbit Web API's [Get Heart Rate Time Series endpoint](https://dev.fitbit.com/build/reference/web-api/heart-rate/) to `/heart-rate`.

### Converting Physical Activity
Execute a **POST** request with body containing JSON obtained from the Fitbit Web API's [Get Daily Activity endpoint](https://dev.fitbit.com/build/reference/web-api/activity/)  to `/physical-activity`.
# Fitbit to Open mHealth Converter
Spring Boot microservice that converts data obtained from [Fitbit's Web API](https://dev.fitbit.com/build/reference/web-api/) into [Open mHealth](https://openmhealth.org) compliant JSON. Based on [Shimmer](https://github.com/openmhealth/shimmer).
Currently supports intraday heart rate, intraday step count, and physical activity data.

## Installation and Usage
- Requires [JDK 11 or above](https://jdk.java.net/16/) installed.

### To build
`./gradlew build`

### To run
`./gradlew bootRun`

The application will run on `http://localhost:8080`

### Converting Intraday Heart Rate
Execute a **POST** request with body containing JSON obtained from the Fitbit Web API's [Get Heart Rate Time Series endpoint](https://dev.fitbit.com/build/reference/web-api/heart-rate/) to `/heart-rate`.

### Converting Intraday Step Count
Execute a **POST** request with body containing JSON obtained from the Fitbit Web API's [Get Activity Intraday Time Series endpoint](https://dev.fitbit.com/build/reference/web-api/activity/#activity-time-series)  to `/step-count`.

### Converting Physical Activity
Execute a **POST** request with body containing JSON obtained from the Fitbit Web API's [Get Daily Activity endpoint](https://dev.fitbit.com/build/reference/web-api/activity/)  to `/physical-activity`.
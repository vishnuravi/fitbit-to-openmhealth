# Fitbit to Open mHealth Converter
Spring Boot microservice that converts data obtained from Fitbit's Web API into [Open mHealth](https://openmhealth.org) compliant JSON. Based on [Shimmer](https://github.com/openmhealth/shimmer).
Currently supports intraday heart rate data.

## Steps
- Requires [Java 11](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed.

To build and run application:
1. `./gradlew build`
2. `./gradlew bootRun`

To convert *intraday heart rate* data, execute a *POST* request with body containing JSON obtained from the Fitbit Web API to `http://localhost:8080/heart-rate?granularity=N` where N represents the granularity in minutes of the data.

# Fitbit to Open mHealth Converter
Spring Boot microservice that converts data obtained from [Fitbit's Web API](https://dev.fitbit.com/build/reference/web-api/) into [Open mHealth](https://openmhealth.org) compliant JSON. Based on [Shimmer](https://github.com/openmhealth/shimmer).
Currently supports intraday heart rate, intraday step count, and physical activity data.

## Installation
- Requires [JDK 11 or above](https://jdk.java.net/16/) installed.

### To build
```
./gradlew build
```

### To test
```
./gradlew test
```

### To run
```
./gradlew bootRun
```

The application will start on `http://localhost:8080` by default.

## Usage

| Endpoint  | Description  | Method  |  Body   |
|---|---|---|---|
| /heart-rate/intraday  | Converts Fitbit heart rate (intraday) to OMH [heart-rate](https://www.openmhealth.org/schemas/omh_heart-rate/)   | POST | JSON from [Get Heart Rate Time Series](https://dev.fitbit.com/build/reference/web-api/heart-rate/) endpoint   | 
| /physical-activity  | Converts Fitbit daily activity summary to OMH [physical-activity](https://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_physical-activity)  | POST | JSON from [Get Daily Activity](https://dev.fitbit.com/build/reference/web-api/activity/) endpoint   |  
| /step-count/summary  | Converts Fitbit step count (intraday) to OMH [step-count](https://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_step-count)   | POST | JSON from [Get Activity Intraday Time Series](https://dev.fitbit.com/build/reference/web-api/activity/#activity-time-series) endpoint  | 
| /step-count/intraday | Converts Fitbit step count (summary) to OMH [step-count](https://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_step-count) | POST | JSON from [Get Activity Intraday Time Series](https://dev.fitbit.com/build/reference/web-api/activity/#activity-time-series) |
| /sleep/episode | Converts Fitbit sleep logs to OMH [sleep-episode](https://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_sleep-episode) | POST | JSON from [Get Sleep Logs](https://dev.fitbit.com/build/reference/web-api/sleep/#get-sleep-logs)

## To build a docker container
docker build -t fitbit2openmhealth/v1.0 .

## To run the same locally 
docker run -p 8080:8080 fitbit2openmhealth/v1.0

## To run the same in google cloudrun
docker tag fitbit2openmhealth/v1.0 gcr.io/\[gcp-project\]/fitbit2openmhealth:v1.0

docker push gcr.io/\[gcp-project\]/fitbit2openmhealth:v1.0

gcloud beta run deploy --image gcr.io/\[gcp-project\]/fitbit2openmhealth:v1.0
 


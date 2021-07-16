package com.vishnuravi.fitbitOMH.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.openmhealth.mapper.fitbit.FitbitIntradayHeartRateDataPointMapper;
import org.openmhealth.schema.domain.omh.DataPoint;
import org.openmhealth.schema.domain.omh.HeartRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeartRateService {

    @Autowired
    public HeartRateService(){
    }

    /**
     * Converts a list of Fitbit intraday heart rate data points to Open mHealth heart-rate using {@link FitbitIntradayHeartRateDataPointMapper}.
     * @param jsonNode - JSON from Fitbit Web API
     * @return A list of {@link HeartRate} data points
     */
    public List<DataPoint<HeartRate>> mapIntradayHeartRate(JsonNode jsonNode){

        // Get granularity interval and units from JSON
        Integer intradayDataGranularityInterval = jsonNode.get("activities-heart-intraday").get("datasetInterval").asInt();
        String intradayDataGranularityUnits = jsonNode.get("activities-heart-intraday").get("datasetType").asText();

        FitbitIntradayHeartRateDataPointMapper dataPointMapper = new FitbitIntradayHeartRateDataPointMapper(intradayDataGranularityInterval, intradayDataGranularityUnits);
        return dataPointMapper.asDataPoints(jsonNode);
    }

}

package com.vishnuravi.fitbitOMH.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.openmhealth.mapper.fitbit.FitbitIntradayStepCountDataPointMapper;
import org.openmhealth.mapper.fitbit.FitbitStepCountDataPointMapper;
import org.openmhealth.schema.domain.omh.DataPoint;
import org.openmhealth.schema.domain.omh.StepCount2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepCountService {

    @Autowired
    public StepCountService(){
    }

    public List<DataPoint<StepCount2>> mapStepCount(JsonNode jsonNode){
        FitbitStepCountDataPointMapper dataPointMapper = new FitbitStepCountDataPointMapper();
        return dataPointMapper.asDataPoints(jsonNode);
    }

    public List<DataPoint<StepCount2>> mapIntradayStepCount(JsonNode jsonNode){

        // Get granularity from JSON
        Integer intradayDataGranularityInterval = jsonNode.get("activities-steps-intraday").get("datasetInterval").asInt();
        String intradayDataGranularityUnits = jsonNode.get("activities-steps-intraday").get("datasetType").asText();

        FitbitIntradayStepCountDataPointMapper dataPointMapper = new FitbitIntradayStepCountDataPointMapper(intradayDataGranularityInterval, intradayDataGranularityUnits);
        return dataPointMapper.asDataPoints(jsonNode);
    }

}

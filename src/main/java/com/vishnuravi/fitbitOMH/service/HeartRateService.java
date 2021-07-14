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

    public List<DataPoint<HeartRate>> mapHeartRate(JsonNode jsonNode, Integer granularity){
        FitbitIntradayHeartRateDataPointMapper dataPointMapper = new FitbitIntradayHeartRateDataPointMapper(granularity);
        return dataPointMapper.asDataPoints(jsonNode);
    }

}

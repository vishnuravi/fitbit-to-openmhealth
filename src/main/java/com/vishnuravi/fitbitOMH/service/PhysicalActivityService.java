package com.vishnuravi.fitbitOMH.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.openmhealth.mapper.fitbit.FitbitPhysicalActivityDataPointMapper;
import org.openmhealth.schema.domain.omh.DataPoint;
import org.openmhealth.schema.domain.omh.PhysicalActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhysicalActivityService {

    @Autowired
    public PhysicalActivityService(){
    }

    /**
     * Converts Fitbit daily activity summary to Open mHealth physical-activity using {@link FitbitPhysicalActivityDataPointMapper}.
     * @param jsonNode - JSON from Fitbit Web API
     * @return A List of {@link PhysicalActivity} data points
     */
    public List<DataPoint<PhysicalActivity>> mapPhysicalActivity(JsonNode jsonNode){
        FitbitPhysicalActivityDataPointMapper dataPointMapper = new FitbitPhysicalActivityDataPointMapper();
        return dataPointMapper.asDataPoints(jsonNode);
    }

}

package com.vishnuravi.fitbitOMH.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.vishnuravi.fitbitOMH.service.HeartRateService;
import org.openmhealth.schema.domain.omh.DataPoint;
import org.openmhealth.schema.domain.omh.HeartRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/heart-rate")
public class HeartRateController {

    @Autowired
    private HeartRateService heartRateService;

    /**
     * Converts Fitbit intraday heart rate to Open mHealth heart-rate
     * @param jsonNode - JSON from Fitbit Web API
     * @return A List of {@link HeartRate} data points
     */
    @PostMapping("/intraday")
    public List<DataPoint<HeartRate>> mapIntradayHeartRateToOMH(@RequestBody JsonNode jsonNode){
        return heartRateService.mapIntradayHeartRate(jsonNode);
    }
}

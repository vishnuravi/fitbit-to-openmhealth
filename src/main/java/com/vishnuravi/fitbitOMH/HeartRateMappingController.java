package com.vishnuravi.fitbitOMH;

import com.fasterxml.jackson.databind.JsonNode;
import org.openmhealth.schema.domain.omh.DataPoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.openmhealth.mapper.fitbit.*;

import java.util.List;

@RestController
@RequestMapping("/heart-rate")
public class HeartRateMappingController {

    Integer intradayDataGranularityInMinutes = 1;

    @PostMapping
    public List<?> mapHeartRateToOMH(@RequestBody JsonNode jsonNode){

        FitbitDataPointMapper<?> dataPointMapper = new FitbitIntradayHeartRateDataPointMapper(intradayDataGranularityInMinutes);
        List<? extends DataPoint<?>> dataPoints = dataPointMapper.asDataPoints(jsonNode);

        return dataPoints;
    }
}

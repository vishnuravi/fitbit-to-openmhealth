package com.vishnuravi.fitbitOMH.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.openmhealth.mapper.fitbit.FitbitSleepEpisodeDataPointMapper;
import org.openmhealth.schema.domain.omh.DataPoint;
import org.openmhealth.schema.domain.omh.SleepEpisode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SleepService {

    @Autowired
    public SleepService() {
    }

    /**
     * Converts Fitbit sleep logs to Open mHealth sleep-episode using {@link FitbitSleepEpisodeDataPointMapper}.
     * @param jsonNode - JSON from Fitbit Web API
     * @return A List of {@link SleepEpisode} data points
     */
    public List<DataPoint<SleepEpisode>> mapSleepEpisode(JsonNode jsonNode){
        FitbitSleepEpisodeDataPointMapper dataPointMapper = new FitbitSleepEpisodeDataPointMapper();
        return dataPointMapper.asDataPoints(jsonNode);
    }
}

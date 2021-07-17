package com.vishnuravi.fitbitOMH.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.vishnuravi.fitbitOMH.service.SleepService;
import org.openmhealth.schema.domain.omh.DataPoint;
import org.openmhealth.schema.domain.omh.SleepDuration2;
import org.openmhealth.schema.domain.omh.SleepEpisode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sleep")
public class SleepController {

    @Autowired
    private SleepService sleepService;

    @PostMapping("/duration")
    public List<DataPoint<SleepDuration2>> mapSleepDurationToOMH(@RequestBody JsonNode jsonNode){
        return sleepService.mapSleepDuration(jsonNode);
    }

    @PostMapping("/episode")
    public List<DataPoint<SleepEpisode>> mapSleepEpisodeToOMH(@RequestBody JsonNode jsonNode){
        return sleepService.mapSleepEpisode(jsonNode);
    }

}

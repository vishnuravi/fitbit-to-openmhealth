package com.vishnuravi.fitbitOMH.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.vishnuravi.fitbitOMH.service.StepCountService;
import org.openmhealth.schema.domain.omh.DataPoint;
import org.openmhealth.schema.domain.omh.StepCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/step-count")
public class StepCountController {

    @Autowired
    private StepCountService stepCountService;

    @PostMapping
    public List<DataPoint<StepCount>> mapStepCountToOMH(@RequestBody JsonNode jsonNode){
        return stepCountService.mapStepCount(jsonNode);
    }

}

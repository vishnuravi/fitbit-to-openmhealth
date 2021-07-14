package com.vishnuravi.fitbitOMH.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.vishnuravi.fitbitOMH.service.PhysicalActivityService;
import org.openmhealth.schema.domain.omh.DataPoint;
import org.openmhealth.schema.domain.omh.PhysicalActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/physical-activity")
public class PhysicalActivityController {

    @Autowired
    private PhysicalActivityService physicalActivityService;

    @PostMapping
    public List<DataPoint<PhysicalActivity>> mapPhysicalActivityToOMH(@RequestBody JsonNode jsonNode){
        return physicalActivityService.mapPhysicalActivity(jsonNode);
    }
}

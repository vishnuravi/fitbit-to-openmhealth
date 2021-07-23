/*
 * Copyright 2015 Open mHealth
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openmhealth.mapper.fitbit;

import com.fasterxml.jackson.databind.JsonNode;
import org.openmhealth.schema.domain.omh.DataPoint;
import org.openmhealth.schema.domain.omh.DurationUnit;
import org.openmhealth.schema.domain.omh.DurationUnitValue;
import org.openmhealth.schema.domain.omh.HeartRate;
import org.openmhealth.mapper.common.DataPointMapperUnitTests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.TimeInterval.ofStartDateTimeAndDuration;
import static org.openmhealth.mapper.fitbit.FitbitDataPointMapper.RESOURCE_API_SOURCE_NAME;


/**
 * @author Wallace Wadge
 *
 * Modified by Vishnu Ravi (2021)
 *
 */
public class FitbitIntradayHeartRateDataPointMapperUnitTests extends DataPointMapperUnitTests {

    private JsonNode responseNode;
    private FitbitIntradayHeartRateDataPointMapper mapper = new FitbitIntradayHeartRateDataPointMapper(1, "minute");


    @BeforeTest
    public void initializeResponseNode() throws IOException {

        responseNode = asJsonNode("/test-data/heart-rate-intraday-test.json");
    }

    @Test
    public void asDataPointsShouldReturnCorrectNumberOfDataPoints() {

        assertThat(mapper.asDataPoints(singletonList(responseNode)).size(), equalTo(5));
    }

    @Test
    public void asDataPointsShouldSetExternalId() {

        final List<DataPoint<HeartRate>> dataPoints = mapper.asDataPoints(singletonList(responseNode));

        for (DataPoint<?> dataPoint : dataPoints) {
            assertThat(
                    dataPoint.getHeader().getAcquisitionProvenance().getAdditionalProperties().get("external_id"),
                    is(not(nullValue())));
        }
    }

    @Test
    public void asDataPointsShouldSetUserId(){

        final List<DataPoint<HeartRate>> dataPoints = mapper.asDataPoints((singletonList(responseNode)));

        for (DataPoint<?> dataPoint : dataPoints) {
            assertThat(
                    dataPoint.getHeader().getUserId(),
                    is(equalTo("123456")));
        }
    }

    @Test
    public void asDataPointsShouldReturnCorrectDataPoints() {

        List<DataPoint<HeartRate>> dataPoints = mapper.asDataPoints(singletonList(responseNode));
        HeartRate.Builder heartRateBuilder = new HeartRate.Builder(76)
                .setEffectiveTimeFrame(ofStartDateTimeAndDuration(OffsetDateTime.parse("2021-07-21T00:01Z"),
                        new DurationUnitValue(DurationUnit.MINUTE, 1)));

        assertThat(dataPoints.get(0).getBody(), equalTo(heartRateBuilder.build()));
        assertThat(dataPoints.get(0).getHeader().getBodySchemaId(), equalTo(HeartRate.SCHEMA_ID));
        assertThat(dataPoints.get(0).getHeader().getAcquisitionProvenance().getSourceName(),
                equalTo(RESOURCE_API_SOURCE_NAME));

    }

    @Test
    public void asDataPointsShouldReturnNoDataPointsWhenDataSetArrayIsEmpty() throws IOException {

        JsonNode emptyDataSetNode = objectMapper.readTree(
                "{\n" +
                        "\"activities-heart\": [ \n" +
                        "{\n" +
                        "\"dateTime\": \"2021-07-21\"\n," +
                        "\"value\": 0\n" +
                        "}\n" +
                        "],\n" +
                        "\"activities-heart-intraday\": {\n" +
                        "\"dataset\": [],\n" +
                        "\"datasetInterval\": 1,\n" +
                        "\"datasetType\": \"minute\"\n" +
                        "}\n" +
                        "}");

        List<DataPoint<HeartRate>> dataPoints = mapper.asDataPoints(singletonList(emptyDataSetNode));
        assertThat(dataPoints.isEmpty(), equalTo(true));
    }
}

/*
 * Copyright 2017 Open mHealth
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

import org.openmhealth.schema.domain.omh.DataPoint;
import org.openmhealth.schema.domain.omh.DurationUnitValue;
import org.openmhealth.schema.domain.omh.SleepEpisode;
import org.openmhealth.schema.domain.omh.TypedUnitValue;
import org.testng.annotations.Test;
import org.openmhealth.mapper.common.DataPointMapperUnitTests;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DurationUnit.MINUTE;
import static org.openmhealth.schema.domain.omh.PercentUnit.PERCENT;
import static org.openmhealth.schema.domain.omh.TimeInterval.ofStartDateTimeAndEndDateTime;


/**
 * @author Emerson Farrugia
 *
 * Modified by Vishnu Ravi (2021)
 *
 */
public class FitbitSleepEpisodeDataPointMapperUnitTests extends DataPointMapperUnitTests {

    private final FitbitSleepEpisodeDataPointMapper mapper = new FitbitSleepEpisodeDataPointMapper();

    protected JsonNode sleepDateResponseNode;

    @Test
    public void asDataPointsShouldReturnCorrectDataPoints() {

        sleepDateResponseNode = asJsonNode("/test-data/sleep-logs-test.json");

        SleepEpisode expectedSleepEpisode = new SleepEpisode.Builder(
                ofStartDateTimeAndEndDateTime(
                        OffsetDateTime.parse("2021-08-12T01:20:30Z"),
                        OffsetDateTime.parse("2021-08-12T06:39:30Z")
                ))
                .setLatencyToSleepOnset(new DurationUnitValue(MINUTE, 0))
                .setLatencyToArising(new DurationUnitValue(MINUTE, 0))
                .setTotalSleepTime(new DurationUnitValue(MINUTE, 284))
                .setMainSleep(true)
                .setSleepMaintenanceEfficiencyPercentage(new TypedUnitValue<>(PERCENT, 97))
                .build();

        List<DataPoint<SleepEpisode>> dataPoints = mapper.asDataPoints(sleepDateResponseNode);

        DataPoint<SleepEpisode> dataPoint = dataPoints.get(0);

        assertThat(dataPoint.getBody(), equalTo(expectedSleepEpisode));
        assertThat(dataPoint.getHeader().getBodySchemaId(), equalTo(SleepEpisode.SCHEMA_ID));
        assertThat(dataPoint.getHeader().getAcquisitionProvenance().getSourceName(),
                equalTo(FitbitDataPointMapper.RESOURCE_API_SOURCE_NAME));
    }
}

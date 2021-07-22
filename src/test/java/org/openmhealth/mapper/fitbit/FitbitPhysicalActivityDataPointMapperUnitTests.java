package org.openmhealth.mapper.fitbit;

import com.fasterxml.jackson.databind.JsonNode;
import org.openmhealth.schema.domain.omh.*;
import org.openmhealth.mapper.common.DataPointMapperUnitTests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.openmhealth.schema.domain.omh.DurationUnit.DAY;
import static org.openmhealth.schema.domain.omh.DurationUnit.MILLISECOND;
import static org.openmhealth.schema.domain.omh.KcalUnit.KILOCALORIE;
import static org.openmhealth.schema.domain.omh.LengthUnit.KILOMETER;


/**
 * @author Chris Schaefbauer
 * @author Emerson Farrugia
 *
 * Modified by Vishnu Ravi (2021)
 *
 */
public class FitbitPhysicalActivityDataPointMapperUnitTests extends DataPointMapperUnitTests {

    private final FitbitPhysicalActivityDataPointMapper mapper = new FitbitPhysicalActivityDataPointMapper();
    private JsonNode singleActivityResponseNode;
    private JsonNode multipleActivityResponseNode;

    @BeforeTest
    public void initializeResponseNodes() throws IOException {

        singleActivityResponseNode =
                asJsonNode("org/openmhealth/shim/fitbit/mapper/fitbit-activities-date-single-in-activities-list.json");
        multipleActivityResponseNode =
                asJsonNode(
                        "org/openmhealth/shim/fitbit/mapper/fitbit-activities-date-multiple-in-activities-list.json");
    }

    @Test
    public void asDataPointsShouldReturnEmptyListWhenResponseIsEmpty() throws IOException {

        JsonNode emptyNode =
                asJsonNode("org/openmhealth/shim/fitbit/mapper/fitbit-activities-date-empty-activities-list.json");

        assertThat(mapper.asDataPoints(emptyNode), is(empty()));
    }

    @Test
    public void asDataPointsShouldReturnCorrectNumberOfDataPoints() {

        assertThat(mapper.asDataPoints(singleActivityResponseNode).size(), equalTo(1));
        assertThat(mapper.asDataPoints(multipleActivityResponseNode).size(), equalTo(3));
    }

    @Test
    public void asDataPointsShouldReturnCorrectDataPointForSingleActivity() {

        List<DataPoint<PhysicalActivity>> dataPoints = mapper.asDataPoints(singleActivityResponseNode);

        assertThatDataPointMatches(dataPoints.get(0), "Walk", "2021-06-19", "09:00", 3.36, 3600000L, 79441095L, 128.0);
    }

    @Test
    public void asDataPointsShouldReturnCorrectDataPointsForMultipleActivities() {

        List<DataPoint<PhysicalActivity>> dataPoints = mapper.asDataPoints(multipleActivityResponseNode);

        assertThatDataPointMatches(dataPoints.get(0), "Run", "2021-07-22", "11:55", 6.43738, 1440000L, 253202765L,
                150.0);
        assertThatDataPointMatches(dataPoints.get(1), "Swimming", "2021-07-21", "10:00", null, null, 253246706L, null);
        assertThatDataPointMatches(dataPoints.get(2), "Walk", "2021-07-21", 6.43738, 253202766L, 200.0);
    }

    public void assertThatDataPointMatches(DataPoint<PhysicalActivity> dataPoint, String expectedActivityName,
            String expectedEffectiveDate, Double expectedDistance, Long expectedExternalId,
            Double expectedCaloriesBurned) {

        OffsetDateTime expectedEffectiveStartDateTime =
                OffsetDateTime.of(LocalDate.parse(expectedEffectiveDate).atStartOfDay(), UTC);

        TimeFrame expectedTimeFrame = new TimeFrame(
                TimeInterval.ofStartDateTimeAndDuration(expectedEffectiveStartDateTime, new DurationUnitValue(DAY, 1)));

        assertThatDataPointMatches(dataPoint, expectedActivityName, expectedTimeFrame, expectedDistance,
                expectedExternalId, expectedCaloriesBurned);
    }

    public void assertThatDataPointMatches(DataPoint<PhysicalActivity> dataPoint, String expectedActivityName,
            TimeFrame expectedTimeFrame, Double expectedDistance, Long expectedExternalId,
            Double expectedCaloriesBurned) {

        PhysicalActivity.Builder expectedMeasureBuilder = new PhysicalActivity.Builder(expectedActivityName);

        expectedMeasureBuilder.setEffectiveTimeFrame(expectedTimeFrame);

        if (expectedDistance != null) {
            expectedMeasureBuilder.setDistance(new LengthUnitValue(KILOMETER, expectedDistance));
        }

        if (expectedCaloriesBurned != null) {
            expectedMeasureBuilder.setCaloriesBurned(new KcalUnitValue(KILOCALORIE, expectedCaloriesBurned));
        }

        PhysicalActivity expectedPhysicalActivity = expectedMeasureBuilder.build();

        assertThat(dataPoint.getBody(), equalTo(expectedPhysicalActivity));
        assertThat(dataPoint.getHeader().getBodySchemaId(), equalTo(PhysicalActivity.SCHEMA_ID));
        assertThat(dataPoint.getHeader().getAcquisitionProvenance().getAdditionalProperties().get("external_id"),
                equalTo(expectedExternalId));
        assertThat(dataPoint.getHeader().getAcquisitionProvenance().getSourceName(),
                equalTo(FitbitDataPointMapper.RESOURCE_API_SOURCE_NAME));
    }

    public void assertThatDataPointMatches(DataPoint<PhysicalActivity> dataPoint, String expectedActivityName,
            String expectedEffectiveDate, String expectedEffectiveStartTime, Double expectedDistance,
            Long expectedDurationInMs, Long expectedExternalId, Double expectedCaloriesBurned) {

        OffsetDateTime expectedEffectiveStartDateTime = OffsetDateTime
                .of(LocalDate.parse(expectedEffectiveDate), LocalTime.parse(expectedEffectiveStartTime), UTC);

        TimeFrame expectedTimeFrame;

        if (expectedDurationInMs != null) {
            DurationUnitValue expectedDuration = new DurationUnitValue(MILLISECOND, expectedDurationInMs);

            expectedTimeFrame = new TimeFrame(
                    TimeInterval.ofStartDateTimeAndDuration(expectedEffectiveStartDateTime, expectedDuration));
        }
        else {
            expectedTimeFrame = new TimeFrame(expectedEffectiveStartDateTime);
        }

        assertThatDataPointMatches(dataPoint, expectedActivityName, expectedTimeFrame, expectedDistance,
                expectedExternalId, expectedCaloriesBurned);
    }
}

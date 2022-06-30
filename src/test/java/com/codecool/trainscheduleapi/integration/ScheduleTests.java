package com.codecool.trainscheduleapi.integration;

import com.codecool.trainscheduleapi.DTO.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ScheduleTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String url = "/schedule";

    @Test
    void testGetPassengerTrainSchedule() {
        TrainSelectionDTO[] trainsToPost = { new TrainSelectionDTO(1L, "InterCity"),
                new TrainSelectionDTO(2L, "InterCity"),
                new TrainSelectionDTO(3L, "Freight"),
                new TrainSelectionDTO(4L, "Freight")};

        for (TrainSelectionDTO trainToPost : trainsToPost) {
            postTrainToURL(trainToPost, "/train");
        }

        StopSelectionDTO[] stopsToPost = { new StopSelectionDTO("irrelevantStop1", 0, null, new Time(0L), 1),
                new StopSelectionDTO("irrelevantStop2", 5, new Time(1000L), new Time(4000L), 2),
                new StopSelectionDTO("departureStopName", 0, null, new Time(0L), 1),
                new StopSelectionDTO("arrivalStopName", 5, new Time(1000L), new Time(4000L), 2),
                new StopSelectionDTO("departureStopName", 0, null, new Time(0L), 1),
                new StopSelectionDTO("arrivalStopName", 5, new Time(1000L), new Time(4000L), 2)};

        for (StopSelectionDTO stopToPost : stopsToPost) {
            postStopToURL(stopToPost, "/stop");
        }

        addStopToTrain(new TrainStopDTO(1L, 3L), "/train/addStop");
        addStopToTrain(new TrainStopDTO(1L, 4L), "/train/addStop");
        addStopToTrain(new TrainStopDTO(2L, 5L), "/train/addStop");
        addStopToTrain(new TrainStopDTO(2L, 6L), "/train/addStop");
        addStopToTrain(new TrainStopDTO(3L, 1L), "/train/addStop");
        addStopToTrain(new TrainStopDTO(4L, 2L), "/train/addStop");

        ResponseEntity<TrainDTO[]> responseTrains = testRestTemplate.getForEntity("/train", TrainDTO[].class);

        ResponseEntity<ScheduleDTO[]> responseSchedule = testRestTemplate.getForEntity(url + "/passenger/departureStopName/arrivalStopName", ScheduleDTO[].class);

        ScheduleDTO[] expectedSchedule = {
                new ScheduleDTO(new Time(0L),
                        "departureStopName",
                        new Time(1000L),
                        "arrivalStopName",
                        "00:00:01",
                        5,
                        Objects.requireNonNull(responseTrains.getBody())[0]),
                new ScheduleDTO(new Time(0L),
                        "departureStopName",
                        new Time(1000L),
                        "arrivalStopName",
                        "00:00:01",
                        5,
                        Objects.requireNonNull(responseTrains.getBody())[1])
        };

        assertEquals(HttpStatus.OK, responseSchedule.getStatusCode());
        assertArraysHasSameElements(expectedSchedule, responseSchedule.getBody());
    }

    @Test
    void testGetFreightTrainSchedule() {
        TrainSelectionDTO[] trainsToPost = { new TrainSelectionDTO(1L, "InterCity"),
                new TrainSelectionDTO(2L, "InterCity"),
                new TrainSelectionDTO(3L, "Freight"),
                new TrainSelectionDTO(4L, "Freight")};

        for (TrainSelectionDTO trainToPost : trainsToPost) {
            postTrainToURL(trainToPost, "/train");
        }

        StopSelectionDTO[] stopsToPost = { new StopSelectionDTO("irrelevantStop1", 0, null, new Time(0L), 1),
                new StopSelectionDTO("irrelevantStop2", 5, new Time(1000L), new Time(4000L), 2),
                new StopSelectionDTO("departureStopName", 0, null, new Time(0L), 1),
                new StopSelectionDTO("arrivalStopName", 5, new Time(1000L), new Time(4000L), 2),
                new StopSelectionDTO("departureStopName", 0, null, new Time(0L), 1),
                new StopSelectionDTO("arrivalStopName", 5, new Time(1000L), new Time(4000L), 2)};

        for (StopSelectionDTO stopToPost : stopsToPost) {
            postStopToURL(stopToPost, "/stop");
        }

        addStopToTrain(new TrainStopDTO(3L, 3L), "/train/addStop");
        addStopToTrain(new TrainStopDTO(3L, 4L), "/train/addStop");
        addStopToTrain(new TrainStopDTO(4L, 5L), "/train/addStop");
        addStopToTrain(new TrainStopDTO(4L, 6L), "/train/addStop");
        addStopToTrain(new TrainStopDTO(1L, 1L), "/train/addStop");
        addStopToTrain(new TrainStopDTO(2L, 2L), "/train/addStop");

        ResponseEntity<TrainDTO[]> responseTrains = testRestTemplate.getForEntity("/train", TrainDTO[].class);

        ResponseEntity<ScheduleDTO[]> responseSchedule = testRestTemplate.getForEntity(url + "/freight/departureStopName/arrivalStopName", ScheduleDTO[].class);

        ScheduleDTO[] expectedSchedule = {
                new ScheduleDTO(new Time(0L),
                        "departureStopName",
                        new Time(1000L),
                        "arrivalStopName",
                        "00:00:01",
                        5,
                        Objects.requireNonNull(responseTrains.getBody())[2]),
                new ScheduleDTO(new Time(0L),
                        "departureStopName",
                        new Time(1000L),
                        "arrivalStopName",
                        "00:00:01",
                        5,
                        Objects.requireNonNull(responseTrains.getBody())[3])
        };

        assertEquals(HttpStatus.OK, responseSchedule.getStatusCode());
        assertArraysHasSameElements(expectedSchedule, responseSchedule.getBody());
    }

    private void postTrainToURL(TrainSelectionDTO train, String url) {
        HttpEntity<TrainSelectionDTO> httpEntity = createHttpEntity(train);
        testRestTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<TrainSelectionDTO> createHttpEntity(TrainSelectionDTO train) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(train, httpHeaders);
    }

    private void postStopToURL(StopSelectionDTO stop, String url) {
        HttpEntity<StopSelectionDTO> httpEntity = createHttpEntity(stop);
        testRestTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<StopSelectionDTO> createHttpEntity(StopSelectionDTO stop) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(stop, httpHeaders);
    }

    private void addStopToTrain(TrainStopDTO trainStopDTO, String url) {
        HttpEntity<TrainStopDTO> httpEntity = createHttpEntity(trainStopDTO);
        testRestTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<TrainStopDTO> createHttpEntity(TrainStopDTO trainStopDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(trainStopDTO, httpHeaders);
    }

    private void assertArraysHasSameElements(ScheduleDTO[] expectedArr, ScheduleDTO[] actualArr){
        List<ScheduleDTO> expected = Arrays.asList(expectedArr);
        List<ScheduleDTO> actual = Arrays.asList(actualArr);
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));
    }
}

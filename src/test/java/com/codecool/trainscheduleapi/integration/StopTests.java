package com.codecool.trainscheduleapi.integration;

import com.codecool.trainscheduleapi.DTO.StopDTO;
import com.codecool.trainscheduleapi.DTO.StopSelectionDTO;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class StopTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String url = "/stop";

    @Test
    void testFindAll() {
        StopSelectionDTO[] stopsToPost = { new StopSelectionDTO("stopName1", 0, null, new Time(0L), 1),
                new StopSelectionDTO("stopName2", 5, new Time(1000L), new Time(4000L), 2)};

        StopDTO[] expectedStops = { new StopDTO(1L, null, 0, "stopName1", null, new Time(0L), 1),
                new StopDTO(2L, null, 5, "stopName2", new Time(1000L), new Time(4000L), 2)};

        for (StopSelectionDTO stopToPost : stopsToPost) {
            postStopToURL(stopToPost, url);
        }

        ResponseEntity<StopDTO[]> responseEntity = testRestTemplate.getForEntity(url, StopDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArraysHasSameElements(expectedStops, responseEntity.getBody());
    }

    @Test
    void testFindById() {
        StopSelectionDTO stopToPost = new StopSelectionDTO("stopName1", 0, null, new Time(0L), 1);

        StopDTO expectedStop = new StopDTO(1L, null, 0, "stopName1", null, new Time(0L), 1);

        postStopToURL(stopToPost, url);

        ResponseEntity<StopDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", StopDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedStop, responseEntity.getBody());
    }

    @Test
    void testSave() {
        assertEquals(0, Objects.requireNonNull(testRestTemplate.getForEntity(url, StopDTO[].class).getBody()).length);

        StopSelectionDTO stopToPost = new StopSelectionDTO("stopName1", 0, null, new Time(0L), 1);

        StopDTO expectedStop = new StopDTO(1L, null, 0, "stopName1", null, new Time(0L), 1);

        postStopToURL(stopToPost, url);

        ResponseEntity<StopDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", StopDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedStop, responseEntity.getBody());

        assertEquals(1, Objects.requireNonNull(testRestTemplate.getForEntity(url, StopDTO[].class).getBody()).length);
    }

    @Test
    void testUpdate() {
        StopSelectionDTO stopToPost = new StopSelectionDTO("stopName1", 0, null, new Time(0L), 1);

        StopDTO expectedStop = new StopDTO(1L, null, 110, "stopNameUpdated", new Time(1000L), new Time(5000L), 6);

        postStopToURL(stopToPost, url);

        stopToPost.setDistance(expectedStop.getDistance());
        stopToPost.setName(expectedStop.getName());
        stopToPost.setArrivalTime(expectedStop.getArrivalTime());
        stopToPost.setDepartureTime(expectedStop.getDepartureTime());
        stopToPost.setPlatform(expectedStop.getPlatform());

        HttpEntity<StopSelectionDTO> httpEntity = createHttpEntity(stopToPost);
        testRestTemplate.put(url + "/1", httpEntity, String.class);

        ResponseEntity<StopDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", StopDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedStop, responseEntity.getBody());
    }

    @Test
    void testDeleteById() {
        StopSelectionDTO stopToPost = new StopSelectionDTO("stopName1", 0, null, new Time(0L), 1);

        StopDTO expectedStop = new StopDTO(1L, null, 0, "stopName1", null, new Time(0L), 1);

        postStopToURL(stopToPost, url);

        ResponseEntity<StopDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", StopDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedStop, responseEntity.getBody());

        testRestTemplate.delete(url + "/1");

        responseEntity = testRestTemplate.getForEntity(url + "/1", StopDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
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

    private void assertArraysHasSameElements(StopDTO[] expectedArr, StopDTO[] actualArr){
        List<StopDTO> expected = Arrays.asList(expectedArr);
        List<StopDTO> actual = Arrays.asList(actualArr);
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));
    }
}
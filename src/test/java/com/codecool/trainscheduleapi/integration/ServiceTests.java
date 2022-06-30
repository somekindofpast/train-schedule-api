package com.codecool.trainscheduleapi.integration;

import com.codecool.trainscheduleapi.DTO.ServiceDTO;
import com.codecool.trainscheduleapi.DTO.ServiceSelectionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ServiceTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String url = "/service";

    @Test
    void testFindAll() {
        ServiceSelectionDTO[] servicesToPost = { new ServiceSelectionDTO(true, true, true,
                false, false, false, false,
                false, false),
        new ServiceSelectionDTO(true, true, true, true,
                true, true, true, true, true)
        };

        ServiceDTO[] expectedServices = { new ServiceDTO(1L, null, true, true, true,
                false, false, false, false,
                false, false),
                new ServiceDTO(2L, null, true, true, true, true,
                        true, true, true, true, true)
        };

        for (ServiceSelectionDTO serviceToPost : servicesToPost) {
            postServiceToURL(serviceToPost, url);
        }

        ResponseEntity<ServiceDTO[]> responseEntity = testRestTemplate.getForEntity(url, ServiceDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArraysHasSameElements(expectedServices, responseEntity.getBody());
    }

    @Test
    void testFindById() {
        ServiceSelectionDTO serviceToPost = new ServiceSelectionDTO(true, true, true,
                false, false, false, false,
                false, false);

        ServiceDTO expectedService = new ServiceDTO(1L, null, true, true, true,
                false, false, false, false,
                false, false);

        postServiceToURL(serviceToPost, url);

        ResponseEntity<ServiceDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", ServiceDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedService, responseEntity.getBody());
    }

    @Test
    void testSave() {
        assertEquals(0, Objects.requireNonNull(testRestTemplate.getForEntity(url, ServiceDTO[].class).getBody()).length);

        ServiceSelectionDTO serviceToPost = new ServiceSelectionDTO(true, true, true,
                false, false, false, false,
                false, false);

        ServiceDTO expectedService = new ServiceDTO(1L, null, true, true, true,
                false, false, false, false,
                false, false);

        postServiceToURL(serviceToPost, url);

        ResponseEntity<ServiceDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", ServiceDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedService, responseEntity.getBody());

        assertEquals(1, Objects.requireNonNull(testRestTemplate.getForEntity(url, ServiceDTO[].class).getBody()).length);
    }

    @Test
    void testUpdate() {
        ServiceSelectionDTO serviceToPost = new ServiceSelectionDTO(false, false, false,
                false, false, false, false,
                false, false);

        ServiceDTO expectedService = new ServiceDTO(1L, null, true, true, true,
                true, true, true, true,
                true, true);

        postServiceToURL(serviceToPost, url);

        serviceToPost.setAnyWeatherCondition(true);
        serviceToPost.setBicycleReservation(true);
        serviceToPost.setBudapestPass(true);
        serviceToPost.setFirstClass(true);
        serviceToPost.setSecondClass(true);
        serviceToPost.setLongDistance(true);
        serviceToPost.setReservationCompulsory(true);
        serviceToPost.setSupplementCompulsory(true);
        serviceToPost.setWheelchairAccess(true);

        HttpEntity<ServiceSelectionDTO> httpEntity = createHttpEntity(serviceToPost);
        testRestTemplate.put(url + "/1", httpEntity, String.class);

        ResponseEntity<ServiceDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", ServiceDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedService, responseEntity.getBody());
    }

    @Test
    void testDeleteById() {
        ServiceSelectionDTO serviceToPost = new ServiceSelectionDTO(true, true, true,
                false, false, false, false,
                false, false);

        ServiceDTO expectedService = new ServiceDTO(1L, null, true, true, true,
                false, false, false, false,
                false, false);

        postServiceToURL(serviceToPost, url);

        ResponseEntity<ServiceDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", ServiceDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedService, responseEntity.getBody());

        testRestTemplate.delete(url + "/1");

        responseEntity = testRestTemplate.getForEntity(url + "/1", ServiceDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    private void postServiceToURL(ServiceSelectionDTO service, String url) {
        HttpEntity<ServiceSelectionDTO> httpEntity = createHttpEntity(service);
        testRestTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<ServiceSelectionDTO> createHttpEntity(ServiceSelectionDTO service) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(service, httpHeaders);
    }

    private void assertArraysHasSameElements(ServiceDTO[] expectedArr, ServiceDTO[] actualArr){
        List<ServiceDTO> expected = Arrays.asList(expectedArr);
        List<ServiceDTO> actual = Arrays.asList(actualArr);
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));
    }
}

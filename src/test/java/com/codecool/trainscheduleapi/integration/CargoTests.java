package com.codecool.trainscheduleapi.integration;

import com.codecool.trainscheduleapi.DTO.CargoDTO;
import com.codecool.trainscheduleapi.DTO.CargoSelectionDTO;
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
public class CargoTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String url = "/cargo";

    @Test
    void testFindAll() {

        CargoSelectionDTO[] cargosToPost = {	new CargoSelectionDTO("testName1", "testCarType1"),
                new CargoSelectionDTO("testName2", "testCarType2")};

        CargoDTO[] expectedCargos = { new CargoDTO(1L, null, "testName1", "testCarType1"),
                new CargoDTO(2L, null, "testName2", "testCarType2")};

        for (CargoSelectionDTO cargoToPost : cargosToPost) {
            postCargoToURL(cargoToPost, url);
        }

        ResponseEntity<CargoDTO[]> responseEntity = testRestTemplate.getForEntity(url, CargoDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArraysHasSameElements(expectedCargos, responseEntity.getBody());
    }

    @Test
    void testFindById() {
        CargoSelectionDTO cargoToPost = new CargoSelectionDTO("testName1", "testCarType1");

        CargoDTO expectedCargo = new CargoDTO(1L, null, "testName1", "testCarType1");

        postCargoToURL(cargoToPost, url);

        ResponseEntity<CargoDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", CargoDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCargo, responseEntity.getBody());
    }

    @Test
    void testFindByName() {
        CargoSelectionDTO[] cargosToPost = {	new CargoSelectionDTO("testName1", "testCarType1"),
                new CargoSelectionDTO("testName2", "testCarType2"),
                new CargoSelectionDTO("testNameToLookFor", "testCarType3"),
                new CargoSelectionDTO("testName4", "testCarType4"),
                new CargoSelectionDTO("testNameToLookFor", "testCarType3")};

        CargoDTO[] expectedCargos = { new CargoDTO(3L, null, "testNameToLookFor", "testCarType3"),
                new CargoDTO(5L, null, "testNameToLookFor", "testCarType3")};

        for (CargoSelectionDTO cargoToPost : cargosToPost) {
            postCargoToURL(cargoToPost, url);
        }

        ResponseEntity<CargoDTO[]> responseEntity = testRestTemplate.getForEntity(url + "/name/testNameToLookFor", CargoDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArraysHasSameElements(expectedCargos, responseEntity.getBody());
    }

    @Test
    void testSave() {
        assertEquals(0, Objects.requireNonNull(testRestTemplate.getForEntity(url, CargoDTO[].class).getBody()).length);

        CargoSelectionDTO cargoToPost = new CargoSelectionDTO("testName1", "testCarType1");

        CargoDTO expectedCargo = new CargoDTO(1L, null, "testName1", "testCarType1");

        postCargoToURL(cargoToPost, url);

        ResponseEntity<CargoDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", CargoDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCargo, responseEntity.getBody());

        assertEquals(1, Objects.requireNonNull(testRestTemplate.getForEntity(url, CargoDTO[].class).getBody()).length);
    }

    @Test
    void testUpdate() {
        CargoSelectionDTO cargoToPost = new CargoSelectionDTO("testName1", "testCarType1");

        CargoDTO expectedCargo = new CargoDTO(1L, null, "testNameUpdated", "testCarTypeUpdated");

        postCargoToURL(cargoToPost, url);

        cargoToPost.setName(expectedCargo.getName());
        cargoToPost.setCarType(expectedCargo.getCarType());

        HttpEntity<CargoSelectionDTO> httpEntity = createHttpEntity(cargoToPost);
        testRestTemplate.put(url + "/1", httpEntity, String.class);

        ResponseEntity<CargoDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", CargoDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCargo, responseEntity.getBody());
    }

    @Test
    void testDeleteById() {
        CargoSelectionDTO cargoToPost = new CargoSelectionDTO("testName1", "testCarType1");

        CargoDTO expectedCargo = new CargoDTO(1L, null, "testName1", "testCarType1");

        postCargoToURL(cargoToPost, url);

        ResponseEntity<CargoDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", CargoDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCargo, responseEntity.getBody());

        testRestTemplate.delete(url + "/1");

        responseEntity = testRestTemplate.getForEntity(url + "/1", CargoDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    private void postCargoToURL(CargoSelectionDTO cargo, String url) {
        HttpEntity<CargoSelectionDTO> httpEntity = createHttpEntity(cargo);
        testRestTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<CargoSelectionDTO> createHttpEntity(CargoSelectionDTO cargo) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(cargo, httpHeaders);
    }

    private void assertArraysHasSameElements(CargoDTO[] expectedArr, CargoDTO[] actualArr){
        List<CargoDTO> expected = Arrays.asList(expectedArr);
        List<CargoDTO> actual = Arrays.asList(actualArr);
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));
    }
}

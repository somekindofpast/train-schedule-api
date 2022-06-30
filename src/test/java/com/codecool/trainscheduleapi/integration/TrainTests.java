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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TrainTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String url = "/train";

    @Test
    void testFindAll() {
        TrainSelectionDTO[] trainsToPost = { new TrainSelectionDTO(1L, "InterCity"),
                new TrainSelectionDTO(2L, "Freight")};

        TrainDTO[] expectedTrains = { new TrainDTO(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>()),
                new TrainDTO(2L, "Freight", new ArrayList<>(), null, new ArrayList<>())};

        for (TrainSelectionDTO trainToPost : trainsToPost) {
            postTrainToURL(trainToPost, url);
        }

        ResponseEntity<TrainDTO[]> responseEntity = testRestTemplate.getForEntity(url, TrainDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArraysHasSameElements(expectedTrains, responseEntity.getBody());
    }

    @Test
    void testFindById() {
        TrainSelectionDTO trainToPost = new TrainSelectionDTO(1L, "InterCity");

        TrainDTO expectedTrain = new TrainDTO(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>());

        postTrainToURL(trainToPost, url);

        ResponseEntity<TrainDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", TrainDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedTrain, responseEntity.getBody());
    }

    @Test
    void testFindFreightTrainsByCargoName() {
        TrainSelectionDTO[] trainsToPost = { new TrainSelectionDTO(1L, "InterCity"),
                new TrainSelectionDTO(2L, "Freight"),
                new TrainSelectionDTO(3L, "Freight")};

        for (TrainSelectionDTO trainToPost : trainsToPost) {
            postTrainToURL(trainToPost, url);
        }

        CargoSelectionDTO[] cargosToPost = {	new CargoSelectionDTO("cargoName", "carType"),
                new CargoSelectionDTO("cargoName", "carType")};

        for (CargoSelectionDTO cargoToPost : cargosToPost) {
            postCargoToURL(cargoToPost, "/cargo");
        }

        addCargoToTrain(new TrainCargoDTO(2L, 1L), url + "/addCargo");
        addCargoToTrain(new TrainCargoDTO(3L, 2L), url + "/addCargo");

        ResponseEntity<CargoDTO[]> responseCargos = testRestTemplate.getForEntity("/cargo", CargoDTO[].class);
        assertEquals(2, Objects.requireNonNull(responseCargos.getBody()).length);

        List<CargoDTO> freightTrain1Cargos = new ArrayList<>();
        List<CargoDTO> freightTrain2Cargos = new ArrayList<>();

        freightTrain1Cargos.add(responseCargos.getBody()[0]);
        freightTrain2Cargos.add(responseCargos.getBody()[1]);

        TrainDTO[] expectedTrains = { new TrainDTO(2L, "Freight", new ArrayList<>(), null, freightTrain1Cargos),
                new TrainDTO(3L, "Freight", new ArrayList<>(), null, freightTrain2Cargos)};

        ResponseEntity<TrainDTO[]> responseFreightTrainsByCargoName = testRestTemplate.getForEntity(url + "/freight/cargoName", TrainDTO[].class);

        assertEquals(HttpStatus.OK, responseFreightTrainsByCargoName.getStatusCode());
        assertArraysHasSameElements(expectedTrains, responseFreightTrainsByCargoName.getBody());
    }

    @Test
    void testSave() {
        assertEquals(0, Objects.requireNonNull(testRestTemplate.getForEntity(url, TrainDTO[].class).getBody()).length);

        TrainSelectionDTO trainToPost = new TrainSelectionDTO(1L, "InterCity");

        TrainDTO expectedTrain = new TrainDTO(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>());

        postTrainToURL(trainToPost, url);

        ResponseEntity<TrainDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", TrainDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedTrain, responseEntity.getBody());

        assertEquals(1, Objects.requireNonNull(testRestTemplate.getForEntity(url, TrainDTO[].class).getBody()).length);
    }

    @Test
    void testSaveAddStop() {
        TrainSelectionDTO trainToPost = new TrainSelectionDTO(1L, "InterCity");
        postTrainToURL(trainToPost, url);

        StopSelectionDTO stopToPost = new StopSelectionDTO("stopName1", 0, null, new Time(0L), 1);
        postStopToURL(stopToPost, "/stop");

        addStopToTrain(new TrainStopDTO(1L, 1L), url + "/addStop");

        ResponseEntity<TrainDTO> responseTrain = testRestTemplate.getForEntity(url + "/1", TrainDTO.class);

        ResponseEntity<StopDTO> responseStop = testRestTemplate.getForEntity("/stop/1", StopDTO.class);

        assertEquals(responseStop.getBody(), Objects.requireNonNull(responseTrain.getBody()).getStops().get(0));
    }

    @Test
    void testSaveAddService() {
        TrainSelectionDTO trainToPost = new TrainSelectionDTO(1L, "InterCity");
        postTrainToURL(trainToPost, url);

        ServiceSelectionDTO serviceToPost = new ServiceSelectionDTO(true, true, true,
                false, false, false, false,
                false, false);
        postServiceToURL(serviceToPost, "/service");

        addServiceToTrain(new TrainServiceDTO(1L, 1L), url + "/addService");

        ResponseEntity<TrainDTO> responseTrain = testRestTemplate.getForEntity(url + "/1", TrainDTO.class);

        ResponseEntity<ServiceDTO> responseService = testRestTemplate.getForEntity("/service/1", ServiceDTO.class);

        assertEquals(responseService.getBody(), Objects.requireNonNull(responseTrain.getBody()).getService());
    }

    @Test
    void testSaveAddCargo() {
        TrainSelectionDTO trainToPost = new TrainSelectionDTO(1L, "InterCity");
        postTrainToURL(trainToPost, url);

        CargoSelectionDTO cargoToPost = new CargoSelectionDTO("testName1", "testCarType1");
        postCargoToURL(cargoToPost, "/cargo");

        addCargoToTrain(new TrainCargoDTO(1L, 1L), url + "/addCargo");

        ResponseEntity<TrainDTO> responseTrain = testRestTemplate.getForEntity(url + "/1", TrainDTO.class);

        ResponseEntity<CargoDTO> responseCargo = testRestTemplate.getForEntity("/cargo/1", CargoDTO.class);

        assertEquals(responseCargo.getBody(), Objects.requireNonNull(responseTrain.getBody()).getCargos().get(0));
    }

    @Test
    void testUpdate() {
        TrainSelectionDTO trainToPost = new TrainSelectionDTO(1L, "InterCity");

        TrainDTO expectedTrain = new TrainDTO(1L, "Freight", new ArrayList<>(), null, new ArrayList<>());

        postTrainToURL(trainToPost, url);

        trainToPost.setType("Freight");

        HttpEntity<TrainSelectionDTO> httpEntity = createHttpEntity(trainToPost);
        testRestTemplate.put(url + "/1", httpEntity, String.class);

        ResponseEntity<TrainDTO> responseEntity = testRestTemplate.getForEntity(url + "/1", TrainDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedTrain, responseEntity.getBody());
    }

    @Test
    void testDeleteById() {
        TrainSelectionDTO trainToPost = new TrainSelectionDTO(1L, "InterCity");
        postTrainToURL(trainToPost, url);

        StopSelectionDTO stopToPost = new StopSelectionDTO("stopName1", 0, null, new Time(0L), 1);
        postStopToURL(stopToPost, "/stop");

        addStopToTrain(new TrainStopDTO(1L, 1L), url + "/addStop");

        ResponseEntity<TrainDTO> responseTrain = testRestTemplate.getForEntity(url + "/1", TrainDTO.class);

        ResponseEntity<StopDTO> responseStop = testRestTemplate.getForEntity("/stop/1", StopDTO.class);

        StopDTO expectedStop = new StopDTO(1L, 1L, 0, "stopName1", null, new Time(0L), 1);
        assertEquals(HttpStatus.OK, responseStop.getStatusCode());
        assertEquals(expectedStop, responseStop.getBody());

        List<StopDTO> trainStops = new ArrayList<>();
        trainStops.add(responseStop.getBody());

        TrainDTO expectedTrain = new TrainDTO(1L, "InterCity", trainStops, null, new ArrayList<>());
        assertEquals(HttpStatus.OK, responseTrain.getStatusCode());
        assertEquals(expectedTrain, responseTrain.getBody());

        testRestTemplate.delete(url + "/1");

        responseTrain = testRestTemplate.getForEntity(url + "/1", TrainDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseTrain.getStatusCode());
        assertNull(responseTrain.getBody());

        responseStop = testRestTemplate.getForEntity("/stop/1", StopDTO.class);
        expectedStop.setTrainId(null);
        assertEquals(HttpStatus.OK, responseStop.getStatusCode());
        assertEquals(expectedStop, responseStop.getBody());
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

    private void postServiceToURL(ServiceSelectionDTO service, String url) {
        HttpEntity<ServiceSelectionDTO> httpEntity = createHttpEntity(service);
        testRestTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<ServiceSelectionDTO> createHttpEntity(ServiceSelectionDTO service) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(service, httpHeaders);
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

    private void addStopToTrain(TrainStopDTO trainStopDTO, String url) {
        HttpEntity<TrainStopDTO> httpEntity = createHttpEntity(trainStopDTO);
        testRestTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<TrainStopDTO> createHttpEntity(TrainStopDTO trainStopDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(trainStopDTO, httpHeaders);
    }

    private void addServiceToTrain(TrainServiceDTO trainServiceDTO, String url) {
        HttpEntity<TrainServiceDTO> httpEntity = createHttpEntity(trainServiceDTO);
        testRestTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<TrainServiceDTO> createHttpEntity(TrainServiceDTO trainServiceDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(trainServiceDTO, httpHeaders);
    }

    private void addCargoToTrain(TrainCargoDTO trainCargoDTO, String url) {
        HttpEntity<TrainCargoDTO> httpEntity = createHttpEntity(trainCargoDTO);
        testRestTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<TrainCargoDTO> createHttpEntity(TrainCargoDTO trainCargoDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(trainCargoDTO, httpHeaders);
    }

    private void assertArraysHasSameElements(TrainDTO[] expectedArr, TrainDTO[] actualArr){
        List<TrainDTO> expected = Arrays.asList(expectedArr);
        List<TrainDTO> actual = Arrays.asList(actualArr);
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));
    }
}

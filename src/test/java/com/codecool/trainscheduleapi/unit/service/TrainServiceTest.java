package com.codecool.trainscheduleapi.unit.service;

import com.codecool.trainscheduleapi.DTO.*;
import com.codecool.trainscheduleapi.controller.TrainController;
import com.codecool.trainscheduleapi.entity.Cargo;
import com.codecool.trainscheduleapi.entity.Service;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.repository.CargoRepository;
import com.codecool.trainscheduleapi.repository.ServiceRepository;
import com.codecool.trainscheduleapi.repository.StopRepository;
import com.codecool.trainscheduleapi.repository.TrainRepository;
import com.codecool.trainscheduleapi.service.TrainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainServiceTest {

    @InjectMocks
    private TrainService trainService;

    @Mock
    private TrainRepository trainRepository;
    @Mock
    private StopRepository stopRepository;
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private CargoRepository cargoRepository;

    @Test
    void testFindAll() {
        List<Train> expectedTrains = new ArrayList<>();
        Train train1 = new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>());
        Train train2 = new Train(2L, "Freight", new ArrayList<>(), null, new ArrayList<>());

        expectedTrains.add(train1);
        expectedTrains.add(train2);

        when(trainRepository.findAll()).thenReturn(expectedTrains);

        List<TrainDTO> trainDTOList = trainService.findAll();

        assertEquals(2, trainDTOList.size());
        assertEquals(1L, trainDTOList.get(0).getId());
        assertEquals(2L, trainDTOList.get(1).getId());

        verify(trainRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Optional<Train> train = Optional.of(new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>()));

        when(trainRepository.findById(1L)).thenReturn(train);

        Optional<Train> resultTrain = trainService.findById(1L);

        assertTrue(resultTrain.isPresent());
        assertEquals(1L, resultTrain.get().getId());

        verify(trainRepository, times(1)).findById(1L);
    }

    @Test
    void testFindFreightTrainsByCargoName() {
        Train train = new Train(1L, "Freight", new ArrayList<>(), null, new ArrayList<>());
        TrainDTO expectedTrainDTO = new TrainDTO(1L, "Freight", new ArrayList<>(), null, new ArrayList<>());

        List<Cargo> expectedCargo = new ArrayList<>();
        Cargo cargo1 = new Cargo(1L, null, "cargo5", "carType5");
        Cargo cargo2 = new Cargo(2L, train, "cargo5", "carType5");
        Cargo cargo3 = new Cargo(3L, train, "cargo5", "carType5");

        expectedCargo.add(cargo1);
        expectedCargo.add(cargo2);
        expectedCargo.add(cargo3);

        when(cargoRepository.findCargoByName("cargo5")).thenReturn(expectedCargo);

        Set<TrainDTO> resultTrains = trainService.findFreightTrainsByCargoName("cargo5");

        assertNotNull(resultTrains);
        assertEquals(1, resultTrains.size());
        assertTrue(resultTrains.contains(expectedTrainDTO));
    }

    @Test
    void testSave() {
        TrainSelectionDTO trainSelection = new TrainSelectionDTO(1L, "InterCity");
        Train train = new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>());

        when(trainRepository.save(train)).thenReturn(new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>()));

        TrainDTO resultTrain = trainService.save(trainSelection);

        assertNotNull(resultTrain);
        assertEquals(1L, resultTrain.getId());

        verify(trainRepository, times(1)).save(train);
    }

    @Test
    void testSaveAddStop_withExistingIds_expect_addStopToTrain() {
        Optional<Train> train = Optional.of(new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>()));

        when(trainRepository.findById(1L)).thenReturn(train);

        Optional<Stop> stop = Optional.of(new Stop(1L, null, 0, "stopName1", null, new Time(0L), 1));

        when(stopRepository.findById(1L)).thenReturn(stop);

        stop.get().setTrain(train.get());

        when(stopRepository.save(stop.get())).thenReturn(new Stop(1L, train.get(), 0, "stopName1", null, new Time(0L), 1));

        ResponseEntity<?> responseEntity = trainService.saveAddStop(new TrainStopDTO(1L, 1L), LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new StopDTO(1L, 1L, 0, "stopName1", null, new Time(0L), 1), responseEntity.getBody());

        verify(trainRepository, times(1)).findById(1L);
        verify(stopRepository, times(1)).findById(1L);
        verify(stopRepository, times(1)).save(stop.get());
    }

    @Test
    void testSaveAddStop_withUnknownTrainId_expect_error() {
        when(trainRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = trainService.saveAddStop(new TrainStopDTO(1L, 1L), LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(trainRepository, times(1)).findById(1L);
        verify(stopRepository, times(0)).findById(1L);
        verify(stopRepository, times(0)).save(any());
    }

    @Test
    void testSaveAddStop_withUnknownStopId_expect_error() {
        Optional<Train> train = Optional.of(new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>()));

        when(trainRepository.findById(1L)).thenReturn(train);

        when(stopRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = trainService.saveAddStop(new TrainStopDTO(1L, 1L), LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(trainRepository, times(1)).findById(1L);
        verify(stopRepository, times(1)).findById(1L);
        verify(stopRepository, times(0)).save(any());
    }

    @Test
    void testSaveAddService_withExistingIds_expect_addServiceToTrain() {
        Optional<Train> train = Optional.of(new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>()));

        when(trainRepository.findById(1L)).thenReturn(train);

        Optional<Service> service = Optional.of(new Service(1L, null, true, true, true,
                true, true, true, true,
                true, true));

        when(serviceRepository.findById(1L)).thenReturn(service);

        service.get().setTrain(train.get());

        when(serviceRepository.save(service.get())).thenReturn(new Service(1L, train.get(), true, true, true,
                true, true, true, true,
                true, true));

        ResponseEntity<?> responseEntity = trainService.saveAddService(new TrainServiceDTO(1L, 1L), LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new ServiceDTO(1L, 1L, true, true, true,
                true, true, true, true,
                true, true), responseEntity.getBody());

        verify(trainRepository, times(1)).findById(1L);
        verify(serviceRepository, times(1)).findById(1L);
        verify(serviceRepository, times(1)).save(service.get());
    }

    @Test
    void testSaveAddService_withUnknownTrainId_expect_error() {
        when(trainRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = trainService.saveAddService(new TrainServiceDTO(1L, 1L), LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(trainRepository, times(1)).findById(1L);
        verify(serviceRepository, times(0)).findById(1L);
        verify(serviceRepository, times(0)).save(any());
    }

    @Test
    void testSaveAddService_withUnknownServiceId_expect_error() {
        Optional<Train> train = Optional.of(new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>()));

        when(trainRepository.findById(1L)).thenReturn(train);

        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = trainService.saveAddService(new TrainServiceDTO(1L, 1L), LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(trainRepository, times(1)).findById(1L);
        verify(serviceRepository, times(1)).findById(1L);
        verify(serviceRepository, times(0)).save(any());
    }

    @Test
    void testSaveAddCargo_withExistingIds_expect_addCargoToTrain() {
        Optional<Train> train = Optional.of(new Train(1L, "Freight", new ArrayList<>(), null, new ArrayList<>()));

        when(trainRepository.findById(1L)).thenReturn(train);

        Optional<Cargo> cargo = Optional.of(new Cargo(1L, null, "cargo1", "carType1"));

        when(cargoRepository.findById(1L)).thenReturn(cargo);

        cargo.get().setTrain(train.get());

        when(cargoRepository.save(cargo.get())).thenReturn(new Cargo(1L, train.get(), "cargo1", "carType1"));

        ResponseEntity<?> responseEntity = trainService.saveAddCargo(new TrainCargoDTO(1L, 1L), LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new CargoDTO(1L, 1L, "cargo1", "carType1"), responseEntity.getBody());

        verify(trainRepository, times(1)).findById(1L);
        verify(cargoRepository, times(1)).findById(1L);
        verify(cargoRepository, times(1)).save(cargo.get());
    }

    @Test
    void testSaveAddCargo_withUnknownTrainId_expect_error() {
        when(trainRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = trainService.saveAddCargo(new TrainCargoDTO(1L, 1L), LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(trainRepository, times(1)).findById(1L);
        verify(cargoRepository, times(0)).findById(1L);
        verify(cargoRepository, times(0)).save(any());
    }

    @Test
    void testSaveAddCargo_withUnknownCargoId_expect_error() {
        Optional<Train> train = Optional.of(new Train(1L, "Freight", new ArrayList<>(), null, new ArrayList<>()));

        when(trainRepository.findById(1L)).thenReturn(train);

        when(cargoRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = trainService.saveAddCargo(new TrainCargoDTO(1L, 1L), LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(trainRepository, times(1)).findById(1L);
        verify(cargoRepository, times(1)).findById(1L);
        verify(cargoRepository, times(0)).save(any());
    }

    @Test
    void testUpdate_withExistingId_expect_update() {
        Optional<Train> trainOriginal = Optional.of(new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>()));

        when(trainRepository.findById(1L)).thenReturn(trainOriginal);

        TrainSelectionDTO trainSelection = new TrainSelectionDTO(1L, "InterRegio");
        Train trainUpdated = new Train(1L, "InterRegio", new ArrayList<>(), null, new ArrayList<>());
        TrainDTO expectedTrain = new TrainDTO(1L, "InterRegio", new ArrayList<>(), null, new ArrayList<>());

        when(trainRepository.save(trainOriginal.get())).thenReturn(trainUpdated);

        ResponseEntity<?> responseEntity = trainService.update(trainSelection, 1L, LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedTrain, responseEntity.getBody());

        verify(trainRepository, times(1)).findById(1L);
        verify(trainRepository, times(1)).save(trainUpdated);
    }

    @Test
    void testUpdate_withUnknownId_expect_error() {
        when(trainRepository.findById(2L)).thenReturn(Optional.empty());

        TrainSelectionDTO trainSelection = new TrainSelectionDTO(1L, "InterRegio");
        Train trainUpdated = new Train(1L, "InterRegio", new ArrayList<>(), null, new ArrayList<>());

        ResponseEntity<?> responseEntity = trainService.update(trainSelection, 2L, LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(trainRepository, times(1)).findById(2L);
        verify(trainRepository, times(0)).save(trainUpdated);
    }

    @Test
    void testDeleteById_withExistingId_expect_deletion() {
        Optional<Train> train = Optional.of(new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>()));

        when(trainRepository.findById(1L)).thenReturn(train);

        ResponseEntity<?> responseEntity = trainService.deleteById(1L, LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(trainRepository, times(1)).findById(1L);
        verify(trainRepository, times(1)).delete(train.get());
    }

    @Test
    void testDeleteById_withUnknownId_expect_error() {
        Optional<Train> train = Optional.of(new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>()));

        when(trainRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = trainService.deleteById(2L, LoggerFactory.getLogger(TrainController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(trainRepository, times(1)).findById(2L);
        verify(trainRepository, times(0)).delete(train.get());
    }
}

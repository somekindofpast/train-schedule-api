package com.codecool.trainscheduleapi.unit.service;

import com.codecool.trainscheduleapi.DTO.CargoDTO;
import com.codecool.trainscheduleapi.DTO.CargoSelectionDTO;
import com.codecool.trainscheduleapi.controller.CargoController;
import com.codecool.trainscheduleapi.entity.Cargo;
import com.codecool.trainscheduleapi.repository.CargoRepository;
import com.codecool.trainscheduleapi.service.CargoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CargoServiceTest {

    @InjectMocks
    private CargoService cargoService;

    @Mock
    private CargoRepository cargoRepository;

    @Test
    void testFindAll() {
        List<Cargo> expectedCargo = new ArrayList<>();
        Cargo cargo1 = new Cargo(1L, null, "cargo1", "carType1");
        Cargo cargo2 = new Cargo(2L, null, "cargo2", "carType2");

        expectedCargo.add(cargo1);
        expectedCargo.add(cargo2);

        when(cargoRepository.findAll()).thenReturn(expectedCargo);

        List<CargoDTO> cargoDTOList = cargoService.findAll();

        assertEquals(2, cargoDTOList.size());
        assertEquals(1L, cargoDTOList.get(0).getId());
        assertEquals(2L, cargoDTOList.get(1).getId());

        verify(cargoRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Optional<Cargo> cargo = Optional.of(new Cargo(1L, null, "cargo1", "carType1"));

        when(cargoRepository.findById(1L)).thenReturn(cargo);

        Optional<Cargo> resultCargo = cargoService.findById(1L);

        assertTrue(resultCargo.isPresent());
        assertEquals(1L, resultCargo.get().getId());

        verify(cargoRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByName() {
        List<Cargo> expectedCargo = new ArrayList<>();
        Cargo cargo1 = new Cargo(1L, null, "cargo5", "carType5");
        Cargo cargo2 = new Cargo(2L, null, "cargo5", "carType5");

        expectedCargo.add(cargo1);
        expectedCargo.add(cargo2);

        when(cargoRepository.findCargoByName("cargo5")).thenReturn(expectedCargo);

        List<CargoDTO> cargoDTOList = cargoService.findByName("cargo5");

        assertEquals(2, cargoDTOList.size());
        assertEquals(1L, cargoDTOList.get(0).getId());
        assertEquals("cargo5", cargoDTOList.get(0).getName());
        assertEquals(2L, cargoDTOList.get(1).getId());
        assertEquals("cargo5", cargoDTOList.get(1).getName());

        verify(cargoRepository, times(1)).findCargoByName("cargo5");
    }

    @Test
    void testSave() {
        CargoSelectionDTO cargoSelection = new CargoSelectionDTO("cargo1", "carType1");
        Cargo cargo = new Cargo(null, null, "cargo1", "carType1");

        when(cargoRepository.save(cargo)).thenReturn(new Cargo(1L, null, "cargo1", "carType1"));

        CargoDTO resultCargo = cargoService.save(cargoSelection);

        assertNotNull(resultCargo);
        assertEquals(1L, resultCargo.getId());

        verify(cargoRepository, times(1)).save(cargo);
    }

    @Test
    void testUpdate_withExistingId_expect_update() {
        Optional<Cargo> cargoOriginal = Optional.of(new Cargo(1L, null, "cargo1", "carType1"));

        when(cargoRepository.findById(1L)).thenReturn(cargoOriginal);

        CargoSelectionDTO cargoSelection = new CargoSelectionDTO("cargo2", "carType2");
        Cargo cargoUpdated = new Cargo(1L, null, "cargo2", "carType2");
        CargoDTO expectedCargo = new CargoDTO(1L, null, "cargo2", "carType2");

        when(cargoRepository.save(cargoOriginal.get())).thenReturn(cargoUpdated);

        ResponseEntity<?> responseEntity = cargoService.update(cargoSelection, 1L, LoggerFactory.getLogger(CargoController.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCargo, responseEntity.getBody());

        verify(cargoRepository, times(1)).findById(1L);
        verify(cargoRepository, times(1)).save(cargoUpdated);
    }

    @Test
    void testUpdate_withUnknownId_expect_error() {
        when(cargoRepository.findById(2L)).thenReturn(Optional.empty());

        CargoSelectionDTO cargoSelection = new CargoSelectionDTO("cargo2", "carType2");
        Cargo cargoUpdated = new Cargo(1L, null, "cargo2", "carType2");

        ResponseEntity<?> responseEntity = cargoService.update(cargoSelection, 2L, LoggerFactory.getLogger(CargoController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(cargoRepository, times(1)).findById(2L);
        verify(cargoRepository, times(0)).save(cargoUpdated);
    }

    @Test
    void testDeleteById_withExistingId_expect_deletion() {
        Optional<Cargo> cargo = Optional.of(new Cargo(1L, null, "cargo1", "carType1"));

        when(cargoRepository.findById(1L)).thenReturn(cargo);

        ResponseEntity<?> responseEntity = cargoService.deleteById(1L, LoggerFactory.getLogger(CargoController.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(cargoRepository, times(1)).findById(1L);
        verify(cargoRepository, times(1)).delete(cargo.get());
    }

    @Test
    void testDeleteById_withUnknownId_expect_error() {
        Optional<Cargo> cargo = Optional.of(new Cargo(1L, null, "cargo1", "carType1"));

        when(cargoRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = cargoService.deleteById(2L, LoggerFactory.getLogger(CargoController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(cargoRepository, times(1)).findById(2L);
        verify(cargoRepository, times(0)).delete(cargo.get());
    }
}

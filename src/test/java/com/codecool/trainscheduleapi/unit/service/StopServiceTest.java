package com.codecool.trainscheduleapi.unit.service;

import com.codecool.trainscheduleapi.DTO.StopDTO;
import com.codecool.trainscheduleapi.DTO.StopSelectionDTO;
import com.codecool.trainscheduleapi.controller.StopController;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.repository.StopRepository;
import com.codecool.trainscheduleapi.service.StopService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StopServiceTest {

    @InjectMocks
    private StopService stopService;

    @Mock
    private StopRepository stopRepository;

    @Test
    void testFindAll() {
        List<Stop> expectedStops = new ArrayList<>();
        Stop stop1 = new Stop(1L, null, 0, "stopName1", null, new Time(0L), 1);
        Stop stop2 = new Stop(2L, null, 5, "stopName2", new Time(1000L), new Time(5000L), 2);

        expectedStops.add(stop1);
        expectedStops.add(stop2);

        when(stopRepository.findAll()).thenReturn(expectedStops);

        List<StopDTO> stopDTOList = stopService.findAll();

        assertEquals(2, stopDTOList.size());
        assertEquals(1L, stopDTOList.get(0).getId());
        assertEquals(2L, stopDTOList.get(1).getId());

        verify(stopRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Optional<Stop> stop = Optional.of(new Stop(1L, null, 0, "stopName1", null, new Time(0L), 1));

        when(stopRepository.findById(1L)).thenReturn(stop);

        Optional<Stop> resultStop = stopService.findById(1L);

        assertTrue(resultStop.isPresent());
        assertEquals(1L, resultStop.get().getId());

        verify(stopRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        StopSelectionDTO stopSelection = new StopSelectionDTO("stopName1", 0, null, new Time(0L), 1);
        Stop stop = new Stop(null, null, 0, "stopName1", null, new Time(0L), 1);

        when(stopRepository.save(stop)).thenReturn(new Stop(1L, null, 0, "stopName1", null, new Time(0L), 1));

        StopDTO resultStop = stopService.save(stopSelection);

        assertNotNull(resultStop);
        assertEquals(1L, resultStop.getId());

        verify(stopRepository, times(1)).save(stop);
    }

    @Test
    void testUpdate_withExistingId_expect_update() {
        Optional<Stop> stopOriginal = Optional.of(new Stop(1L, null, 0, "stopName1", null, new Time(0L), 1));

        when(stopRepository.findById(1L)).thenReturn(stopOriginal);

        StopSelectionDTO stopSelection = new StopSelectionDTO("stopName2", 5, new Time(1000L), new Time(5000L), 2);
        Stop stopUpdated = new Stop(1L, null, 5, "stopName2", new Time(1000L), new Time(5000L), 2);
        StopDTO expectedStop = new StopDTO(1L, null, 5, "stopName2", new Time(1000L), new Time(5000L), 2);

        when(stopRepository.save(stopOriginal.get())).thenReturn(stopUpdated);

        ResponseEntity<?> responseEntity = stopService.update(stopSelection, 1L, LoggerFactory.getLogger(StopController.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedStop, responseEntity.getBody());

        verify(stopRepository, times(1)).findById(1L);
        verify(stopRepository, times(1)).save(stopUpdated);
    }

    @Test
    void testUpdate_withUnknownId_expect_error() {
        when(stopRepository.findById(2L)).thenReturn(Optional.empty());

        StopSelectionDTO stopSelection = new StopSelectionDTO("stopName2", 5, new Time(1000L), new Time(5000L), 2);
        Stop stopUpdated = new Stop(1L, null, 5, "stopName2", new Time(1000L), new Time(5000L), 2);

        ResponseEntity<?> responseEntity = stopService.update(stopSelection, 2L, LoggerFactory.getLogger(StopController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(stopRepository, times(1)).findById(2L);
        verify(stopRepository, times(0)).save(stopUpdated);
    }

    @Test
    void testDeleteById_withExistingId_expect_deletion() {
        Optional<Stop> stop = Optional.of(new Stop(1L, null, 0, "stopName1", null, new Time(0L), 1));

        when(stopRepository.findById(1L)).thenReturn(stop);

        ResponseEntity<?> responseEntity = stopService.deleteById(1L, LoggerFactory.getLogger(StopController.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(stopRepository, times(1)).findById(1L);
        verify(stopRepository, times(1)).delete(stop.get());
    }

    @Test
    void testDeleteById_withUnknownId_expect_error() {
        Optional<Stop> stop = Optional.of(new Stop(1L, null, 0, "stopName1", null, new Time(0L), 1));

        when(stopRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = stopService.deleteById(2L, LoggerFactory.getLogger(StopController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(stopRepository, times(1)).findById(2L);
        verify(stopRepository, times(0)).delete(stop.get());
    }
}

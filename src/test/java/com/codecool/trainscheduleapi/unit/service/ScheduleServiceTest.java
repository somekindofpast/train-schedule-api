package com.codecool.trainscheduleapi.unit.service;

import com.codecool.trainscheduleapi.DTO.ScheduleDTO;
import com.codecool.trainscheduleapi.DTO.TrainDTO;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.repository.StopRepository;
import com.codecool.trainscheduleapi.repository.TrainRepository;
import com.codecool.trainscheduleapi.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @InjectMocks
    ScheduleService scheduleService;

    @Mock
    TrainRepository trainRepository;
    @Mock
    StopRepository stopRepository;

    @Test
    void testListTrainSchedule_withPassengerTrainFlag_expect_listPassengerTrainSchedule() {
        String departureName = "departureName";
        String arrivalName = "arrivalName";

        List<Train> trains = new ArrayList<>();
        Train train1 = new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>());
        Train train2 = new Train(2L, "Freight", new ArrayList<>(), null, new ArrayList<>());

        trains.add(train1);
        trains.add(train2);

        Stop stop1 = new Stop(1L, train1, 0, departureName, null, new Time(0L), 1);
        Stop stop2 = new Stop(2L, train1, 5, arrivalName, new Time(1000L), new Time(5000L), 2);
        Stop stop3 = new Stop(3L, train2, 0, departureName, null, new Time(0L), 1);
        Stop stop4 = new Stop(4L, train2, 5, arrivalName, new Time(1000L), new Time(5000L), 2);

        train1.getStops().add(stop1);
        train1.getStops().add(stop2);
        train2.getStops().add(stop3);
        train2.getStops().add(stop4);

        when(trainRepository.getTrainsByStops(departureName, arrivalName)).thenReturn(trains);

        when(stopRepository.findStopByNameAndTrainId(departureName, train1.getId())).thenReturn(Optional.of(stop1));
        when(stopRepository.findStopByNameAndTrainId(arrivalName,   train1.getId())).thenReturn(Optional.of(stop2));

        List<ScheduleDTO> responseSchedule = scheduleService.listTrainSchedule(departureName, arrivalName, false);

        assertEquals(1, responseSchedule.size());
        assertEquals(new ScheduleDTO(new Time(0L), departureName, new Time(1000L), arrivalName, "00:00:01", 5, new TrainDTO(train1)), responseSchedule.get(0));

        verify(trainRepository, times(1)).getTrainsByStops(departureName, arrivalName);
        verify(stopRepository, times(1)).findStopByNameAndTrainId(departureName, train1.getId());
        verify(stopRepository, times(1)).findStopByNameAndTrainId(arrivalName, train1.getId());
        verify(stopRepository, times(0)).findStopByNameAndTrainId(departureName, train2.getId());
        verify(stopRepository, times(0)).findStopByNameAndTrainId(arrivalName, train2.getId());
    }

    @Test
    void testListTrainSchedule_withFreightTrainFlag_expect_listFreightTrainSchedule() {
        String departureName = "departureName";
        String arrivalName = "arrivalName";

        List<Train> trains = new ArrayList<>();
        Train train1 = new Train(1L, "InterCity", new ArrayList<>(), null, new ArrayList<>());
        Train train2 = new Train(2L, "Freight", new ArrayList<>(), null, new ArrayList<>());

        trains.add(train1);
        trains.add(train2);

        Stop stop1 = new Stop(1L, train1, 0, departureName, null, new Time(0L), 1);
        Stop stop2 = new Stop(2L, train1, 5, arrivalName, new Time(1000L), new Time(5000L), 2);
        Stop stop3 = new Stop(3L, train2, 0, departureName, null, new Time(0L), 1);
        Stop stop4 = new Stop(4L, train2, 5, arrivalName, new Time(1000L), new Time(5000L), 2);

        train1.getStops().add(stop1);
        train1.getStops().add(stop2);
        train2.getStops().add(stop3);
        train2.getStops().add(stop4);

        when(trainRepository.getTrainsByStops(departureName, arrivalName)).thenReturn(trains);

        when(stopRepository.findStopByNameAndTrainId(departureName, train2.getId())).thenReturn(Optional.of(stop3));
        when(stopRepository.findStopByNameAndTrainId(arrivalName,   train2.getId())).thenReturn(Optional.of(stop4));

        List<ScheduleDTO> responseSchedule = scheduleService.listTrainSchedule(departureName, arrivalName, true);

        assertEquals(1, responseSchedule.size());
        assertEquals(new ScheduleDTO(new Time(0L), departureName, new Time(1000L), arrivalName, "00:00:01", 5, new TrainDTO(train2)), responseSchedule.get(0));

        verify(trainRepository, times(1)).getTrainsByStops(departureName, arrivalName);
        verify(stopRepository, times(0)).findStopByNameAndTrainId(departureName, train1.getId());
        verify(stopRepository, times(0)).findStopByNameAndTrainId(arrivalName, train1.getId());
        verify(stopRepository, times(1)).findStopByNameAndTrainId(departureName, train2.getId());
        verify(stopRepository, times(1)).findStopByNameAndTrainId(arrivalName, train2.getId());
    }
}

package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.DTO.ScheduleDTO;
import com.codecool.trainscheduleapi.DTO.TrainDTO;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.repository.StopRepository;
import com.codecool.trainscheduleapi.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private TrainRepository trainRepository;
    private StopRepository stopRepository;

    @Autowired
    public ScheduleService(TrainRepository trainRepository, StopRepository stopRepository) {
        this.trainRepository = trainRepository;
        this.stopRepository = stopRepository;
    }

    public List<ScheduleDTO> listTrainSchedule(String departureLocation, String arrivalLocation, Boolean freight) {

        List<Train> trains =  trainRepository.getTrainsByStops(departureLocation, arrivalLocation);

        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        for (Train train : trains) {

            if((freight && !train.getType().equalsIgnoreCase("freight")) || (!freight && train.getType().equalsIgnoreCase("freight"))) {
                continue;
            }

            Optional<Stop> departure = stopRepository.findStopByNameAndTrainId(departureLocation, train.getId());
            Optional<Stop> arrival = stopRepository.findStopByNameAndTrainId(arrivalLocation, train.getId());

            if(departure.isEmpty() || arrival.isEmpty() || arrival.get().getDistance() < departure.get().getDistance())
                continue;

            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setDepartureTime(departure.get().getDepartureTime());
            scheduleDTO.setDepartureLocation(departure.get().getName());
            scheduleDTO.setArrivalTime(arrival.get().getArrivalTime());
            scheduleDTO.setArrivalLocation(arrival.get().getName());
            scheduleDTO.setTravelTime(MillisecondsToTimeUnits(scheduleDTO.getArrivalTime().getTime() - scheduleDTO.getDepartureTime().getTime()));
            scheduleDTO.setTravelDistance(arrival.get().getDistance() - departure.get().getDistance());

            scheduleDTO.setTrainDTO(new TrainDTO(train));
            scheduleDTOList.add(scheduleDTO);
        }

        return scheduleDTOList.stream()
                .sorted(Comparator.comparing(ScheduleDTO :: getDepartureTime))
                .collect(Collectors.toList());
    }

    private String MillisecondsToTimeUnits(long milliSeconds) {
        int seconds = (int) (milliSeconds / 1000) % 60 ;
        int minutes = (int) ((milliSeconds / (1000*60)) % 60);
        int hours   = (int) ((milliSeconds / (1000*60*60)) % 24);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

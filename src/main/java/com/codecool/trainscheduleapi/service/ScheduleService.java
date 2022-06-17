package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.DTO.ScheduleDTO;
import com.codecool.trainscheduleapi.DTO.ScheduleSelectionDTO;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.repository.StopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private StopRepository stopRepository;

    @Autowired
    public ScheduleService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    public List<ScheduleDTO> listTrainSchedule(ScheduleSelectionDTO scheduleSelectionDTO, Boolean freight) {
        List<ScheduleDTO> schedule = new ArrayList<>();
        List<Stop> departures = stopRepository.findStopsByName(scheduleSelectionDTO.getDepartureLocation());
        if(departures.isEmpty())
            return schedule;

        for (Stop departure : departures) {
            String trainType = departure.getTrain().getType();
            if((freight && !trainType.equalsIgnoreCase("freight"))
                    || (!freight && trainType.equalsIgnoreCase("freight"))) {
                continue;
            }

            List<Stop> stops = departure.getTrain().getStops().stream()
                    .sorted(Comparator.comparingInt(Stop::getDistance))
                    .collect(Collectors.toList());

            Optional<Stop> arrival = stops.stream()
                    .filter(s -> s.getName().equals(scheduleSelectionDTO.getArrivalLocation()))
                    .findFirst();

            if(arrival.isPresent() && departure.getDistance() < arrival.get().getDistance()) {
                ScheduleDTO scheduleDTO = new ScheduleDTO();
                scheduleDTO.setDepartureTime(departure.getDepartureTime());
                scheduleDTO.setDepartureLocation(departure.getName());

                scheduleDTO.setArrivalTime(arrival.get().getArrivalTime());
                scheduleDTO.setArrivalLocation(arrival.get().getName());

                scheduleDTO.setTravelTime(MillisecondsToTimeUnits(arrival.get().getArrivalTime().getTime() - departure.getDepartureTime().getTime()));
                scheduleDTO.setTravelDistance(arrival.get().getDistance() - departure.getDistance());

                scheduleDTO.setTrain(departure.getTrain());
                schedule.add(scheduleDTO);
            }
        }

        return schedule.stream()
                .sorted(Comparator.comparing(ScheduleDTO :: getDepartureTime)) //.reversed()
                .collect(Collectors.toList());
    }

    private String MillisecondsToTimeUnits(long milliSeconds) {
        int seconds = (int) (milliSeconds / 1000) % 60 ;
        int minutes = (int) ((milliSeconds / (1000*60)) % 60);
        int hours   = (int) ((milliSeconds / (1000*60*60)) % 24);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

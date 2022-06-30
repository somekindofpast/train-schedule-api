package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.DTO.StopDTO;
import com.codecool.trainscheduleapi.DTO.StopSelectionDTO;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.repository.StopRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StopService {
    private StopRepository stopRepository;

    @Autowired
    public StopService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    public List<StopDTO> findAll() {
        return convertToStopDTOList(stopRepository.findAll());
    }

    public Optional<Stop> findById(Long id) {
        return stopRepository.findById(id);
    }

    public StopDTO save(StopSelectionDTO stopSelectionDTO) {
        Stop stop = new Stop();
        stop.setStopSelectionDTO(stopSelectionDTO);
        return new StopDTO(stopRepository.save(stop));
    }

    public ResponseEntity<?> update(StopSelectionDTO stopSelectionDTO, Long id, Logger logger) {
        Stop stop;
        try {
            stop = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            String errorMessage = "Stop id not found";
            logger.error("update() for Stop returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        stop.setStopSelectionDTO(stopSelectionDTO);
        logger.info("Running update() for Stop. Record updated.");
        return ResponseEntity.ok().body(new StopDTO(stopRepository.save(stop)));
    }

    public ResponseEntity<?> deleteById(Long id, Logger logger) {
        Stop stop;
        try {
            stop = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            String errorMessage = "Stop id not found";
            logger.error("deleteById() for Stop returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        stop.setTrain(null);
        stopRepository.delete(stop);
        logger.info("Running deleteById() for Stop. Record deleted.");
        return ResponseEntity.ok().build();
    }

    private List<StopDTO> convertToStopDTOList(List<Stop> stops) {
        List<StopDTO> stopDTOList = new ArrayList<>();
        for (Stop stop : stops) {
            stopDTOList.add(new StopDTO(stop));
        }
        return stopDTOList;
    }
}

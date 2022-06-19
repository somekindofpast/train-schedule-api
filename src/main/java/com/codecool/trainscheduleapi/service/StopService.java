package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.DTO.StopDTO;
import com.codecool.trainscheduleapi.DTO.StopSelectionDTO;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.repository.StopRepository;
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

    public ResponseEntity<?> findStopByNameAndTrainId(String stopName, Long trainId) {
        Optional<Stop> stop = stopRepository.findStopByNameAndTrainId(stopName, trainId);

        if(stop.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().body(new StopDTO(stop.get()));
    }

    public StopDTO save(StopSelectionDTO stopSelectionDTO) {
        Stop stop = new Stop();
        stop.setStopSelectionDTO(stopSelectionDTO);
        return new StopDTO(stopRepository.save(stop));
    }

    public ResponseEntity<?> update(StopSelectionDTO stopSelectionDTO, Long id) {
        Stop stop;
        try {
            stop = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("stop id not found");
        }

        stop.setStopSelectionDTO(stopSelectionDTO);
        return ResponseEntity.ok().body(new StopDTO(stopRepository.save(stop)));
    }

    public ResponseEntity<?> deleteById(Long id) {
        Stop stop;
        try {
            stop = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("stop id not found");
        }

        stop.setTrain(null);
        stopRepository.delete(stop);
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

package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.repository.StopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StopService {
    private StopRepository stopRepository;

    @Autowired
    public StopService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    public List<Stop> findAll() {
        return stopRepository.findAll();
    }

    public Optional<Stop> findById(Long id) {
        return stopRepository.findById(id);
    }
}

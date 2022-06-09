package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainService {
    private TrainRepository trainRepository;

    @Autowired
    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public List<Train> findAll() {
        return trainRepository.findAll();
    }

    public Optional<Train> findById(Long id) {
        return trainRepository.findById(id);
    }
}

package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.entity.Cargo;
import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.repository.CargoRepository;
import com.codecool.trainscheduleapi.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TrainService {
    private TrainRepository trainRepository;
    private CargoRepository cargoRepository;

    @Autowired
    public TrainService(TrainRepository trainRepository, CargoRepository cargoRepository) {
        this.trainRepository = trainRepository;
        this.cargoRepository = cargoRepository;
    }

    public List<Train> findAll() {
        return trainRepository.findAll();
    }

    public Optional<Train> findById(Long id) {
        return trainRepository.findById(id);
    }

    public Set<Train> findFreightTrainsByCargoName(String cargoName) {
        List<Cargo> cargoList = cargoRepository.findCargoByName(cargoName);
        Set<Train> trains = new HashSet<>();
        for (Cargo cargo : cargoList) {
            trains.add(cargo.getTrain());
        }
        return trains;
    }

    public void deleteById(Long id) {
        trainRepository.deleteById(id);
    }
}

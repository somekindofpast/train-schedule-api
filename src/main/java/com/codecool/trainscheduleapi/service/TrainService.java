package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.DTO.TrainCargoDTO;
import com.codecool.trainscheduleapi.DTO.TrainDTO;
import com.codecool.trainscheduleapi.DTO.TrainServiceDTO;
import com.codecool.trainscheduleapi.DTO.TrainStopDTO;
import com.codecool.trainscheduleapi.entity.Cargo;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.repository.CargoRepository;
import com.codecool.trainscheduleapi.repository.ServiceRepository;
import com.codecool.trainscheduleapi.repository.StopRepository;
import com.codecool.trainscheduleapi.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrainService {
    private TrainRepository trainRepository;
    private StopRepository stopRepository;
    private ServiceRepository serviceRepository;
    private CargoRepository cargoRepository;

    @Autowired
    public TrainService(TrainRepository trainRepository, StopRepository stopRepository, ServiceRepository serviceRepository, CargoRepository cargoRepository) {
        this.trainRepository = trainRepository;
        this.stopRepository = stopRepository;
        this.serviceRepository = serviceRepository;
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

    public Train save(TrainDTO trainDTO) {
        Train newTrain = new Train();
        newTrain.setId(trainDTO.getId());
        newTrain.setType(trainDTO.getType());
        newTrain.setStops(new ArrayList<>());
        newTrain.setCargos(new ArrayList<>());

        return trainRepository.save(newTrain);
    }

    public Train saveAddStop(TrainStopDTO trainStopDTO) {
        Train train = findById(trainStopDTO.getTrainId()).orElseThrow();
        Stop stop = stopRepository.findById(trainStopDTO.getStopId()).orElseThrow();
        train.getStops().add(stop);

        return trainRepository.save(train);
    }

    public Train saveAddService(TrainServiceDTO trainServiceDTO) {
        Train train = findById(trainServiceDTO.getTrainId()).orElseThrow();
        com.codecool.trainscheduleapi.entity.Service service = serviceRepository.findById(trainServiceDTO.getServiceId()).orElseThrow();
        train.setService(service);

        return trainRepository.save(train);
    }

    public Train saveAddCargo(TrainCargoDTO trainCargoDTO) {
        Train train = findById(trainCargoDTO.getTrainId()).orElseThrow();
        Cargo cargo = cargoRepository.findById(trainCargoDTO.getCargoId()).orElseThrow();
        train.getCargos().add(cargo);

        return trainRepository.save(train);
    }

    public Train update(Long id, String type) {
        Train train = findById(id).orElseThrow();
        train.setType(type);

        return trainRepository.save(train);
    }

    public void deleteById(Long id) {
        trainRepository.deleteById(id);
    }
}

package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.DTO.*;
import com.codecool.trainscheduleapi.entity.Cargo;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.repository.CargoRepository;
import com.codecool.trainscheduleapi.repository.ServiceRepository;
import com.codecool.trainscheduleapi.repository.StopRepository;
import com.codecool.trainscheduleapi.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public List<TrainDTO> findAll() {
        List<Train> trains = trainRepository.findAll();
        List<TrainDTO> trainDTOList = new ArrayList<>();
        for (Train train : trains) {
            trainDTOList.add(new TrainDTO(train));
        }
        return trainDTOList;
    }

    public Optional<Train> findById(Long id) {
        return trainRepository.findById(id);
    }

    public Set<TrainDTO> findFreightTrainsByCargoName(String cargoName) {
        List<Cargo> cargoList = cargoRepository.findCargoByName(cargoName);
        Set<TrainDTO> trainDTOList = new HashSet<>();
        for (Cargo cargo : cargoList) {
            trainDTOList.add(new TrainDTO(cargo.getTrain()));
        }
        return trainDTOList;
    }

    public TrainDTO save(TrainSelectionDTO trainSelectionDTO) {
        Train newTrain = new Train();
        newTrain.setId(trainSelectionDTO.getId());
        newTrain.setType(trainSelectionDTO.getType());
        newTrain.setStops(new ArrayList<>());
        newTrain.setCargos(new ArrayList<>());

        return new TrainDTO(trainRepository.save(newTrain));
    }

    public ResponseEntity<?> saveAddStop(TrainStopDTO trainStopDTO) {
        Optional<Train> train = findById(trainStopDTO.getTrainId());
        if(train.isEmpty())
            return ResponseEntity.badRequest().body("train id not found");

        Optional<Stop> stop = stopRepository.findById(trainStopDTO.getStopId());
        if(stop.isEmpty())
            return ResponseEntity.badRequest().body("stop id not found");

        stop.get().setTrain(train.get());
        return ResponseEntity.ok().body(new StopDTO(stopRepository.save(stop.get())));
    }

    public ResponseEntity<?> saveAddService(TrainServiceDTO trainServiceDTO) {
        Optional<Train> train = findById(trainServiceDTO.getTrainId());
        if(train.isEmpty())
            return ResponseEntity.badRequest().body("train id not found");

        Optional<com.codecool.trainscheduleapi.entity.Service> service = serviceRepository.findById(trainServiceDTO.getServiceId());
        if(service.isEmpty())
            return ResponseEntity.badRequest().body("service id not found");

        service.get().setTrain(train.get());
        return ResponseEntity.ok().body(new ServiceDTO(serviceRepository.save(service.get())));
    }

    public ResponseEntity<?> saveAddCargo(TrainCargoDTO trainCargoDTO) {
        Optional<Train> train = findById(trainCargoDTO.getTrainId());
        if(train.isEmpty())
            return ResponseEntity.badRequest().body("train id not found");

        Optional<Cargo> cargo = cargoRepository.findById(trainCargoDTO.getCargoId());
        if(cargo.isEmpty())
            return ResponseEntity.badRequest().body("cargo id not found");

        cargo.get().setTrain(train.get());
        return ResponseEntity.ok().body(new CargoDTO(cargoRepository.save(cargo.get())));
    }

    public ResponseEntity<?> update(Long id, String type) {
        Optional<Train> train = findById(id);
        if(train.isEmpty())
            return ResponseEntity.badRequest().body("train id not found");

        if(type == null || type.length() < 3)
            return ResponseEntity.badRequest().body("train type is incorrect");

        train.get().setType(type);

        return ResponseEntity.ok().body(new TrainDTO(trainRepository.save(train.get())));
    }

    public ResponseEntity<?> deleteById(Long id) {
        try {
            Train train = findById(id).orElseThrow();
            train.getStops().forEach(stop -> stop.setTrain(null));
            train.getService().setTrain(null);
            train.getCargos().forEach(cargo -> cargo.setTrain(null));
            trainRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("train id not found");
        }
    }
}

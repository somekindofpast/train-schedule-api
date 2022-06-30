package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.DTO.*;
import com.codecool.trainscheduleapi.entity.Cargo;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.repository.CargoRepository;
import com.codecool.trainscheduleapi.repository.ServiceRepository;
import com.codecool.trainscheduleapi.repository.StopRepository;
import com.codecool.trainscheduleapi.repository.TrainRepository;
import org.slf4j.Logger;
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

    public ResponseEntity<?> saveAddStop(TrainStopDTO trainStopDTO, Logger logger) {
        Optional<Train> train = findById(trainStopDTO.getTrainId());
        if(train.isEmpty()) {
            String errorMessage = "Train id not found";
            logger.error("saveAddStop() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }


        Optional<Stop> stop = stopRepository.findById(trainStopDTO.getStopId());
        if(stop.isEmpty()) {
            String errorMessage = "Stop id not found";
            logger.error("saveAddStop() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        stop.get().setTrain(train.get());
        logger.info("Running saveAddStop() for Train. Stop added to the list of Stops for Train.");
        return ResponseEntity.ok().body(new StopDTO(stopRepository.save(stop.get())));
    }

    public ResponseEntity<?> saveAddService(TrainServiceDTO trainServiceDTO, Logger logger) {
        Optional<Train> train = findById(trainServiceDTO.getTrainId());
        if(train.isEmpty()) {
            String errorMessage = "Train id not found";
            logger.error("saveAddService() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        Optional<com.codecool.trainscheduleapi.entity.Service> service = serviceRepository.findById(trainServiceDTO.getServiceId());
        if(service.isEmpty()) {
            String errorMessage = "Service id not found";
            logger.error("saveAddService() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        service.get().setTrain(train.get());
        logger.info("Running saveAddService() for Train. Service added to Train.");
        return ResponseEntity.ok().body(new ServiceDTO(serviceRepository.save(service.get())));
    }

    public ResponseEntity<?> saveAddCargo(TrainCargoDTO trainCargoDTO, Logger logger) {
        Optional<Train> train = findById(trainCargoDTO.getTrainId());
        if(train.isEmpty()) {
            String errorMessage = "Train id not found";
            logger.error("saveAddCargo() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        Optional<Cargo> cargo = cargoRepository.findById(trainCargoDTO.getCargoId());
        if(cargo.isEmpty()) {
            String errorMessage = "Cargo id not found";
            logger.error("saveAddCargo() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        cargo.get().setTrain(train.get());
        logger.info("Running saveAddCargo() for Train. Cargo added to the list of Cargos for Train.");
        return ResponseEntity.ok().body(new CargoDTO(cargoRepository.save(cargo.get())));
    }

    public ResponseEntity<?> update(TrainSelectionDTO trainSelectionDTO, Long id, Logger logger) {
        Train train;
        try {
            train = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            String errorMessage = "Train id not found";
            logger.error("update() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        train.setType(trainSelectionDTO.getType());
        logger.info("Running update() for Train. Record updated.");
        return ResponseEntity.ok().body(new TrainDTO(trainRepository.save(train)));
    }

    public ResponseEntity<?> deleteById(Long id, Logger logger) {
        Train train;
        try {
            train = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            String errorMessage = "Train id not found";
            logger.error("deleteById() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        train.getStops().forEach(stop -> stop.setTrain(null));
        if(train.getService() != null)
            train.getService().setTrain(null);
        train.getCargos().forEach(cargo -> cargo.setTrain(null));
        trainRepository.delete(train);
        logger.info("Running deleteById() for Train. Record deleted.");
        return ResponseEntity.ok().build();
    }
}

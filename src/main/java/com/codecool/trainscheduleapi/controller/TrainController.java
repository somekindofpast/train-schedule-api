package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.DTO.*;
import com.codecool.trainscheduleapi.ValidationUtility;
import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.service.TrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/train")
public class TrainController {
    private TrainService trainService;
    private Logger logger = LoggerFactory.getLogger(TrainController.class);

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping
    public List<TrainDTO> findAll() {
        logger.info("Running findAll() for Train. Result list can be empty.");
        return trainService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<Train> train = trainService.findById(id);

        if(train.isEmpty()) {
            logger.error("findById() for Train returned with error 404: Record not found.");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Running findById() for Train. Record found.");
            return ResponseEntity.ok().body(new TrainDTO(train.get()));
        }
    }

    @GetMapping("/freight/{cargo_name}")
    public ResponseEntity<?> findFreightTrainsByCargoName(@PathVariable("cargo_name") String cargoName) {
        if(cargoName == null || cargoName.length() < 1 || 200 < cargoName.length()) {
            String errorMessage = "Cargo name must be between 1-200 characters";
            logger.error("findFreightTrainsByCargoName() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        logger.info("Running findFreightTrainsByCargoName() for Train. Result list can be empty.");
        return ResponseEntity.ok().body(trainService.findFreightTrainsByCargoName(cargoName));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid TrainSelectionDTO trainSelectionDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            String errorMessage = ValidationUtility.getFieldErrorMessages(errors);
            logger.error("save() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        logger.info("Running save() for Train. Record saved.");
        return ResponseEntity.ok().body(trainService.save(trainSelectionDTO));
    }

    @PostMapping("/addStop")
    public ResponseEntity<?> saveAddStop(@RequestBody @Valid TrainStopDTO trainStopDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            String errorMessage = ValidationUtility.getFieldErrorMessages(errors);
            logger.error("saveAddStop() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        return trainService.saveAddStop(trainStopDTO, logger);
    }

    @PostMapping("/addService")
    public ResponseEntity<?> saveAddService(@RequestBody @Valid TrainServiceDTO trainServiceDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            String errorMessage = ValidationUtility.getFieldErrorMessages(errors);
            logger.error("saveAddService() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        return trainService.saveAddService(trainServiceDTO, logger);
    }

    @PostMapping("/addCargo")
    public ResponseEntity<?> saveAddCargo(@RequestBody @Valid TrainCargoDTO trainCargoDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            String errorMessage = ValidationUtility.getFieldErrorMessages(errors);
            logger.error("saveAddCargo() for Train returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        return trainService.saveAddCargo(trainCargoDTO, logger);
    }

    @PutMapping("/{id}/type/{type}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @PathVariable("type") String type) {
        return trainService.update(id, type, logger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return trainService.deleteById(id, logger);
    }
}

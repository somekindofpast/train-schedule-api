package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.DTO.*;
import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.service.TrainService;
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

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping
    public List<TrainDTO> findAll() {
        return trainService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<Train> train = trainService.findById(id);

        if(train.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(new TrainDTO(train.get()));
        }
    }

    @GetMapping("/freight/{cargo_name}")
    public ResponseEntity<?> findFreightTrainsByCargoName(@PathVariable("cargo_name") String cargoName) {
        if(cargoName == null || cargoName.length() < 3)
            return ResponseEntity.badRequest().body("cargo name must be at least 3 characters");

        return ResponseEntity.ok().body(trainService.findFreightTrainsByCargoName(cargoName));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid TrainSelectionDTO trainSelectionDTO, BindingResult errors) {
        if(errors.hasErrors())
            return ResponseEntity.badRequest().body("one or more JSON field not valid");

        return ResponseEntity.ok().body(trainService.save(trainSelectionDTO));
    }

    @PostMapping("/addStop")
    public ResponseEntity<?> saveAddStop(@RequestBody @Valid TrainStopDTO trainStopDTO, BindingResult errors) {
        if(errors.hasErrors())
            return ResponseEntity.badRequest().body("one or more JSON field not valid");

        return trainService.saveAddStop(trainStopDTO);
    }

    @PostMapping("/addService")
    public ResponseEntity<?> saveAddService(@RequestBody @Valid TrainServiceDTO trainServiceDTO, BindingResult errors) {
        if(errors.hasErrors())
            return ResponseEntity.badRequest().body("one or more JSON field not valid");

        return trainService.saveAddService(trainServiceDTO);
    }

    @PostMapping("/addCargo")
    public ResponseEntity<?> saveAddCargo(@RequestBody @Valid TrainCargoDTO trainCargoDTO, BindingResult errors) {
        if(errors.hasErrors())
            return ResponseEntity.badRequest().body("one or more JSON field not valid");

        return trainService.saveAddCargo(trainCargoDTO);
    }

    @PutMapping("/{id}/type/{type}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @PathVariable("type") String type) {
        return trainService.update(id, type);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return trainService.deleteById(id);
    }
}

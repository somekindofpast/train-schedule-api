package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.DTO.TrainCargoDTO;
import com.codecool.trainscheduleapi.DTO.TrainDTO;
import com.codecool.trainscheduleapi.DTO.TrainServiceDTO;
import com.codecool.trainscheduleapi.DTO.TrainStopDTO;
import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/train")
public class TrainController {
    private TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping
    public List<Train> findAll() {
        return trainService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Train> findById(@PathVariable("id") Long id) {
        return trainService.findById(id);
    }

    @GetMapping("/freight/{cargo_name}")
    public Set<Train> findFreightTrainsByCargoName(@PathVariable("cargo_name") String cargoName) {
        return trainService.findFreightTrainsByCargoName(cargoName);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid TrainDTO trainDTO, BindingResult errors) {
        if(errors.hasErrors())
            return ResponseEntity.badRequest().body("train type is incorrect");
        else
            return ResponseEntity.ok().body(trainService.save(trainDTO));
        //return trainService.save(trainDTO);
    }

    @PostMapping("/addStop")
    public Train saveAddStop(@RequestBody TrainStopDTO trainStopDTO) {
        return trainService.saveAddStop(trainStopDTO);
    }

    @PostMapping("/addService")
    public Train saveAddService(@RequestBody TrainServiceDTO trainServiceDTO) {
        return trainService.saveAddService(trainServiceDTO);
    }

    @PostMapping("/addCargo")
    public Train saveAddCargo(@RequestBody TrainCargoDTO trainCargoDTO) {
        return trainService.saveAddCargo(trainCargoDTO);
    }

    @PutMapping("/{id}/type/{type}")
    public Train update(@PathVariable("id") Long id, @PathVariable("type") String type) {
        return trainService.update(id, type);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        trainService.deleteById(id);
    }
}

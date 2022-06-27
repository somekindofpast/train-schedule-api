package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.DTO.StopDTO;
import com.codecool.trainscheduleapi.DTO.StopSelectionDTO;
import com.codecool.trainscheduleapi.ValidationUtility;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.service.StopService;
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
@RequestMapping("/stop")
public class StopController {
    private StopService stopService;
    private Logger logger = LoggerFactory.getLogger(StopController.class);

    @Autowired
    public StopController(StopService stopService) {
        this.stopService = stopService;
    }

    @GetMapping
    public List<StopDTO> findAll() {
        logger.info("Running findAll() for Stop. Result list can be empty.");
        return stopService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<Stop> stop = stopService.findById(id);

        if(stop.isEmpty()) {
            logger.error("findById() for Stop returned with error 404: Record not found.");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Running findById() for Stop. Record found.");
            return ResponseEntity.ok().body(new StopDTO(stop.get()));
        }
    }

    @GetMapping("/name/{name}/train_id/{train_id}")
    public ResponseEntity<?> findStopByNameAndTrainId(@PathVariable("name") String name, @PathVariable("train_id") Long trainId) {
        if(name == null || name.length() < 1 || 200 < name.length()) {
            String errorMessage = "Stop name must be between 1-200 characters";
            logger.error("findStopByNameAndTrainId() for Stop returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        if(trainId == null || trainId < 0) {
            String errorMessage = "Train id must be a non negative number";
            logger.error("findStopByNameAndTrainId() for Stop returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        return stopService.findStopByNameAndTrainId(name, trainId, logger);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid StopSelectionDTO stopSelectionDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            String errorMessage = ValidationUtility.getFieldErrorMessages(errors);
            logger.error("save() for Stop returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        logger.info("Running save() for Stop. Record saved.");
        return ResponseEntity.ok().body(stopService.save(stopSelectionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid StopSelectionDTO stopSelectionDTO, BindingResult errors, @PathVariable("id") Long id) {
        if(errors.hasFieldErrors()) {
            String errorMessage = ValidationUtility.getFieldErrorMessages(errors);
            logger.error("update() for Stop returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        return stopService.update(stopSelectionDTO, id, logger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return stopService.deleteById(id, logger);
    }
}

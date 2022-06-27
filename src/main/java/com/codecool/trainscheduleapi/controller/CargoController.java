package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.DTO.CargoDTO;
import com.codecool.trainscheduleapi.DTO.CargoSelectionDTO;
import com.codecool.trainscheduleapi.ValidationUtility;
import com.codecool.trainscheduleapi.entity.Cargo;
import com.codecool.trainscheduleapi.service.CargoService;
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
@RequestMapping("/cargo")
public class CargoController {
    private CargoService cargoService;
    private Logger logger = LoggerFactory.getLogger(CargoController.class);

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public List<CargoDTO> findAll() {
        logger.info("Running findAll() for Cargo. Result list can be empty.");
        return cargoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<Cargo> cargo = cargoService.findById(id);

        if(cargo.isEmpty()) {
            logger.error("findById() for Cargo returned with error 404: Record not found.");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Running findById() for Cargo. Record found.");
            return ResponseEntity.ok().body(new CargoDTO(cargo.get()));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable("name") String name) {
        if(name == null || name.length() < 1 || 200 < name.length()) {
            String errorMessage = "Cargo name must be between 1-200 characters";
            logger.error("findByName() for Cargo returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        logger.info("Running findByName() for Cargo. Result list can be empty.");
        return ResponseEntity.ok().body(cargoService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid CargoSelectionDTO cargoSelectionDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            String errorMessage = ValidationUtility.getFieldErrorMessages(errors);
            logger.error("save() for Cargo returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        logger.info("Running save() for Cargo. Record saved.");
        return ResponseEntity.ok().body(cargoService.save(cargoSelectionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CargoSelectionDTO cargoSelectionDTO, BindingResult errors, @PathVariable("id") Long id) {
        if(errors.hasFieldErrors()) {
            String errorMessage = ValidationUtility.getFieldErrorMessages(errors);
            logger.error("update() for Cargo returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        return cargoService.update(cargoSelectionDTO, id, logger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return cargoService.deleteById(id, logger);
    }
}

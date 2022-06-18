package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.DTO.CargoDTO;
import com.codecool.trainscheduleapi.DTO.CargoSelectionDTO;
import com.codecool.trainscheduleapi.ValidationUtility;
import com.codecool.trainscheduleapi.entity.Cargo;
import com.codecool.trainscheduleapi.service.CargoService;
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

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public List<CargoDTO> findAll() {
        return cargoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<Cargo> cargo = cargoService.findById(id);

        if(cargo.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(new CargoDTO(cargo.get()));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable("name") String name) {
        if(name == null || name.length() < 1 || 200 < name.length())
            return ResponseEntity.badRequest().body("cargo name must be between 1-200 characters");

        return ResponseEntity.ok().body(cargoService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid CargoSelectionDTO cargoSelectionDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtility.getFieldErrorMessages(errors));
        }

        return ResponseEntity.ok().body(cargoService.save(cargoSelectionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save(@RequestBody @Valid CargoSelectionDTO cargoSelectionDTO, BindingResult errors, @PathVariable("id") Long id) {
        if(errors.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtility.getFieldErrorMessages(errors));
        }

        return cargoService.update(cargoSelectionDTO, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return cargoService.deleteById(id);
    }
}

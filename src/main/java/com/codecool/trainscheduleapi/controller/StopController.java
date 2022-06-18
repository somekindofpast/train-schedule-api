package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.DTO.StopDTO;
import com.codecool.trainscheduleapi.DTO.StopSelectionDTO;
import com.codecool.trainscheduleapi.ValidationUtility;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.service.StopService;
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

    @Autowired
    public StopController(StopService stopService) {
        this.stopService = stopService;
    }

    @GetMapping
    public List<StopDTO> findAll() {
        return stopService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<Stop> stop = stopService.findById(id);

        if(stop.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(new StopDTO(stop.get()));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findStopsByName(@PathVariable("name") String name) {
        if(name == null || name.length() < 1 || 200 < name.length())
            return ResponseEntity.badRequest().body("stop name must be between 1-200 characters");

        return ResponseEntity.ok().body(stopService.findStopsByName(name));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid StopSelectionDTO stopSelectionDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtility.getFieldErrorMessages(errors));
        }

        return ResponseEntity.ok().body(stopService.save(stopSelectionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid StopSelectionDTO stopSelectionDTO, BindingResult errors, @PathVariable("id") Long id) {
        if(errors.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtility.getFieldErrorMessages(errors));
        }

        return stopService.update(stopSelectionDTO, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return stopService.deleteById(id);
    }
}

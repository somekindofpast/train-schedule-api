package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.DTO.ServiceDTO;
import com.codecool.trainscheduleapi.DTO.ServiceSelectionDTO;
import com.codecool.trainscheduleapi.ValidationUtility;
import com.codecool.trainscheduleapi.entity.Service;
import com.codecool.trainscheduleapi.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service")
public class ServiceController {
    private ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public List<ServiceDTO> findAll() {
        return serviceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<com.codecool.trainscheduleapi.entity.Service> service = serviceService.findById(id);

        if(service.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(new ServiceDTO(service.get()));
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid ServiceSelectionDTO serviceSelectionDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtility.getFieldErrorMessages(errors));
        }

        return ResponseEntity.ok().body(serviceService.save(serviceSelectionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid ServiceSelectionDTO serviceSelectionDTO, BindingResult errors, @PathVariable("id") Long id) {
        if(errors.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtility.getFieldErrorMessages(errors));
        }

        return serviceService.update(serviceSelectionDTO, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return serviceService.deleteById(id);
    }
}

package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.DTO.ServiceDTO;
import com.codecool.trainscheduleapi.DTO.ServiceSelectionDTO;
import com.codecool.trainscheduleapi.ValidationUtility;
import com.codecool.trainscheduleapi.service.ServiceService;
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
@RequestMapping("/service")
public class ServiceController {
    private ServiceService serviceService;
    private Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public List<ServiceDTO> findAll() {
        logger.info("Running findAll() for Service. Result list can be empty.");
        return serviceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<com.codecool.trainscheduleapi.entity.Service> service = serviceService.findById(id);

        if(service.isEmpty()) {
            logger.error("findById() for Service returned with error 404: Record not found.");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Running findById() for Service. Record found.");
            return ResponseEntity.ok().body(new ServiceDTO(service.get()));
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid ServiceSelectionDTO serviceSelectionDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            String errorMessage = ValidationUtility.getFieldErrorMessages(errors);
            logger.error("save() for Service returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        logger.info("Running save() for Service. Record saved.");
        return ResponseEntity.ok().body(serviceService.save(serviceSelectionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid ServiceSelectionDTO serviceSelectionDTO, BindingResult errors, @PathVariable("id") Long id) {
        if(errors.hasFieldErrors()) {
            String errorMessage = ValidationUtility.getFieldErrorMessages(errors);
            logger.error("update() for Service returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        return serviceService.update(serviceSelectionDTO, id, logger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return serviceService.deleteById(id, logger);
    }
}

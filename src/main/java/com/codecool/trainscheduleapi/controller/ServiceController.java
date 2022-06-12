package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.entity.Service;
import com.codecool.trainscheduleapi.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service")
public class ServiceController {
    private ServiceService service;

    @Autowired
    public ServiceController(ServiceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Service> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Service> findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}

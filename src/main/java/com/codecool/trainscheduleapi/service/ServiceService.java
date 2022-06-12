package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {
    private ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<com.codecool.trainscheduleapi.entity.Service> findAll() {
        return serviceRepository.findAll();
    }

    public Optional<com.codecool.trainscheduleapi.entity.Service> findById(Long id) {
        return serviceRepository.findById(id);
    }

    public void deleteById(Long id) {
        serviceRepository.deleteById(id);
    }
}

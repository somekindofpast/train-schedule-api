package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.DTO.ServiceDTO;
import com.codecool.trainscheduleapi.DTO.ServiceSelectionDTO;
import com.codecool.trainscheduleapi.repository.ServiceRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServiceService {
    private ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceDTO> findAll() {
        List<com.codecool.trainscheduleapi.entity.Service> services = serviceRepository.findAll();
        List<ServiceDTO> serviceDTOList = new ArrayList<>();
        for (com.codecool.trainscheduleapi.entity.Service service : services) {
            serviceDTOList.add(new ServiceDTO(service));
        }
        return serviceDTOList;
    }

    public Optional<com.codecool.trainscheduleapi.entity.Service> findById(Long id) {
        return serviceRepository.findById(id);
    }

    public ServiceDTO save(ServiceSelectionDTO serviceSelectionDTO) {
        com.codecool.trainscheduleapi.entity.Service service = new com.codecool.trainscheduleapi.entity.Service();
        service.setServiceSelectionDTO(serviceSelectionDTO);

        return new ServiceDTO(serviceRepository.save(service));
    }

    public ResponseEntity<?> update(ServiceSelectionDTO serviceSelectionDTO, Long id, Logger logger) {
        com.codecool.trainscheduleapi.entity.Service service;
        try {
            service = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            String errorMessage = "Service id not found";
            logger.error("update() for Service returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        service.setServiceSelectionDTO(serviceSelectionDTO);
        logger.info("Running update() for Service. Record updated.");
        return ResponseEntity.ok().body(new ServiceDTO(serviceRepository.save(service)));
    }

    public ResponseEntity<?> deleteById(Long id, Logger logger) {
        com.codecool.trainscheduleapi.entity.Service service;
        try {
            service = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            String errorMessage = "Service id not found";
            logger.error("deleteById() for Service returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        service.setTrain(null);
        serviceRepository.delete(service);
        logger.info("Running deleteById() for Service. Record deleted.");
        return ResponseEntity.ok().build();
    }
}

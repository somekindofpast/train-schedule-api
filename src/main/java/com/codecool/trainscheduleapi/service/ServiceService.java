package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.DTO.ServiceDTO;
import com.codecool.trainscheduleapi.DTO.ServiceSelectionDTO;
import com.codecool.trainscheduleapi.repository.ServiceRepository;
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

    public ResponseEntity<?> update(ServiceSelectionDTO serviceSelectionDTO, Long id) {
        com.codecool.trainscheduleapi.entity.Service service;
        try {
            service = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("service id not found");
        }

        service.setServiceSelectionDTO(serviceSelectionDTO);
        return ResponseEntity.ok().body(new ServiceDTO(serviceRepository.save(service)));
    }

    public ResponseEntity<?> deleteById(Long id) {
        com.codecool.trainscheduleapi.entity.Service service;
        try {
            service = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("service id not found");
        }

        service.setTrain(null);
        serviceRepository.delete(service);
        return ResponseEntity.ok().build();
    }
}

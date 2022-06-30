package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.DTO.CargoDTO;
import com.codecool.trainscheduleapi.DTO.CargoSelectionDTO;
import com.codecool.trainscheduleapi.entity.Cargo;
import com.codecool.trainscheduleapi.repository.CargoRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CargoService {
    private CargoRepository cargoRepository;

    @Autowired
    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public List<CargoDTO> findAll() {
        return convertToCargoDTOList(cargoRepository.findAll());
    }

    public Optional<Cargo> findById(Long id) {
        return cargoRepository.findById(id);
    }

    public List<CargoDTO> findByName(String name) {
        return convertToCargoDTOList(cargoRepository.findCargoByName(name));
    }

    public CargoDTO save(CargoSelectionDTO cargoSelectionDTO) {
        Cargo cargo = new Cargo();
        cargo.setName(cargoSelectionDTO.getName());
        cargo.setCarType(cargoSelectionDTO.getCarType());
        return new CargoDTO(cargoRepository.save(cargo));
    }

    public ResponseEntity<?> update(CargoSelectionDTO cargoSelectionDTO, Long id, Logger logger) {
        Cargo cargo;
        try {
            cargo = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            String errorMessage = "Cargo id not found";
            logger.error("update() for Cargo returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        cargo.setName(cargoSelectionDTO.getName());
        cargo.setCarType(cargoSelectionDTO.getCarType());
        logger.info("Running update() for Cargo. Record updated.");
        return ResponseEntity.ok().body(new CargoDTO(cargoRepository.save(cargo)));
    }

    public ResponseEntity<?> deleteById(Long id, Logger logger) {
        Cargo cargo;
        try {
            cargo = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            String errorMessage = "Cargo id not found";
            logger.error("deleteById() for Cargo returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }
        
        cargo.setTrain(null);
        cargoRepository.delete(cargo);
        logger.info("Running deleteById() for Cargo. Record deleted.");
        return ResponseEntity.ok().build();
    }

    private List<CargoDTO> convertToCargoDTOList(List<Cargo> cargos) {
        List<CargoDTO> cargoDTOList = new ArrayList<>();
        for (Cargo cargo : cargos) {
            cargoDTOList.add(new CargoDTO(cargo));
        }
        return cargoDTOList;
    }
}

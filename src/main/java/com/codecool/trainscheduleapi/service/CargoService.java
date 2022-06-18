package com.codecool.trainscheduleapi.service;

import com.codecool.trainscheduleapi.DTO.CargoDTO;
import com.codecool.trainscheduleapi.DTO.CargoSelectionDTO;
import com.codecool.trainscheduleapi.entity.Cargo;
import com.codecool.trainscheduleapi.repository.CargoRepository;
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
        cargo.setCarType(cargo.getCarType());
        return new CargoDTO(cargoRepository.save(cargo));
    }

    public ResponseEntity<?> update(CargoSelectionDTO cargoSelectionDTO, Long id) {
        Cargo cargo;
        try {
            cargo = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("cargo id not found");
        }

        cargo.setName(cargoSelectionDTO.getName());
        cargo.setCarType(cargo.getCarType());
        return ResponseEntity.ok().body(new CargoDTO(cargoRepository.save(cargo)));
    }

    public ResponseEntity<?> deleteById(Long id) {
        Cargo cargo;
        try {
            cargo = findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("cargo id not found");
        }
        
        cargo.setTrain(null);
        cargoRepository.delete(cargo);
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

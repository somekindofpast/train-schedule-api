package com.codecool.trainscheduleapi.repository;

import com.codecool.trainscheduleapi.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    List<Cargo> findCargoByName(String name);
}

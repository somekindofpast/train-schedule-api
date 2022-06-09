package com.codecool.trainscheduleapi.repository;

import com.codecool.trainscheduleapi.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
}

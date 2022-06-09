package com.codecool.trainscheduleapi.repository;

import com.codecool.trainscheduleapi.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {
}

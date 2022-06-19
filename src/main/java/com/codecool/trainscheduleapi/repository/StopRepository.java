package com.codecool.trainscheduleapi.repository;

import com.codecool.trainscheduleapi.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {
    @Query("SELECT s0 FROM Stop s0 WHERE s0.name =:stopName AND s0 IN (SELECT s1 FROM Stop s1 JOIN s1.train t WHERE t.id =:trainId)")
    Optional<Stop> findStopByNameAndTrainId(@Param("stopName") String stopName, @Param("trainId") Long trainId);
}

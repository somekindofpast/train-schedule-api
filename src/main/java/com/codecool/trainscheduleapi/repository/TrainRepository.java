package com.codecool.trainscheduleapi.repository;

import com.codecool.trainscheduleapi.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    //@Query("SELECT t FROM Train t LEFT JOIN t.stops s WHERE (:departureLocation MEMBER OF s AND :arrivalLocation MEMBER OF s)") //s.name =:departureLocation AND s.name =:arrivalLocation

    @Query("SELECT t0 FROM Train t0 WHERE t0 IN (SELECT t1 FROM Train t1 LEFT JOIN t1.stops s1 WHERE (s1.name =:departureLocation AND s1.departureTime IS NOT NULL)) AND t0 IN (SELECT t2 FROM Train t2 LEFT JOIN t2.stops s2 WHERE (s2.name =:arrivalLocation AND s2.arrivalTime IS NOT NULL))")
    List<Train> getTrainsByStops(@Param("departureLocation") String departureLocation, @Param("arrivalLocation") String arrivalLocation);
}

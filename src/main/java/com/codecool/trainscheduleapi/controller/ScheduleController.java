package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private ScheduleService scheduleService;
    private Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/passenger/{departureLocation}/{arrivalLocation}")
    public ResponseEntity<?> getPassengerTrainSchedule(@PathVariable("departureLocation") String departureLocation, @PathVariable("arrivalLocation") String arrivalLocation) {
        if(departureLocation == null || departureLocation.length() < 1 || 200 < departureLocation.length()) {
            String errorMessage = "DepartureLocation must be between 1-200 characters long";
            logger.error("getPassengerTrainSchedule() for Schedule returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        if(arrivalLocation == null || arrivalLocation.length() < 1 || 200 < arrivalLocation.length()) {
            String errorMessage = "ArrivalLocation must be between 1-200 characters long";
            logger.error("getPassengerTrainSchedule() for Schedule returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        logger.info("Running getPassengerTrainSchedule() for Schedule. Result list can be empty.");
        return ResponseEntity.ok().body(scheduleService.listTrainSchedule(departureLocation, arrivalLocation, false));
    }

    @GetMapping("/freight/{departureLocation}/{arrivalLocation}")
    public ResponseEntity<?> getFreightTrainSchedule(@PathVariable("departureLocation") String departureLocation, @PathVariable("arrivalLocation") String arrivalLocation) {
        if(departureLocation == null || departureLocation.length() < 1 || 200 < departureLocation.length()) {
            String errorMessage = "DepartureLocation must be between 1-200 characters long";
            logger.error("getFreightTrainSchedule() for Schedule returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        if(arrivalLocation == null || arrivalLocation.length() < 1 || 200 < arrivalLocation.length()) {
            String errorMessage = "ArrivalLocation must be between 1-200 characters long";
            logger.error("getFreightTrainSchedule() for Schedule returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        logger.info("Running getFreightTrainSchedule() for Schedule. Result list can be empty.");
        return ResponseEntity.ok().body(scheduleService.listTrainSchedule(departureLocation, arrivalLocation, true));
    }
}

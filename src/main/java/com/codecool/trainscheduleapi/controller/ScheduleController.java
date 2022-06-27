package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.DTO.ScheduleSelectionDTO;
import com.codecool.trainscheduleapi.ValidationUtility;
import com.codecool.trainscheduleapi.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private ScheduleService scheduleService;
    private Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/passenger")
    public ResponseEntity<?> getPassengerTrainSchedule(@RequestBody @Valid ScheduleSelectionDTO scheduleSelectionDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            String errorMessage = ValidationUtility.getFieldErrorMessages(errors);
            logger.error("getPassengerTrainSchedule() for Schedule returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        logger.info("Running getPassengerTrainSchedule() for Schedule. Result list can be empty.");
        return ResponseEntity.ok().body(scheduleService.listTrainSchedule(scheduleSelectionDTO, false));
    }

    @GetMapping("/freight")
    public ResponseEntity<?> getFreightTrainSchedule(@RequestBody @Valid ScheduleSelectionDTO scheduleSelectionDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            String errorMessage = ValidationUtility.getFieldErrorMessages(errors);
            logger.error("getFreightTrainSchedule() for Schedule returned with error 400: Bad request. " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        logger.info("Running getFreightTrainSchedule() for Schedule. Result list can be empty.");
        return ResponseEntity.ok().body(scheduleService.listTrainSchedule(scheduleSelectionDTO, true));
    }
}

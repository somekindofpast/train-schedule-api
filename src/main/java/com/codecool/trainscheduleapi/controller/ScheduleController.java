package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.DTO.ScheduleDTO;
import com.codecool.trainscheduleapi.DTO.ScheduleSelectionDTO;
import com.codecool.trainscheduleapi.ValidationUtility;
import com.codecool.trainscheduleapi.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/passenger")
    public ResponseEntity<?> getPassengerTrainSchedule(@RequestBody @Valid ScheduleSelectionDTO scheduleSelectionDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtility.getFieldErrorMessages(errors));
        }

        return ResponseEntity.ok().body(scheduleService.listTrainSchedule(scheduleSelectionDTO, false));
    }

    @GetMapping("/freight")
    public ResponseEntity<?> getFreightTrainSchedule(@RequestBody @Valid ScheduleSelectionDTO scheduleSelectionDTO, BindingResult errors) {
        if(errors.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtility.getFieldErrorMessages(errors));
        }

        return ResponseEntity.ok().body(scheduleService.listTrainSchedule(scheduleSelectionDTO, true));
    }
}

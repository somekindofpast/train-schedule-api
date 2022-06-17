package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.DTO.ScheduleDTO;
import com.codecool.trainscheduleapi.DTO.ScheduleSelectionDTO;
import com.codecool.trainscheduleapi.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<ScheduleDTO> getPassengerTrainSchedule(@RequestBody ScheduleSelectionDTO scheduleSelectionDTO) {
        return scheduleService.listTrainSchedule(scheduleSelectionDTO, false);
    }

    @GetMapping("/freight")
    public List<ScheduleDTO> getFreightTrainSchedule(@RequestBody ScheduleSelectionDTO scheduleSelectionDTO) {
        return scheduleService.listTrainSchedule(scheduleSelectionDTO, true);
    }
}

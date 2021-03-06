package com.codecool.trainscheduleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrainScheduleApiApplication {

	public static void main(String[] args) {
		DatabaseBuilder.run();
		SpringApplication.run(TrainScheduleApiApplication.class, args);
	}

}

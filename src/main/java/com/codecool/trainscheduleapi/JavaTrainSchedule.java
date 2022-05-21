package com.codecool.trainscheduleapi;

import org.flywaydb.core.Flyway;

public class JavaTrainSchedule {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure().dataSource(System.getenv("DATABASE_URL"), System.getenv("DATABASE_USERNAME"), System.getenv("DATABASE_PASSWORD")).load();

        //flyway.clean();
        flyway.migrate();
    }
}

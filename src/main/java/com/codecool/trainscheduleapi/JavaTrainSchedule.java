package com.codecool.trainscheduleapi;

import org.flywaydb.core.Flyway;

import java.sql.*;

public class JavaTrainSchedule {
    private final static int FREIGHT_TRAIN_START = 50000;

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure().dataSource(System.getenv("DATABASE_URL"), System.getenv("DATABASE_USERNAME"), System.getenv("DATABASE_PASSWORD")).load();

        flyway.clean();
        //buildDatabase(flyway);
    }

    private static void buildDatabase(Flyway flyway) {
        flyway.migrate();

        Connection con = new ConnectionUtility().getConnection();
        generateTrains(con);
        generateStops(con);
    }

    private static void generateTrains(Connection con) {
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from train");

            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                int highestClass = rs.getInt("highest_class");

                insertTrain(con, (id % 2 == 0 ? id + 2 : id - 2), type, highestClass);
                insertTrain(con, (id % 2 == 0 ? id + 4 : id - 4), type, highestClass);
                insertTrain(con, FREIGHT_TRAIN_START + id, "Freight", 0);
                insertTrain(con, (id % 2 == 0 ? FREIGHT_TRAIN_START + id + 2 : FREIGHT_TRAIN_START + id - 2), "Freight", 0);
                insertTrain(con, (id % 2 == 0 ? FREIGHT_TRAIN_START + id + 4 : FREIGHT_TRAIN_START + id - 4), "Freight", 0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertTrain(Connection con, int id, String type, int highestClass) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO train" +
                " (id,type,highest_class) VALUES (?,?,?)");
        stmt.setInt(1, id);
        stmt.setString(2, type);
        stmt.setInt(3, highestClass);
        stmt.executeUpdate();
    }

    private static void generateStops(Connection con) {
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from stop");

            while (rs.next()) {
                int trainId = rs.getInt("train_id");
                int distance = rs.getInt("distance");
                String name = rs.getString("name");
                Time arrivalTime = rs.getTime("arrival_time");
                Time departureTime = rs.getTime("departure_time");
                int platform = rs.getInt("platform");

                insertStop(con, (trainId % 2 == 0 ? trainId + 2 : trainId - 2), distance, name, arrivalTime, departureTime, platform);
                insertStop(con, (trainId % 2 == 0 ? trainId + 4 : trainId - 4), distance, name, arrivalTime, departureTime, platform);
                insertStop(con, FREIGHT_TRAIN_START + trainId, distance, name, arrivalTime, departureTime, platform);
                insertStop(con, (trainId % 2 == 0 ? FREIGHT_TRAIN_START + trainId + 2 : FREIGHT_TRAIN_START + trainId - 2), distance, name, arrivalTime, departureTime, platform);
                insertStop(con, (trainId % 2 == 0 ? FREIGHT_TRAIN_START + trainId + 4 : FREIGHT_TRAIN_START + trainId - 4), distance, name, arrivalTime, departureTime, platform);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertStop (
            Connection con,
            int trainId,
            int distance,
            String name,
            Time arrivalTime,
            Time departureTime,
            int platform
    ) throws SQLException {

        PreparedStatement stmt = con.prepareStatement("INSERT INTO stop" +
                " (train_id, distance, name, arrival_time, departure_time, platform) VALUES (?,?,?,?,?,?)");
        stmt.setInt(1, trainId);
        stmt.setInt(2, distance);
        stmt.setString(3, name);
        stmt.setTime(4, arrivalTime);
        stmt.setTime(5, departureTime);
        stmt.setInt(6, platform);
        stmt.executeUpdate();
    }

    private static void generateServices(Connection con) {
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from service");

            while (rs.next()) {
                int trainId = rs.getInt("train_id");
                boolean suburban = rs.getBoolean("suburban");
                boolean longDistance = rs.getBoolean("long_distance");
                boolean firstClass = rs.getBoolean("first_class");
                boolean secondClass = rs.getBoolean("second_class");
                boolean reservationCompulsory = rs.getBoolean("reservation_compulsory");
                boolean supplementCompulsory = rs.getBoolean("supplement_compulsory");
                boolean wheelchairAccess = rs.getBoolean("wheelchair_access");
                boolean bicycleReservation = rs.getBoolean("bicycle_reservation");
                boolean anyWeatherCondition = rs.getBoolean("any_weather_condition");
                boolean budapestPass = rs.getBoolean("budapest_pass");

                insertService(con, (trainId % 2 == 0 ? trainId + 2 : trainId - 2),
                        suburban, longDistance, firstClass, secondClass, reservationCompulsory,
                        supplementCompulsory, wheelchairAccess, bicycleReservation, anyWeatherCondition, budapestPass);

                insertService(con, (trainId % 2 == 0 ? trainId + 4 : trainId - 4),
                        suburban, longDistance, firstClass, secondClass, reservationCompulsory,
                        supplementCompulsory, wheelchairAccess, bicycleReservation, anyWeatherCondition, budapestPass);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertService (
            Connection con,
            int trainId,
            boolean suburban,
            boolean longDistance,
            boolean firstClass,
            boolean secondClass,
            boolean reservationCompulsory,
            boolean supplementCompulsory,
            boolean wheelchairAccess,
            boolean bicycleReservation,
            boolean anyWeatherCondition,
            boolean budapestPass
    ) throws SQLException {

        PreparedStatement stmt = con.prepareStatement("INSERT INTO service" +
                " (train_id, suburban, long_distance, first_class, second_class, reservation_compulsory, " +
                "supplement_compulsory, wheelchair_access, bicycle_reservation, " +
                "any_weather_condition, budapest_pass) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        stmt.setInt(1, trainId);
        stmt.setBoolean(2, suburban);
        stmt.setBoolean(3, longDistance);
        stmt.setBoolean(4, firstClass);
        stmt.setBoolean(5, secondClass);
        stmt.setBoolean(6, reservationCompulsory);
        stmt.setBoolean(7, supplementCompulsory);
        stmt.setBoolean(8, wheelchairAccess);
        stmt.setBoolean(9, bicycleReservation);
        stmt.setBoolean(10, anyWeatherCondition);
        stmt.setBoolean(11, budapestPass);
        stmt.executeUpdate();
    }
}

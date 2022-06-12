package com.codecool.trainscheduleapi;

import org.flywaydb.core.Flyway;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DatabaseBuilder {
    private final static int FREIGHT_TRAIN_START = 50000;
    private static List<String> freightCarTypes = new ArrayList<>();
    private static List<List<String>> cargo = new ArrayList<>();

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure().dataSource(System.getenv("DATABASE_URL"), System.getenv("DATABASE_USERNAME"), System.getenv("DATABASE_PASSWORD")).load();

        flyway.clean();
        flyway.migrate();
        buildDatabase();
    }

    private static void buildDatabase() {
        Connection con = new ConnectionUtility().getConnection();
        generateTrains(con);
        generateStops(con);
        generateServices(con);
        generateCargo(con);
    }

    private static void generateTrains(Connection con) {
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from train");

            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");

                insertTrain(con, (id % 2 == 0 ? id + 2 : id - 2), type);
                insertTrain(con, (id % 2 == 0 ? id + 4 : id - 4), type);
                insertTrain(con, FREIGHT_TRAIN_START + id, "Freight");
                insertTrain(con, (id % 2 == 0 ? FREIGHT_TRAIN_START + id + 2 : FREIGHT_TRAIN_START + id - 2), "Freight");
                insertTrain(con, (id % 2 == 0 ? FREIGHT_TRAIN_START + id + 4 : FREIGHT_TRAIN_START + id - 4), "Freight");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertTrain(Connection con, int id, String type) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO train" +
                " (id,type) VALUES (?,?)");
        stmt.setInt(1, id);
        stmt.setString(2, type);
        stmt.executeUpdate();
    }

    private static void generateStops(Connection con) {
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from stop");

            long milliSecondDelay = 1000L * 60L * 60L;

            while (rs.next()) {
                int trainId = rs.getInt("train_id");
                int distance = rs.getInt("distance");
                String name = rs.getString("name");
                Time arrivalTime = rs.getTime("arrival_time");
                Time departureTime = rs.getTime("departure_time");
                int platform = rs.getInt("platform");

                insertStop(con, (trainId % 2 == 0 ? trainId + 2 : trainId - 2),
                        distance, name, (arrivalTime == null ? null : new Time(arrivalTime.getTime() + milliSecondDelay)),
                        (departureTime == null ? null : new Time(departureTime.getTime() + milliSecondDelay)), platform);

                insertStop(con, (trainId % 2 == 0 ? trainId + 4 : trainId - 4),
                        distance, name, (arrivalTime == null ? null : new Time(arrivalTime.getTime() + milliSecondDelay * 2)),
                        (departureTime == null ? null : new Time(departureTime.getTime() + milliSecondDelay * 2)), platform);

                insertStop(con, FREIGHT_TRAIN_START + trainId, distance, name, arrivalTime, departureTime, platform);

                insertStop(con, (trainId % 2 == 0 ? FREIGHT_TRAIN_START + trainId + 2 : FREIGHT_TRAIN_START + trainId - 2),
                        distance, name, (arrivalTime == null ? null : new Time(arrivalTime.getTime() + milliSecondDelay)),
                        (departureTime == null ? null : new Time(departureTime.getTime() + milliSecondDelay)), platform);

                insertStop(con, (trainId % 2 == 0 ? FREIGHT_TRAIN_START + trainId + 4 : FREIGHT_TRAIN_START + trainId - 4),
                        distance, name, (arrivalTime == null ? null : new Time(arrivalTime.getTime() + milliSecondDelay * 2)),
                        (departureTime == null ? null : new Time(departureTime.getTime() + milliSecondDelay * 2)), platform);
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
                        longDistance, firstClass, secondClass, reservationCompulsory, supplementCompulsory,
                        wheelchairAccess, bicycleReservation, anyWeatherCondition, budapestPass);

                insertService(con, (trainId % 2 == 0 ? trainId + 4 : trainId - 4),
                        longDistance, firstClass, secondClass, reservationCompulsory, supplementCompulsory,
                        wheelchairAccess, bicycleReservation, anyWeatherCondition, budapestPass);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertService (
            Connection con,
            int trainId,
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
                " (train_id, long_distance, first_class, second_class, reservation_compulsory, " +
                "supplement_compulsory, wheelchair_access, bicycle_reservation, " +
                "any_weather_condition, budapest_pass) VALUES (?,?,?,?,?,?,?,?,?,?)");
        stmt.setInt(1, trainId);
        stmt.setBoolean(2, longDistance);
        stmt.setBoolean(3, firstClass);
        stmt.setBoolean(4, secondClass);
        stmt.setBoolean(5, reservationCompulsory);
        stmt.setBoolean(6, supplementCompulsory);
        stmt.setBoolean(7, wheelchairAccess);
        stmt.setBoolean(8, bicycleReservation);
        stmt.setBoolean(9, anyWeatherCondition);
        stmt.setBoolean(10, budapestPass);
        stmt.executeUpdate();
    }

    private static void generateCargo(Connection con) {
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from train");

            initializeCargo();
            while (rs.next()) {
                String type = rs.getString("type");
                if(!type.toLowerCase().equals("freight"))
                    continue;
                int trainId = rs.getInt("id");

                int numberOfCars = new Random().nextInt(10) + 10;
                for (int i = 0; i < numberOfCars; i++) {
                    int carTypeNumber = new Random().nextInt(freightCarTypes.size());
                    int cargoNumber = new Random().nextInt(cargo.get(carTypeNumber).size());

                    PreparedStatement stmt = con.prepareStatement("INSERT INTO cargo" +
                            " (train_id, name, car_type) VALUES (?,?,?)");
                    stmt.setInt(1, trainId);
                    stmt.setString(2, cargo.get(carTypeNumber).get(cargoNumber));
                    stmt.setString(3, freightCarTypes.get(carTypeNumber));
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initializeCargo() {
        freightCarTypes = new ArrayList<>();
        cargo = new ArrayList<>();

        freightCarTypes.addAll(Arrays.asList(
                "boxcar", "hopper car", "flatcar", "tank car", "gondola", "coil car", "refrigerator car", "specialty car"
        ));

        cargo.add(new ArrayList<String>(Arrays.asList("consumer packaged goods", "auto parts", "paper reams", "canned goods", "bagged products")));
        cargo.add(new ArrayList<String>(Arrays.asList("coal", "ore", "salt", "sand", "grain & wheat", "corn", "sugar", "fertilizer")));
        cargo.add(new ArrayList<String>(Arrays.asList("poles & pipes", "logs & cut lumber", "steel plates & beams", "wind turbines", "machinery & equipment", "intermodal containers")));
        cargo.add(new ArrayList<String>(Arrays.asList("oil", "water", "chemicals", "petroleum-based products", "liquid hydrogen")));
        cargo.add(new ArrayList<String>(Arrays.asList("scrap metal", "steel plates & coils", "rail track & ties", "gravel", "construction debris", "miscellaneous waste")));
        cargo.add(new ArrayList<String>(Arrays.asList("steel coils", "copper coils", "plastic tubing")));
        cargo.add(new ArrayList<String>(Arrays.asList("frozen meat & fish", "fresh produce", "milk", "beer", "ice")));
        cargo.add(new ArrayList<String>(Arrays.asList("automotive vehicles", "ballast", "aggregate", "miscellaneous items")));
    }
}

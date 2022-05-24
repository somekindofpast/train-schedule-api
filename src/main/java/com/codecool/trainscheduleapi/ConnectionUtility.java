package com.codecool.trainscheduleapi;

import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class ConnectionUtility {
    public Connection getConnection(){
        Connection connection = null;
        String userName = System.getenv("DATABASE_USERNAME");
        String password = System.getenv("DATABASE_PASSWORD");
        String url = System.getenv("DATABASE_URL");

        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

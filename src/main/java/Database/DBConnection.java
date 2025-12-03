package Database;

import javafx.application.Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DBConnection {

    private static final String HOSTNAME = "srvcis3270.mysql.database.azure.com";
    private static final String DATABASE = "test";
    private static final String USERNAME = "Raquel3v";
    private static final String PASSWORD = "Password!";
    private static final String URL =  "jdbc:mysql://" + HOSTNAME + ":3306/" + DATABASE + "?useSSL=true&serverTimezone=UTC";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(" Failed to connect to database.");
            return null;
        }
    }
}

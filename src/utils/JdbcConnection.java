package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//public class JdbcConnection {
//    private String jdbcURL = "jdbc:mysql://localhost:3306/e-commerce?useSSL=false";
//    private String jdbcUsername = "root";
//    private String jdbcPassword = "";
//
//    public Connection getConnection() {
//        Connection connection = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return connection;
//    }
//}
public class JdbcConnection {
    private static JdbcConnection instance;
    private String jdbcURL = "jdbc:mysql://localhost:3306/e-commerce?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    // Private constructor to prevent instantiation from outside
    private JdbcConnection() {
    }

    // Static method to get the singleton instance
    public static JdbcConnection getInstance() {
        if (instance == null) {
            instance = new JdbcConnection();
        }
        return instance;
    }

    // Method to get the connection
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
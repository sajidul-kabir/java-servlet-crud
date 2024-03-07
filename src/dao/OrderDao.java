package dao;

import entity.Order;
import entity.Product;
import utils.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private JdbcConnection jdbcConnection;

    public OrderDao(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }
    private static final String SELECT_ALL_ORDERS = "select * from orders";
    private static final String INSERT_Orders_SQL = "INSERT INTO orders" + "  (customer_name,product,transaction,address) VALUES "
            + " (?, ?,?,?);";



    public List<Order> selectAllOrders() {

        List<Order> Orders = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = jdbcConnection.getConnection();


             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (((ResultSet) rs).next()) {
                int id = rs.getInt("id");
                String customer_name = rs.getString("customer_name");
                Double transaction = rs.getDouble("transaction");
                String address = rs.getString("address");
                String product = rs.getString("product");
                Orders.add(new Order(id,customer_name,address,product,transaction));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return Orders;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public void insertOrder(Order order) {
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_Orders_SQL);) {
            System.out.println(preparedStatement);

            preparedStatement.setString(1, order.getCustomer_name());
            preparedStatement.setString(2, order.getProduct());
            preparedStatement.setDouble(3, order.getTransaction() );
            preparedStatement.setString(4, order.getAddress() );

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
}

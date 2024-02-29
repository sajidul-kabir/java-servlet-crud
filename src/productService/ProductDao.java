package productService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/e-commerce?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    public ProductDao() {
    }

    //    private static final String INSERT_ProductS_SQL = "INSERT INTO Products" + "  (name, email, country) VALUES "
//            + " (?, ?, ?);";
//
//    private static final String SELECT_Product_BY_ID = "select id,name,email,country from Products where id =?";
    private static final String SELECT_ALL_PRODUCTS = "select * from product";
//    private static final String DELETE_ProductS_SQL = "delete from Products where id = ?;";
//    private static final String UPDATE_ProductS_SQL = "update Products set name = ?,email= ?, country =? where id = ?;";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public List<Product> selectAllProducts() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Product> Products = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (((ResultSet) rs).next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float price = rs.getFloat("price");
                Products.add(new Product(id,name,price));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return Products;
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
}

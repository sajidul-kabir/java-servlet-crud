package dao;

import entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/e-commerce?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    public ProductDao() {
    }


//
    private static final String SELECT_Product_BY_ID = "select id,name,price from product where id =?";
    private static final String SELECT_ALL_PRODUCTS = "select * from product";
    private static final String INSERT_ProductS_SQL = "INSERT INTO product" + "  (name, price) VALUES "
            + " (?, ?);";
    private static final String UPDATE_ProductS_SQL = "update product set name = ?,price= ? where id=?;";
    private static final String DELETE_ProductS_SQL = "delete from product where id = ?;";

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

    public Product selectProductById(int id){
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_Product_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                 product = new Product( name, price);
            }
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
    }
    public void insertProduct(Product product){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ProductS_SQL);) {
            System.out.println(preparedStatement);

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice() );

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }


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

    public void updateProduct(Product updatedProduct) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ProductS_SQL);) {

            statement.setString(1, updatedProduct.getName());
            statement.setDouble(2, updatedProduct.getPrice());
            statement.setInt(3, updatedProduct.getId());
            System.out.println(statement);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteProduct(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ProductS_SQL);) {

            statement.setInt(1, id);
            System.out.println(statement);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

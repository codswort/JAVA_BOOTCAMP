package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static edu.school21.repositories.Query.*;

public class ProductsRepositoryJdbcImpl {
    private Connection connection;
    public ProductsRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    public List<Product> findAll() {
        String query = FIND_ALL.getQuery();
        List<Product> productList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Long productID = rs.getLong("id");
                    String productName = rs.getString("name");
                    Double productPrice = rs.getDouble("price");
                    Product product = new Product(productID, productName, productPrice);
                    productList.add(product);
                }
                return productList;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Optional<Product> findById(Long id) {
        String query = FIND_BY_ID.getQuery();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                Long productID = rs.getLong("id");
                String productName = rs.getString("name");
                Double productPrice = rs.getDouble("price");
                Product product = new Product(productID, productName, productPrice);
                return Optional.of(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }


    public void update(Product product) {
        String sql = UPDATE.getQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Error of updating the product");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    void save(Product product) {
        String sql = SAVE.getQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, product.getId());
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void delete(Long id) {
        String sql = DELETE.getQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

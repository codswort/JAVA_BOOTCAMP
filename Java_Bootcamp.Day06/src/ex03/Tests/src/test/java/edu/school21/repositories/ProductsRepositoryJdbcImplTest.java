package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(4l, "product4", 140d);
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>(Arrays.asList(
            new Product(1l, "product1", 110d),
            new Product(2l, "product2", 120d),
            new Product(3l, "product3", 130d),
            new Product(4l, "product4", 140d),
            new Product(5l, "product5", 150d))
    );

    final Product EXPECTED_UPDATED_PRODUCT = new Product(2l, "product2", 210d);
    final Product EXPECTED_SAVED_PRODUCT = new Product(6l, "product6", 160d);
    private DataSource dataSource;
    private ProductsRepositoryJdbcImpl productsRepository;


    @BeforeEach
    public void init() throws SQLException {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    public void testFindByID() {
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, productsRepository.findById(4l).get());
    }

    @Test
    public void testFindAll() {
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepository.findAll());
    }

    @Test
    public void testUpdate() {
        productsRepository.update(new Product(2l, "product2", 210d));
        assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepository.findById(2l).get());
    }

    @Test
    public void testSave() {
        productsRepository.save(new Product(6l, "product6", 160d));
        assertEquals(EXPECTED_SAVED_PRODUCT, productsRepository.findById(6l).get());
    }

    @Test
    public void testDelete() {
        productsRepository.delete(3l);
        assertFalse(productsRepository.findById(3l).isPresent());
    }
}

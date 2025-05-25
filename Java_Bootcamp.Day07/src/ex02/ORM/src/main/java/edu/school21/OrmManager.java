package edu.school21;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrmManager {

    private Connection connection;
    private Object entity;
    public OrmManager(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    public void create(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(OrmEntity.class)) {
                createTable(clazz);
            }
        }
    }
    public void dropTable(Class<?> clazz) {
        String tableName = clazz.getAnnotation(OrmEntity.class).table();
        StringBuilder sql = new StringBuilder("DROP TABLE IF EXISTS " + tableName + " CASCADE");
        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            statement.execute();
            System.out.println("Executed query: " + sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error dropping table", e);
        }
    }
    private void createTable(Class<?> clazz) {
        String tableName = clazz.getAnnotation(OrmEntity.class).table();
        dropTable(clazz);
        String query = creatingQuery(clazz, tableName);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
            System.out.println("Executed query: " + query);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table", e);
        }
    }
    private String creatingQuery(Class<?> clazz, String tableName) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> sql = new ArrayList<>();
        String startQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                sql.add(field.getName() + " SERIAL PRIMARY KEY");
            } else if (field.isAnnotationPresent(OrmColumn.class)) {
                sql.add(field.getName() + " " + getSqlType(field));
            }
        }
        String query = startQuery + String.join(", ", sql) + ");";
        return query;
    }
    private String getSqlType(Field field) {
        String name = field.getType().getSimpleName();
        OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
        if (name.equals("String")) {
            return "varchar(" + ormColumn.length() + ")";
        } else if (name.equals("Integer")) {
            return "int";
        } else if (name.equals("Double")) {
            return "double";
        } else if (name.equals("Boolean")) {
            return "boolean";
        } else if (name.equals("Long")) {
            return "bigint";
        }
        return "unknown";
    }
    private String savingQuery(Class<?> clazz, String tableName) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> columnNames = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(OrmColumnId.class)) continue;
            if (field.isAnnotationPresent(OrmColumn.class)) {
                columnNames.add(field.getName());
                try {
                    Object value = field.get(entity);
                    values.add(value == null ? "NULL" : "'" + value + "'");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to get the value of the field " + field.getName(), e);
                }
            }
        }
        String startQuery = "INSERT INTO " + tableName + " (" + String.join(", ", columnNames) + ")";
        String valuePart = " VALUES (" + String.join(", ", values) + ")";
        return startQuery + valuePart;
    }
    public void save(Object entity) {
        this.entity = entity;
        Class<?> clazz = entity.getClass();
        if (!clazz.isAnnotationPresent(OrmEntity.class)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not entity (OrmEntity)");
        }
        String tableName = clazz.getAnnotation(OrmEntity.class).table();
        String query = savingQuery(clazz, tableName);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
            System.out.println("Executed query: " + query);
        } catch (SQLException e) {
            throw new RuntimeException("Error saving object", e);
        }
    }

    private String updatingQuery(Class<?> clazz, String tableName) {
        Field[] fields = clazz.getDeclaredFields();
        Object idValue = null;
        List<String> updates = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    idValue = field.get(entity);
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    Object value = field.get(entity);
                    updates.add(field.getName() + " = " + (value == null ? "NULL" : "'" + value + "'"));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to get the value of the field " + field.getName(), e);
            }
        }
        if (idValue == null) throw new IllegalArgumentException("Cannot update entity without an ID");
        String query = "UPDATE " + tableName + " SET " + String.join(", ", updates) + " WHERE id = " + idValue;
        return query;
    }
    public void update(Object entity) {
        this.entity = entity;
        Class<?> clazz = entity.getClass();
        if (!clazz.isAnnotationPresent(OrmEntity.class)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not entity (OrmEntity)");
        }
        String tableName = clazz.getAnnotation(OrmEntity.class).table();
        String query = updatingQuery(clazz, tableName);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) throw new RuntimeException("Update failed: No record found with ID = " + rowsUpdated);
            System.out.println("Executed query: " + query);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating object", e);
        }
    }

    public <T> T findById(Long id, Class<T> aClass) {
        if (!aClass.isAnnotationPresent(OrmEntity.class)) {
            throw new IllegalArgumentException("Class " + aClass.getName() + " is not entity (OrmEntity)");
        }
        String tableName = aClass.getAnnotation(OrmEntity.class).table();
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    T entity = aClass.getDeclaredConstructor().newInstance();
                    for (Field field : aClass.getDeclaredFields()) {
                        field.setAccessible(true);

                        if (field.isAnnotationPresent(OrmColumnId.class)) {
                            field.set(entity, resultSet.getLong("id"));
                        } else if (field.isAnnotationPresent(OrmColumn.class)) {
                            field.set(entity, resultSet.getObject(field.getName(), field.getType()));
                        }
                    }
                    return entity;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error searching for the object " + aClass.getName() + " с ID = " + id, e);
        }
    }
}

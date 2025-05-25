package edu.school21.repositories;

public enum Query {
    FIND_BY_ID("select * FROM Store.Products p WHERE p.id = ?"),
    FIND_ALL("select * FROM Store.Products"),
    UPDATE("update Store.Products SET name = ?, price = ? WHERE id = ?"),
    SAVE("insert into Store.Products (id, name, price) values (?, ?, ?)"),
    DELETE("delete from Store.Products where id = ?");


    private final String QUERY;

    Query(String QUERY) {
        this.QUERY = QUERY;
    }

    public String getQuery() {
        return QUERY;
    }
}

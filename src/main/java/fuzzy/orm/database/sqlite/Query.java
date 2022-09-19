package fuzzy.orm.database.sqlite;

public class Query {
    private String query;
    private Query(String query) {
        this.query = query;
    }

    private Query append(String sql) {
        query += " " + sql;
        return this;
    }

    @Override
    public String toString() {
        return query;
    }

    public Query where(String scope) {
        String sql = String.format("WHERE %s", scope);
        return append(sql);
    }

    public Query limit(String limit) {
        String sql = String.format("LIMIT %s", limit);
        return append(sql);
    }

    public Query order(String order) {
        String sql = String.format("ORDER BY %s", order);
        return append(sql);
    }

    public Query order(String order, String decending) {
        String sql = String.format("%s %s", order, decending);
        return order(sql);
    }

    public static Query create(String table, String props) {
        String sql = String.format("CREATE TABLE %s ( %s )", table, props);
        return new Query(sql);
    }

    public static Query select(String table) {
        String sql = String.format("SELECT * FROM %s", table);
        return new Query(sql);
    }

    public static Query insert(String table, String key, String values) {
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", table, key, values);
        return new Query(sql);
    }

    public static Query update(String table, String props) {
        String sql = String.format("UPDATE %s SET %s", table, props);
        return new Query(sql);
    }

    public static Query delete(String table) {
        String sql = String.format("DELETE FROM %s", table);
        return new Query(sql);
    }
}

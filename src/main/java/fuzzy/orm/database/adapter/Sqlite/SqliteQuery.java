package fuzzy.orm.database.adapter.Sqlite;

import fuzzy.orm.database.request.Request;
import fuzzy.orm.model.Model;
import java.util.Map;

public class SqliteQuery {

    Request request;

    public SqliteQuery(Request request)
    {
        this.request = request;
    }

    public String getSQL() throws UnsupportedOperationException
    {
        switch (request.type())
        {
            case READ -> {
                return read();
            }
            case CREATE -> {
                return create();
            }
            case UPDATE -> {
                return update();
            }
            case DELETE -> {
                return delete();
            }
            case LAST -> {
                return last();
            }
            case CREATE_TABLE -> {
                return createTable();
            }
            default -> {
                throw new UnsupportedOperationException("Request type is not implemented");
            }
        }
    }

    private String createTable()
    {
        StringBuilder properties = new StringBuilder();
        int lastIndex = (request.properties().size() - 1);
        int index = 0;
        for (Map.Entry<String, Object> set : request.properties().entrySet()) {
            properties.append(columnSetup(set.getKey(), set.getValue()));
            if (index < lastIndex)
            {
                properties.append(", ");
                index++;
            }
        }

        String query = "CREATE TABLE %s ( %s )";
        return String.format(query, request.table(), properties);
    }

    private String columnSetup(String key, Object value)
    {
        String dataType = getDataType(value);
        StringBuilder column = new StringBuilder();
        column.append(key);
        column.append(" ");
        column.append(dataType);
        column.append(" ");

        if (key.equals(Model.ID))
            column.append("PRIMARY KEY");
        else if (dataType.equals("TEXT"))
            column.append("NULL");
        else
            column.append("NOT NULL");

        return column.toString();
    }

    private String getDataType(Object value)
    {
        if (value instanceof Integer)
            return "INTEGER";

        // Floating point value
        if (value instanceof Double ||
                value instanceof Float)
            return "REAL";

        // Booleans in Sqlite is stored as Integer.
        if (value instanceof Boolean)
            return "INTEGER";

        return "TEXT";
    }

    private String last()
    {
        String query = "SELECT * FROM %s ORDER BY id DESC LIMIT 1";
        return String.format(query, request.table());
    }

    private String read()
    {
        String query = "SELECT * FROM %s WHERE id = %s";
        return String.format(query, request.table(), request.properties().get(Model.ID));
    }

    private String delete()
    {
        String query = "DELETE FROM %s WHERE id = %s";
        return String.format(query, request.table(), request.properties().get(Model.ID));
    }

    private String create()
    {
        StringBuilder keys = new StringBuilder();
        StringBuilder values = new StringBuilder();
        int lastIndex = (request.properties().size() - 1);
        int index = 0;
        for (Map.Entry<String, Object> set : request.properties().entrySet()) {
            String key = String.valueOf(set.getKey());
            if (key.equals(Model.ID)) continue;

            keys.append(key);

            if (set.getValue() instanceof String)
            {
                values.append(String.format("\"%s\"", set.getValue()));
            }
            else
            {
                String value = String.valueOf(set.getValue());
                values.append(value);
            }

            if (index < lastIndex)
            {
                index++;
                keys.append(", ");
                values.append(", ");
            }
        }

        String query = "INSERT INTO %s (%s) VALUES (%s)";
        return String.format(query, request.table(), keys, values);
    }

    private String update()
    {
        StringBuilder props = new StringBuilder();
        int lastIndex = (request.properties().size() - 2);
        int index = 0;
        for (Map.Entry<String, Object> set : request.properties().entrySet()) {
            String key = String.valueOf(set.getKey());
            if (key.equals(Model.ID)) continue;

            String value = String.valueOf(set.getValue());

            if (set.getValue() instanceof String)
            {
                props.append(String.format("%s = \"%s\"", key, value));
            }
            else
            {
                props.append(String.format("%s = %s", key, value));
            }

            if (index < lastIndex)
            {
                index++;
                props.append(", ");
            }
        }

        String query = "UPDATE %s SET %s WHERE id = (%s)";
        System.out.println(String.format(query, request.table(), props, request.properties().get(Model.ID)));
        return String.format(query, request.table(), props, request.properties().get(Model.ID));
    }
}

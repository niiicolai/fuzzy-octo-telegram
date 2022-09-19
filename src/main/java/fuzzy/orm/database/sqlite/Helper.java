package fuzzy.orm.database.sqlite;

import fuzzy.orm.database.request.Request;
import fuzzy.orm.model.Model;

import java.util.Map;

public class Helper {

    public static String keys(Request request) {
        StringBuilder keys = new StringBuilder();
        int lastIndex = (request.getProps().size() - 1);
        int index = 0;
        for (Map.Entry<String, Object> set : request.getProps().entrySet()) {
            keys.append(set.getKey());
            if (index < lastIndex)
            {
                keys.append(", ");
                index++;
            }
        }
        return keys.toString();
    }

    public static String values(Request request) {
        StringBuilder values = new StringBuilder();
        int lastIndex = (request.getProps().size() - 1);
        int index = 0;
        for (Map.Entry<String, Object> set : request.getProps().entrySet()) {
            String val = String.valueOf(set.getValue());
            if (set.getValue() instanceof String) {
                val = "'" + val + "'";
            }
            values.append(val);
            if (index < lastIndex)
            {
                values.append(", ");
                index++;
            }
        }
        return values.toString();
    }

    public static String createProps(Request request)
    {
        StringBuilder properties = new StringBuilder();
        int lastIndex = (request.getProps().size() - 1);
        int index = 0;
        for (Map.Entry<String, Object> set : request.getProps().entrySet()) {
            properties.append(createProp(set.getKey(), set.getValue()));
            if (index < lastIndex)
            {
                properties.append(", ");
                index++;
            }
        }
        return properties.toString();
    }

    public static String updateProps(Request request)
    {
        StringBuilder properties = new StringBuilder();
        int lastIndex = (request.getProps().size() - 1);
        int index = 0;
        for (Map.Entry<String, Object> set : request.getProps().entrySet()) {
            properties.append(updateProp(set.getKey(), set.getValue()));
            if (index < lastIndex)
            {
                properties.append(", ");
                index++;
            }
        }
        return properties.toString();
    }

    private static String createProp(String key, Object value) {
        String dataType = getDataType(value);

        String state = "NOT NULL";
        if (key.equals(Model.ID))
            state = "PRIMARY KEY";
        else if (dataType.equals("TEXT"))
            state = "NULL";

        return String.format("%s %s %s", key, dataType, state);
    }

    private static String updateProp(String key, Object value) {
        if (value instanceof String)
            return String.format("%s = \"%s\"", key, value);
        else
            return String.format("%s = %s", key, value);
    }

    private static String getDataType(Object value)
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
}

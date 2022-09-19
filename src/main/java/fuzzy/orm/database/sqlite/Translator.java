package fuzzy.orm.database.sqlite;

import fuzzy.orm.database.request.Request;
import fuzzy.orm.database.request.Scope;
import fuzzy.orm.util.Property;

import java.util.HashMap;
import java.util.Map;

public class Translator {

    public static String interpret(Request request) {
        // Get query builder with operation information.
        Query query = createFrom(request);

        query = setWhere(query, request);
        query = setOrder(query, request);
        query = setLimit(query, request);

        return query.toString();
    }

    private static Query createFrom(Request request)
    {
        String table = request.getTable();
        switch (request.getOperation()) {
            case SELECT -> {
                return Query.select(table);
            }
            case INSERT -> {
                String keys = Helper.keys(request);
                String values = Helper.values(request);
                return Query.insert(table, keys, values);
            }
            case UPDATE -> {
                String props = Helper.updateProps(request);
                return Query.update(table, props);
            }
            case DELETE -> {
                return Query.delete(table);
            }
            case CREATE -> {
                String props = Helper.createProps(request);
                return Query.create(table, props);
            }
            default -> {
                throw new UnsupportedOperationException("Operation is not implemented");
            }
        }
    }

    private static Query setWhere(Query query, Request request) {
        HashMap<Scope, Property> scopes = request.getScopes();
        for (Map.Entry entry : scopes.entrySet()) {
            if (entry.getKey().equals(Scope.WHERE)) {
                Property prop = (Property) entry.getValue();
                String key = String.valueOf(prop.getKey());
                String val = String.valueOf(prop.getValue());
                if (prop.getValue() instanceof String)
                    val = "'" + val + "'";

                String sql = String.format("%s = %s", key, val);
                query = query.where(sql);
            }
        }
        return query;
    }

    private static Query setOrder(Query query, Request request) {
        HashMap<Scope, Property> scopes = request.getScopes();
        for (Map.Entry entry : scopes.entrySet()) {
            if (entry.getKey().equals(Scope.ORDER)) {
                Property prop = (Property) entry.getValue();
                String key = String.valueOf(prop.getKey());
                String val = String.valueOf(prop.getValue());
                String sql = String.format("%s %s", key, val);
                query = query.order(sql);
                break; // Only allow a single ORDER keyword
            }
        }
        return query;
    }

    private static Query setLimit(Query query, Request request) {
        HashMap<Scope, Property> scopes = request.getScopes();
        for (Map.Entry entry : scopes.entrySet()) {
            if (entry.getKey().equals(Scope.LIMIT)) {
                Property prop = (Property) entry.getValue();
                String val = String.valueOf(prop.getValue());
                query = query.limit(val);
                break; // Only allow a single LIMIT keyword
            }
        }
        return query;
    }
}

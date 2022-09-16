package fuzzy.orm.database.adapter.Sqlite;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import fuzzy.orm.database.adapter.Adapter;
import fuzzy.orm.database.request.Request;
import fuzzy.orm.database.request.RequestType;
import fuzzy.orm.database.response.Response;
import fuzzy.orm.database.response.ResponseCode;
import fuzzy.orm.model.Model;

public class SqliteAdapter implements Adapter {

    public static String DIRECTORY_PATH = "jdbc:sqlite:C:/sqlite/";
    private Connection connection = null;

    @Override
    public boolean connect(String database) {
        try {
            String url = getUrl(database);
            connection = DriverManager.getConnection(url);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean create(String database) {
        try {
            String url = getUrl(database);
            connection = DriverManager.getConnection(url);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        if (connection == null) return false;
        try {
            connection.close();
            connection = null;
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Response execute(Request request) {
        if (connection == null)
            return new Response(ResponseCode.ERROR, "Found no database connection", null);

        if (request.type() == RequestType.CREATE_TABLE ||
                request.type() == RequestType.DELETE)
        {
            return executeNoResult(request);
        }
        else
        {
            boolean isUpdating = request.type() == RequestType.CREATE || request.type() == RequestType.UPDATE;
            return isUpdating ? executeUpdate(request) : executeQuery(request);
        }
    }

    private Response executeUpdate(Request request) {
        try {
            Statement statement = connection.createStatement();
            SqliteQuery updateQuery = new SqliteQuery(request);
            statement.executeUpdate(updateQuery.getSQL());

            // Ensure the request has an id prop
            if (!request.properties().containsKey(Model.ID))
                request.properties().put(Model.ID, 0);

            return executeQuery(new Request(request.table(), RequestType.LAST, request.properties()));
        } catch (SQLException e) {
            return new Response(ResponseCode.ERROR, e.getMessage(), null);
        }
    }

    private Response executeQuery(Request request) {
        try {
            Statement statement = connection.createStatement();

            SqliteQuery sqliteQuery = new SqliteQuery(request);
            ResultSet resultSet = statement.executeQuery(sqliteQuery.getSQL());
            resultSet.next();
            return createResponse(request, resultSet);

        } catch (SQLException e) {
            return new Response(ResponseCode.ERROR, e.getMessage(), null);
        }
    }

    private Response executeNoResult(Request request)
    {
        try {
            Statement statement = connection.createStatement();
            SqliteQuery sqliteQuery = new SqliteQuery(request);
            statement.executeUpdate(sqliteQuery.getSQL());

            return new Response(ResponseCode.OK, "", null);

        } catch (SQLException e) {
            return new Response(ResponseCode.ERROR, e.getMessage(), null);
        }
    }

    private Response createResponse(Request request, ResultSet resultSet) {
        try {
            HashMap<String, Object> properties = new HashMap<String, Object>();
            // Read result
            for (Map.Entry<String, Object> set : request.properties().entrySet()) {
                String key = set.getKey();
                Object obj = resultSet.getObject(key);
                if (obj != null)
                {
                    properties.put(key, obj);
                }
            }
            return new Response(ResponseCode.OK, "", properties);
        } catch (SQLException e) {
            return new Response(ResponseCode.ERROR, e.getMessage(), null);
        }
    }

    private static String getUrl(String database)
    {
        return DIRECTORY_PATH + database;
    }
}

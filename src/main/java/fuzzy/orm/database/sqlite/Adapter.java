package fuzzy.orm.database.sqlite;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import fuzzy.orm.database.request.Operation;
import fuzzy.orm.database.request.Request;
import fuzzy.orm.database.response.Response;
import fuzzy.orm.database.response.ResponseCode;

public class Adapter implements fuzzy.orm.database.adapter.Adapter {

    private static String DIRECTORY_PATH = "jdbc:sqlite:C:/sqlite/";
    private Connection connection = null;

    @Override
    public boolean connect(String database) {
        try {
            String url = DIRECTORY_PATH + database;
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
            String url = DIRECTORY_PATH + database;
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

        String sql = Translator.interpret(request);

        try {
            Statement statement = connection.createStatement();
            if (request.getOperation().equals(Operation.SELECT)) {
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                HashMap<String, Object> properties = createResponseProps(request, resultSet);
                return new Response(ResponseCode.OK, "", properties);
            }
            else {
                statement.executeUpdate(sql);
                return new Response(ResponseCode.OK, "", null);
            }
        } catch (SQLException e) {
            return new Response(ResponseCode.ERROR, e.getMessage(), null);
        }
    }

    private HashMap<String, Object> createResponseProps(Request request, ResultSet resultSet) throws SQLException {
        HashMap<String, Object> pattern = request.getReponsePropsPattern();
        if (pattern == null) return null;

        HashMap<String, Object> props = new HashMap<>();
        for (Map.Entry<String, Object> set : pattern.entrySet()) {
            Object obj = resultSet.getObject(set.getKey());
            if (obj != null)
            {
                props.put(set.getKey(), obj);
            }
        }

        return props;
    }
}

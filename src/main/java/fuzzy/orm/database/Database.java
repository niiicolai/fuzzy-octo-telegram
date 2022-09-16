package fuzzy.orm.database;

import fuzzy.orm.database.adapter.Adapter;
import fuzzy.orm.database.adapter.Sqlite.SqliteAdapter;
import fuzzy.orm.database.request.Request;
import fuzzy.orm.database.response.Response;

import java.sql.SQLException;

public class Database {

    private static Adapter adapter = new SqliteAdapter();
    public static String database = "development.db";

    public static Response execute(Request request) {

        if (!adapter.connect(database))
            adapter.create(database);

        Response response = adapter.execute(request);
        adapter.disconnect();

        return response;
    }
}

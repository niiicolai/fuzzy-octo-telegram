package fuzzy.orm.database;

import fuzzy.orm.database.adapter.Adapter;
import fuzzy.orm.database.request.Request;
import fuzzy.orm.database.response.Response;

public class Database {
    private static Adapter adapter
            = new fuzzy.orm.database.sqlite.Adapter();

    public static String database = "development.db";

    public static Response execute(Request request) {

        if (!adapter.connect(database))
            adapter.create(database);

        Response response = adapter.execute(request);
        adapter.disconnect();

        return response;
    }
}

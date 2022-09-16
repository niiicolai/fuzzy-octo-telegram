package fuzzy.orm.database.adapter;

import fuzzy.orm.database.request.Request;
import fuzzy.orm.database.response.Response;

import java.sql.SQLException;

public interface Adapter {
    boolean connect(String database);
    boolean create(String database);
    boolean disconnect();
    Response execute(Request request);
}

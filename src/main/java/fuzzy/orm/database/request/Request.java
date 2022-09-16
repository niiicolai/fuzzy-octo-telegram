package fuzzy.orm.database.request;

import java.util.HashMap;

public record Request(String table, RequestType type, HashMap<String, Object> properties) {
}

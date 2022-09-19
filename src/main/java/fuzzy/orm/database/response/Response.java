package fuzzy.orm.database.response;

import java.util.HashMap;

public record Response(ResponseCode code, String error, HashMap<String, Object> props) {
}
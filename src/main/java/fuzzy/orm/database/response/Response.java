package fuzzy.orm.database.response;

import fuzzy.orm.database.request.RequestType;

import java.util.HashMap;

public record Response(ResponseCode code, String error, HashMap<String, Object> properties) {
}
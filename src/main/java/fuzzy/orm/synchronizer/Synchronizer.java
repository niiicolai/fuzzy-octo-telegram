package fuzzy.orm.synchronizer;

import fuzzy.orm.database.Database;
import fuzzy.orm.database.request.Request;
import fuzzy.orm.database.request.RequestType;
import fuzzy.orm.database.response.Response;
import fuzzy.orm.database.response.ResponseCode;
import fuzzy.orm.model.Model;

import java.util.HashMap;
import java.util.Map;

public class Synchronizer {

    public static boolean execute(Model model, RequestType type)
    {
        Request request = new Request(model.getClass().getSimpleName(), type, model.getProperties());
        Response response = Database.execute(request);

        if (response.code().equals(ResponseCode.OK))
        {
            synchronizeProperties(model, response.properties());
            return true;
        }
        else
        {
            System.out.println(response.error());
            return false;
        }
    }

    private static void synchronizeProperties(Model model, HashMap<String, Object> properties)
    {
        if (properties == null) return;
        for (Map.Entry<String, Object> set : properties.entrySet()) {
            model.set(set.getKey(), set.getValue());
        }
    }
}

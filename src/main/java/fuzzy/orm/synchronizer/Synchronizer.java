package fuzzy.orm.synchronizer;

import fuzzy.orm.database.Database;
import fuzzy.orm.database.request.Operation;
import fuzzy.orm.database.request.Request;
import fuzzy.orm.database.response.Response;
import fuzzy.orm.database.response.ResponseCode;
import fuzzy.orm.model.Model;

import java.util.HashMap;
import java.util.Map;

public class Synchronizer {

    public static void execute(Model model, Request request)
    {
        Response response = Database.execute(request);

        if (response.code().equals(ResponseCode.OK)) {
            synchronize(model, response);

            Operation operation = request.getOperation();
            if (operation.equals(Operation.INSERT) || operation.equals(Operation.UPDATE)) {
                Model.select(model).order(Model.ID, "DESC").invoke();
            }
        } else {
            System.out.println("Synchronizer(ERROR): " + response.error());
        }
    }

    private static void synchronize(Model model, Response response) {
        HashMap<String, Object> props = response.props();
        if (props == null) return;

        for (Map.Entry<String, Object> set : model.getProps().entrySet()) {
            Object obj = props.get(set.getKey());
            if (obj != null)
            {
                model.setProperty(set.getKey(), obj);
            }
        }
    }
}

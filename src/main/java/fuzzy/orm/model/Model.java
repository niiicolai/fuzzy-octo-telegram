package fuzzy.orm.model;

import fuzzy.orm.database.request.Operation;
import fuzzy.orm.database.request.RequestBuilder;

import java.util.HashMap;

public class Model
{
    public static final String ID = "id";
    protected HashMap<String, Object> props
            = new HashMap<>();

    public Model() {
        props.put(ID, 0);
    }

    public int getId() {
        return (int)props.get(ID);
    }

    public void setProperty(String key, Object value) {
        props.put(key, value);
    }

    public Object getProperty(String key) {
        return props.get(key);
    }

    public HashMap<String, Object> getProps() {
        return props;
    }

    /* Operations */

    public static RequestBuilder find(Model model, int id) {
        RequestBuilder builder = new RequestBuilder(model);
        builder.setOperation(Operation.SELECT);
        builder.where(ID, id);
        return builder;
    }

    public static RequestBuilder select(Model model) {
        RequestBuilder builder = new RequestBuilder(model);
        builder.setOperation(Operation.SELECT);
        return builder;
    }

    public static RequestBuilder insert(Model model) {
        RequestBuilder builder = new RequestBuilder(model);
        builder.setOperation(Operation.INSERT);
        return builder;
    }

    public RequestBuilder update() {
        RequestBuilder builder = new RequestBuilder(this);
        builder.setOperation(Operation.UPDATE);
        return builder;
    }

    public RequestBuilder delete() {
        RequestBuilder builder = new RequestBuilder(this);
        builder.setOperation(Operation.DELETE);
        builder.where(ID, getId());
        return builder;
    }
}
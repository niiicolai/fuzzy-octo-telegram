package fuzzy.orm.database.request;

import fuzzy.orm.util.Property;

import java.util.HashMap;

public class Request {
    private Operation operation;
    private HashMap<Scope, Property> scopes
            = new HashMap<>();
    private HashMap<String, Object> props
            = new HashMap<>();

    private HashMap<String, Object> reponsePropsPattern
            = new HashMap<>();

    private String table;

    public Request(String table) {
        this.table = table;
    }

    public Operation getOperation() {
        return operation;
    }

    public HashMap<Scope, Property> getScopes() {
        return scopes;
    }

    public HashMap<String, Object> getProps() {
        return props;
    }

    public String getTable() {
        return table;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void addScope(Scope scope, String key, Object value)
    {
        scopes.put(scope, new Property(key, value));
    }

    public void addProp(String key, Object value)
    {
        props.put(key, value);
    }

    public HashMap<String, Object> getReponsePropsPattern() {
        return reponsePropsPattern;
    }

    public void setReponsePropsPattern(HashMap<String, Object> pattern) {
        reponsePropsPattern = pattern;
    }
}

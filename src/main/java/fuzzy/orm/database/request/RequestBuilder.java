package fuzzy.orm.database.request;

import fuzzy.orm.model.Model;
import fuzzy.orm.synchronizer.Synchronizer;

public class RequestBuilder {
    private Model model;
    private Request request;

    public RequestBuilder(Model model) {
        this.request = new Request(model.getClass().getSimpleName());
        this.model = model;

        request.setReponsePropsPattern(model.getProps());
    }

    public void setOperation(Operation operation) {
        request.setOperation(operation);
    }

    @Override
    public String toString() {
        return request.toString();
    }

    public RequestBuilder insertProp(String key, Object value) {
        request.addProp(key, value);
        return this;
    }

    public RequestBuilder where(String key, Object value) {
        request.addScope(Scope.WHERE, key, value);
        return this;
    }

    public RequestBuilder not(String key, Object value) {
        request.addScope(Scope.NOT, key, value);
        return this;
    }

    public RequestBuilder order(String key, String descending) {
        request.addScope(Scope.ORDER, key, descending);
        return this;
    }

    public RequestBuilder limit(int limit) {
        request.addScope(Scope.LIMIT, "", limit);
        return this;
    }

    public Model invoke()
    {
        Synchronizer.execute(model, request);
        return model;
    }
}

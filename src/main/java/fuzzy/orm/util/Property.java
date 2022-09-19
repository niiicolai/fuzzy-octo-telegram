package fuzzy.orm.util;

public class Property {
    private String key;
    private Object value;

    public Property(String key, Object value)
    {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}

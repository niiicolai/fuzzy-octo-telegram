package fuzzy.orm.model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class PropertyModel {

    /**
     * A hashmap containing the
     * model's properties.
     */
    HashMap<String, Object> properties;

    public PropertyModel()
    {
        this.properties = new HashMap<>();
    }

    /**
     * Add or update a property.
     */
    public void set(String key, Object value)
    {
        properties.put(key, value);
    }

    public void addProperties(HashMap<String, Object> properties)
    {
        for (Map.Entry<String, Object> set : properties.entrySet()) {
            set(set.getKey(), set.getValue());
        }
    }

    /**
     * Overrides the current properties.
     */
    public void setProperties(HashMap<String, Object> properties)
    {
        this.properties = properties;
    }

    /**
     * return the current properties.
     */
    public HashMap<String, Object> getProperties()
    {
        return properties;
    }

    /**
     * Return a property if the hashmap
     * contains the property key.
     */
    public Object get(String key) throws NoSuchElementException
    {
        if (!properties.containsKey(key))
            throw new NoSuchElementException("Couldn't find a property with the provided key!");

        return properties.get(key);
    }

    /**
     * Return true if the hashmap
     * contains the property key.
     */
    public boolean contains(String key)
    {
        return properties.containsKey(key);
    }

    /**
     * Return the number of properties.
     */
    public int size()
    {
        return properties.size();
    }
}
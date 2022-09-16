package fuzzy.orm.model;

import fuzzy.orm.database.request.RequestType;
import fuzzy.orm.synchronizer.Synchronizer;

import java.util.HashMap;

public class Model extends PropertyModel
{
    public static final String ID = "id";
    protected boolean isSynchronized;

    public Model()
    {
        super();
    }

    public Model(int id)
    {
        super();
        set(ID, id);
    }

    public Model(HashMap<String, Object> properties)
    {
        super();
        addProperties(properties);
    }

    public int getId() throws IllegalStateException
    {
        if (!contains(ID)) {
            String error = "The object has no id property!";
            throw new IllegalStateException(error);
        }

        return (int)get(ID);
    }

    public boolean create() throws IllegalStateException
    {
        if (isSynchronized)
        {
            String error = "The object is already created!";
            throw new IllegalStateException(error);
        }

        isSynchronized = Synchronizer.execute(this, RequestType.CREATE);
        return isSynchronized;
    }

    public boolean read() throws IllegalStateException
    {
        isSynchronized = Synchronizer.execute(this, RequestType.READ);
        return isSynchronized;
    }

    public boolean update() throws IllegalStateException
    {
        if (!isSynchronized)
        {
            String error = "The object should be created/read before it can be updated!";
            throw new IllegalStateException(error);
        }
        isSynchronized = Synchronizer.execute(this, RequestType.UPDATE);
        return isSynchronized;
    }

    public boolean delete() throws IllegalStateException
    {
        if (!isSynchronized)
        {
            String error = "The object should be created/read before it can be deleted!";
            throw new IllegalStateException(error);
        }

        if (Synchronizer.execute(this, RequestType.DELETE))
        {
            isSynchronized = false;
            return true;
        }

        return false;
    }
}
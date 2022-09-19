package models;

import fuzzy.orm.database.request.RequestBuilder;
import fuzzy.orm.model.Model;

public class Car extends Model
{
    private Car ()
    {
        super();
		setProperty("name", "");

    }

    public Car (String name)
    {
        super();
		setProperty("name", name);
    }

	public String getName()
	{
		return (String)getProperty("name");
	}

	public void setPropertyName(String name)
	{
		setProperty("name", name);
	}

    public static RequestBuilder find(int id) {
		Car model = new Car();
		return Model.find(model, id);
	}

	public static RequestBuilder select() {
		Car model = new Car();
		return Model.select(model);
	}

	public static RequestBuilder insert() {
		Car model = new Car();
		return Model.insert(model);
	}
}
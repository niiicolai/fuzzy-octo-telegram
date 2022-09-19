package models;

import fuzzy.orm.database.request.RequestBuilder;
import fuzzy.orm.model.Model;

public class SuperHero extends Model
{
	private SuperHero () {
		super();
		setProperty("pseudonym", "");
		setProperty("realName", "");
		setProperty("strength", 0.0);
	}

	public String getPseudonym()
	{
		return (String)getProperty("pseudonym");
	}

	public String getRealName()
	{
		return (String)getProperty("realName");
	}

	public double getStrength()
	{
		return (double)getProperty("strength");
	}

	public void setPseudonym(String pseudonym)
	{
		setProperty("pseudonym", pseudonym);
	}

	public void setRealName(String realName)
	{
		setProperty("realName", realName);
	}

	public void setStrength(double strength)
	{
		setProperty("strength", strength);
	}

	public static RequestBuilder find(int id) {
		SuperHero superHero = new SuperHero();
		return Model.find(superHero, id);
	}

	public static RequestBuilder select() {
		SuperHero superHero = new SuperHero();
		return Model.select(superHero);
	}

	public static RequestBuilder insert() {
		SuperHero superHero = new SuperHero();
		return Model.insert(superHero);
	}
}
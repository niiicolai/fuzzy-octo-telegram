package models;

import fuzzy.orm.model.Model;

public class SuperHero extends Model
{
    public SuperHero (int id)
    {
        super(id);
		set("pseudonym", "");
		set("realName", "");
		set("strength", 0.0);

    }

    public SuperHero (String pseudonym, String realName, double strength)
    {
        super();
		set("pseudonym", pseudonym);
		set("realName", realName);
		set("strength", strength);
    }

	public String getPseudonym()
	{
		return (String)get("pseudonym");
	}

	public String getRealName()
	{
		return (String)get("realName");
	}

	public double getStrength()
	{
		return (double)get("strength");
	}

	public void setPseudonym(String pseudonym)
	{
		set("pseudonym", pseudonym);
	}

	public void setRealName(String realName)
	{
		set("realName", realName);
	}

	public void setStrength(double strength)
	{
		set("strength", strength);
	}

}
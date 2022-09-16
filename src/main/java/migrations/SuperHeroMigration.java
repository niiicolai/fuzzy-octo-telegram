package migrations;

import fuzzy.orm.database.Database;
import fuzzy.orm.database.request.Request;
import fuzzy.orm.database.request.RequestType;
import fuzzy.orm.database.response.Response;

import java.util.HashMap;

public class SuperHeroMigration
{
    public static void main(String[] args) {
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("id", 0);
		properties.put("pseudonym", "");
		properties.put("realName", "");
		properties.put("strength", 0.0);

        Request request = new Request("SuperHero", RequestType.CREATE_TABLE, properties);
        Response response = Database.execute(request);

        System.out.println("Code: " + response.code());
        System.out.println("Error: " + response.error());
    }
}
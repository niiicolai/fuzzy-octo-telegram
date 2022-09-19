package migrations;

import fuzzy.orm.database.Database;
import fuzzy.orm.database.request.Operation;
import fuzzy.orm.database.request.Request;
import fuzzy.orm.database.response.Response;

public class SuperHeroMigration
{
    public static void main(String[] args) {

        Request request = new Request("SuperHero");
        request.setOperation(Operation.CREATE);
        request.addProp("id", 0);
        request.addProp("pseudonym", "");
        request.addProp("realName", "");
        request.addProp("strength", 0.0);

        Response response = Database.execute(request);

        System.out.println("Code: " + response.code());
        System.out.println("Error: " + response.error());
    }
}
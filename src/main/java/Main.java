import fuzzy.orm.model.Model;
import models.Car;
import models.SuperHero;

public class Main {
    public static void main(String[] args)
    {
        SuperHero hero1 = (SuperHero) SuperHero
                .find(10) // READ
                .invoke();

        SuperHero hero2 = (SuperHero) SuperHero
                .find(10)  // READ + UPDATE
                .invoke()
                .update()
                .insertProp("realName", "Unknown")
                .insertProp("strength", 123)
                .invoke();

        SuperHero hero3 = (SuperHero) SuperHero
                .insert()  // CREATE
                .insertProp("pseudonym", "Unknown")
                .insertProp("realName", "Unknown")
                .insertProp("strength", 123)
                .invoke();

        SuperHero hero4 = (SuperHero) SuperHero
                .insert()  // CREATE + DELETE
                .insertProp("pseudonym", "Unknown")
                .insertProp("realName", "Unknown")
                .insertProp("strength", 123)
                .invoke()
                .delete()
                .invoke();

        printHero(hero1);
        printHero(hero2);
        printHero(hero3);
        printHero(hero4);

        Car car1 = (Car) Car
                .insert()   // Create car
                .insertProp("name", "Ford")
                .invoke();

        Car car2 = (Car) Car
                .select()   // Get last created car
                .order(Model.ID, "DESC")
                .invoke();

        System.out.println(car1.getId());
        System.out.println(car1.getName());

        System.out.println(car2.getId());
        System.out.println(car2.getName());
    }

    private static void printHero(SuperHero hero) {
        System.out.println(hero.getId());
        System.out.println(hero.getPseudonym());
        System.out.println(hero.getRealName());
        System.out.println(hero.getStrength());
    }
}

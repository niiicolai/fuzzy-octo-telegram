import models.SuperHero;

import java.util.HashMap;

public class Main {
    public static void main(String[] args)
    {
        try
        {
            // Test find
            SuperHero hero = new SuperHero(1);
            hero.read();

            System.out.println(hero.getId());
            System.out.println(hero.getRealName());
            System.out.println(hero.getPseudonym());
            System.out.println(hero.getStrength());

            // Test create
            SuperHero superHero = new SuperHero("Superman", "Kent", 123);
            superHero.create();

            System.out.println(superHero.getId());
            System.out.println(superHero.getRealName());
            System.out.println(superHero.getPseudonym());
            System.out.println(superHero.getStrength());

            // Test update
            superHero.setRealName("Not Kent");
            superHero.setStrength(14212);
            superHero.update();

            System.out.println(superHero.getId());
            System.out.println(superHero.getRealName());
            System.out.println(superHero.getPseudonym());
            System.out.println(superHero.getStrength());

            // Test delete
            superHero.delete();

            SuperHero deletedHero = new SuperHero(superHero.getId());
            deletedHero.read();
            System.out.println(deletedHero.getId());
            System.out.println(deletedHero.getRealName());
            System.out.println(deletedHero.getPseudonym());
            System.out.println(deletedHero.getStrength());

        } catch (IllegalStateException e)
        {
            System.out.println(e.getMessage());
        }
    }
}

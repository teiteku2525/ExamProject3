package creature.character;

import creature.Creature;

import java.io.PrintWriter;

public class SuperHero extends Hero {
    public SuperHero(Hero hero) {
        super("覚醒" +hero.getName(),hero.getHp() - 30,hero.getWeapon());
    }
    public void attack(PrintWriter out, Creature target) {
        int damage = (int)(getWeapon().getDamage() * 2.5);
        out.println(getName() + "は" + getWeapon().getName() + getWeapon().attackMessage());
        out.println(target.getName() + "に" + damage + "のダメージを与えた！" );
        target.setHp(target.getHp() - damage);
    }
}

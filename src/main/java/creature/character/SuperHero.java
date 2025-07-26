package creature.character;

import creature.Creature;
import weapon.Weapon;

import java.io.PrintWriter;

public class SuperHero extends Hero {
    public SuperHero(Hero hero) {
        super("覚醒" +hero.getName(),hero.getHp() - 30,hero.getWeapon());
    }
    private SuperHero(String name, int hp, Weapon weapon) {
        super(name, hp, weapon);
    }
    public Creature copy() {
        return new SuperHero(this.getName(), this.getHp(),this.getWeapon());
    }
    public void attack(PrintWriter out, Creature target) {
        int damage = (int)(getWeapon().getDamage() * 2.5);
        out.println(getName() + "は" + getWeapon().getName() + getWeapon().attackMessage());
        out.println(target.getName() + "に" + damage + "のダメージを与えた！" );
        target.setHp(target.getHp() - damage);
    }
}

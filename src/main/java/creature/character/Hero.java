package creature.character;

import creature.Character;
import creature.Creature;
import weapon.Weapon;

import java.io.PrintWriter;

public class Hero extends Character {

    public Hero(String name, int hp, Weapon weapon) {
        super(name, hp,weapon);
    }
    public Creature copy() {
        return new Hero(this.getName(), this.getHp(),this.getWeapon());
    }

    public void attack(PrintWriter out,Creature target) {
        int damage = getWeapon().getDamage();
        out.println(getName() + "は" + getWeapon().getName() + getWeapon().attackMessage());
        out.println(target.getName() + "に" + damage + "のダメージを与えた！" );
        target.setHp(target.getHp() - damage);
    }



}

package creature.character;

import creature.Character;
import creature.Creature;
import weapon.Weapon;

import java.io.PrintWriter;

public class Wizard extends Character {
    int mp;
    public Wizard(String name, int hp, Weapon weapon, int mp) {
        super(name, hp, weapon);
        setMp(mp);
    }
    public Creature copy() {
        return new Wizard(this.getName(), this.getHp(),this.getWeapon(), this.getMp());
    }

    public void magic(PrintWriter out, Creature target) {
        if (getMp() < getWeapon().getCost()) {
            out.println("MPが足りない！");
            return;
        }
        int damage = getWeapon().getDamage();
        out.println(this.getName() + "は" + getWeapon().getName() + getWeapon().attackMessage());
        out.println( target.getName() + "に" + damage + "のダメージを与えた！");
        target.setHp(target.getHp() - damage);
        this.setMp(this.getMp() - getWeapon().getCost());
    }

    public void attack(PrintWriter out, Creature target) {
        out.println(this.getName() + "は石を投げた！");
        out.println(target.getName() + "に3のダメージを与えた！");
        target.setHp(target.getHp() - 3);
    }

    public String showStatus() {
        return getName() + ":HP " + getHp() + " MP " + getMp();
    }


    public void setMp(int mp) {
        this.mp = mp;
    }
    public int getMp() {
        return this.mp;
    }
}

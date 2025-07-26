package creature.character;

import creature.Character;
import creature.Creature;
import weapon.Weapon;

import java.io.PrintWriter;

public class Thief extends Character {

    private boolean guard;

    public Thief(String name, int hp, Weapon weapon){
        super(name, hp, weapon);
    }
    public Creature copy() {
        return new Thief(this.getName(), this.getHp(),this.getWeapon());
    }

    public void attack(PrintWriter out, Creature target) {
        int damage = getWeapon().getDamage() * 2;
        out.println(this.getName() + "は素早く2回攻撃した！");
        out.println(getWeapon().getName() + getWeapon().attackMessage());
        out.println(target.getName() + "に" + damage + "のダメージを与えた！");
        target.setHp(target.getHp() - damage);
    }

    public void guard(PrintWriter out) {
        out.println(this.getName() + "は身を守っている！");
        setGuard(true);
    }

    public void setHp(PrintWriter out, int hp) {
        if (getGuard()) {
            out.println("<br>しかし、" + getName() + "は攻撃を回避し、ダメージが入らなかった！");
            setGuard(false);
        }else {
            super.setHp(hp);
        }
    }
    private void setGuard(boolean isGuard) {
        this.guard = isGuard;
    }
    private boolean getGuard() {
        return guard;
    }

}

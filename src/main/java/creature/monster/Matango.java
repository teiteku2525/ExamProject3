package creature.monster;

import creature.Creature;
import creature.Monster;
import creature.character.Thief;

import java.io.PrintWriter;

public class Matango extends Monster {
    public Matango(char suffix, int hp) {
        super("お化けキノコ", suffix,hp);
    }
    public Creature copy() {
        return new Matango(this.getSuffix(),this.getHp());
    }

    public void attack(PrintWriter out, Creature target) {
        out.println(this.getName() + getSuffix() + "は体当たり攻撃！ " + target.getName() + "に6のダメージを与えた！");
        if (target instanceof Thief) {
            ((Thief) target).setHp(out,target.getHp() - 6);
        }else {
            target.setHp(target.getHp() - 6);
        }
    }


}

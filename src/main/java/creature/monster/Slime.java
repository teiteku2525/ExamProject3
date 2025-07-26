package creature.monster;

import creature.Creature;
import creature.Monster;
import creature.character.Thief;

import java.io.PrintWriter;

public final class Slime extends Monster {
    public Slime(char suffix, int hp) {
        super("スライム", suffix,hp);
    }
    public Creature copy() {
        return new Slime(this.getSuffix(),this.getHp());
    }

    public void attack(PrintWriter out, Creature target) {
        out.println(this.getName() + this.getSuffix() + "は体当たり攻撃！ " +target.getName() + "に5のダメージを与えた！");
        if (target instanceof Thief) {
            ((Thief) target).setHp(out,target.getHp() - 5);
        }else {
            target.setHp(target.getHp() - 5);
        }

    }
}

package creature.monster;

import creature.Creature;
import creature.Monster;
import creature.character.Hero;
import creature.character.Thief;

import java.io.PrintWriter;

public class Goblin extends Monster {

    public Goblin(char suffix, int hp){
        super("ゴブリン", suffix,hp);
    }
    public Creature copy() {
        return new Goblin(this.getSuffix(),this.getHp());
    }

    public void attack(PrintWriter out, Creature target) {
        out.println("<p>" + this.getName() + this.getSuffix() + "はナイフで切りつけた！ " + target.getName() + "に8のダメージを与えた！");
        if (target instanceof Thief) {
            ((Thief) target).setHp(out,target.getHp() - 8);
        }else {
            target.setHp(target.getHp() - 8);
        }
    }
}

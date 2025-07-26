package creature;

import java.io.PrintWriter;

public interface Creature {
    boolean isAlive();

    String showStatus();

    void attack(PrintWriter out,Creature target);

    String getName();
    int getHp();
    void setHp(int hp);

    Creature copy();
}

package Game;

import java.util.ArrayList;

public class Minions extends Cards {
    private int attackDamage;
    private int frozen;
    public Minions(int mana, int health, int attackDamage, String description, ArrayList<String> colors, String name) {
        super(mana, health, description, colors, name);
        this.attackDamage = attackDamage;
        this.frozen = 0;
    }
    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }
    public int getAttackDamage() {
        return attackDamage;
    }
    public void setFrozen(int frozen) {
        this.frozen = frozen;
    }
    public int getFrozen() {
        return frozen;
    }
    public void yes_frozen() {
        this.frozen = 1;
    }
    public void not_frozen() {
        this.frozen = 0;
    }

}

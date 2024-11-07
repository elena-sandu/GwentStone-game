package Game;

import java.util.ArrayList;

public class Minions extends Cards {
    private int attackDamage;
    private int frozen;
    private int attack;
    private int ability;
    private int is_tank;
    public Minions(int mana, int health, int attackDamage, String description, ArrayList<String> colors, String name) {
        super(mana, health, description, colors, name);
        this.attackDamage = attackDamage;
        this.frozen = 0;
        this.is_tank = tankcard(name);
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
    public void not_frozen() {
        this.frozen = 0;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public int getAttack() {
        return attack;
    }
    public void setAbility(int ability) {
        this.ability = ability;
    }
    public int getAbility() {
        return ability;
    }
    public void setIs_tank(int is_tank) {
        this.is_tank = is_tank;
    }
    public int getIs_tank() {
        return is_tank;
    }
    public int tankcard(String name) {
        if(name.equals("Goliath") || name.equals("Warden")) {
            return 1;
        }
        return 0;
    }
}

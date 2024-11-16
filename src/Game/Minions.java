package Game;

import java.util.ArrayList;

public class Minions extends Cards {
    private int attackDamage;
    private boolean frozen;
    private boolean attack;
    private boolean ability;
    private int is_tank;
    public Minions(int mana, int health, int attackDamage, String description, ArrayList<String> colors, String name) {
        super(mana, health, description, colors, name);
        this.attackDamage = attackDamage;
        this.frozen = false;
        this.attack = false;
        this.ability = false;
        this.is_tank = tankcard(name);
    }
    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }
    public int getAttackDamage() {
        return attackDamage;
    }
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
    public boolean getFrozen() {
        return frozen;
    }
    public void setAttack(boolean attack) {
        this.attack = attack;
    }
    public boolean getAttack() {
        return attack;
    }
    public void setAbility(boolean ability) {
        this.ability = ability;
    }
    public boolean getAbility() {
        return ability;
    }
    public void setIs_tank(int is_tank) {
        this.is_tank = is_tank;
    }
    public int getIs_tank() {
        return is_tank;
    }
    public int tankcard(String name) {
        if(name == null)
            return 0;
        if(name.equals("Goliath") || name.equals("Warden")) {
            return 1;
        }
        return 0;
    }
}

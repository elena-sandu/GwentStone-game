package Game;
import java.util.ArrayList;

public class Hero extends Cards {
    boolean hasAttacked;
    public Hero(int mana, int health, String description, ArrayList<String> colors, String name) {
        super(mana, 30, description, colors, name);
    }
    public boolean getHasAttacked() {
        return hasAttacked;
    }
    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
    public void useHAbility(ArrayList<Minions> rowAffected) {
        return;
    }
}

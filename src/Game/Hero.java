package Game;
import java.util.ArrayList;

public class Hero extends Cards {
    int hasAttacked;
    public Hero(int mana, int health, String description, ArrayList<String> colors, String name) {
        super(mana, 30, description, colors, name);
    }
    public int getHasAttacked() {
        return hasAttacked;
    }
    public void setHasAttacked(int hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}

package Game;
import java.util.ArrayList;

public class Cards {
    private int mana;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public Cards(int mana, int health, String description, ArrayList<String> colors, String name) {
        this.mana = mana;
        this.health = health;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }
    public void setMana(int mana) {
        this.mana = mana;
    }
    public int getMana() {
        return mana;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getHealth() {
        return health;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }
    public ArrayList<String> getColors() {
        return colors;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}

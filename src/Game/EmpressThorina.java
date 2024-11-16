package Game;

import java.util.ArrayList;

public class EmpressThorina extends Hero{
    public EmpressThorina(int mana, int health, String description, ArrayList<String> colors, String name) {
        super(mana, 30, description, colors, name);
    }
    @Override
    public void useHAbility(ArrayList<Minions> rowAffected) {
        Minions maxHealth = rowAffected.get(0);
        int index = 0;
        for(int i = 0; i < rowAffected.size(); i++) {
            if(maxHealth.getHealth() < rowAffected.get(i).getHealth()) {
                index = i;
                maxHealth = rowAffected.get(i);
            }
        }
        rowAffected.remove(index);
    }
}

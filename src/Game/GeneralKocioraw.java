package Game;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero{
    public GeneralKocioraw(int mana, int health, String description, ArrayList<String> colors, String name) {
        super(mana, 30, description, colors, name);
    }
    @Override
    public void useHAbility(ArrayList<Minions> rowAffected) {
        for(Minions m : rowAffected) {
            int aux = m.getAttackDamage();
            aux++;
            m.setAttackDamage(aux);
        }
    }
}

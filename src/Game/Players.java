package Game;

import java.util.ArrayList;

public class Players {
    private Hero hero;
    private ArrayList<Minions> minions = new ArrayList<>();
    private ArrayList<Minions> hand_card = new ArrayList<>();
    private int mana;
    private int manazece;
    public void setHero(Hero hero) {
        this.hero = hero;
    }
    public Hero getHero() {
        return hero;
    }
    public void setMinions(ArrayList<Minions> minions) {
        this.minions = minions;
    }
    public ArrayList<Minions> getMinions() {
        return minions;
    }
    public void setHand_card(ArrayList<Minions> hand_card) {
        this.hand_card = hand_card;
    }
    public ArrayList<Minions> getHand_card() {
        return hand_card;
    }
    public void setMana(int mana) {
        this.mana = mana;
    }
    public int getMana() {
        return mana;
    }
    public void setManazece(int manazece) {
        this.manazece = manazece;
    }
    public int getManazece() {
        return manazece;
    }
    public void add_mana() {
        if(manazece < 10) {
            manazece++;
        } else {
            manazece = 10;
        }
        mana = mana + manazece;
    }
    public void decrese_mana(int value) {
        this.mana = this.mana - value;
    }
    public void addHand_card(Minions minion) {
        hand_card.add(minion);
    }

}

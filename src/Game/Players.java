package Game;

import fileio.CardInput;

import java.util.ArrayList;

public class Players {
    private Hero hero;
    private ArrayList<Minions> minions = new ArrayList<>();
    private ArrayList<Minions> hand_card = new ArrayList<>();
    private ArrayList<ArrayList<CardInput>> allDecks;
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
    public void addHand_card(Minions minion) {
        hand_card.add(minion);
    }

    public ArrayList<ArrayList<CardInput>> getAllDecks() {
        return allDecks;
    }

    public void setAllDecks(ArrayList<ArrayList<CardInput>> allDecks) {
        this.allDecks = allDecks;
    }
}

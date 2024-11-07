package Game;

import fileio.CardInput;
import fileio.Input;
import fileio.StartGameInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SetPlayerCards {
    private Prep game_p;
    private ArrayList<Minions> playerOneMinions;
    private ArrayList<Minions> playerTwoMinions;
    private Minions card1;
    private Minions card2;
    public SetPlayerCards(Prep game_p) {
        this.game_p = game_p;
        this.playerOneMinions = new ArrayList<>();
        this.playerTwoMinions = new ArrayList<>();
    }
    public void init(StartGameInput game, Input inputData) {
        int shuffleSeed = game.getShuffleSeed();
        //carti player 1
        ArrayList<ArrayList<CardInput>> deck1 = inputData.getPlayerOneDecks().getDecks();
        ArrayList<CardInput> un_deck1 = deck1.get(game.getPlayerOneDeckIdx());
        Collections.shuffle(un_deck1, new Random(shuffleSeed));
        ArrayList<Minions> hands = new ArrayList<>();
        CardInput hand_card = un_deck1.get(0);
        un_deck1.remove(0);
        for(CardInput c : un_deck1) {
            Minions min = new Minions(c.getMana(), c.getHealth(), c.getAttackDamage(), c.getDescription(), c.getColors(), c.getName());
            playerOneMinions.add(min);
        }
        //carti player 2
        ArrayList<ArrayList<CardInput>> deck2 = inputData.getPlayerTwoDecks().getDecks();
        ArrayList<CardInput> un_deck = deck2.get(game.getPlayerTwoDeckIdx());
        Collections.shuffle(un_deck, new Random(shuffleSeed));
        CardInput hand_card2 = un_deck.get(0);
        ArrayList<Minions> hands2 = new ArrayList<>();
        un_deck.remove(0);
        for(CardInput c : un_deck) {
            Minions min = new Minions(c.getMana(), c.getHealth(), c.getAttackDamage(), c.getDescription(), c.getColors(), c.getName());
            playerTwoMinions.add(min);
        }
        game_p.start(game);
        game_p.setPlayer_rounds(0);
        game_p.getPlayer1().setMinions(playerOneMinions);
        card1 = new Minions(hand_card.getMana(), hand_card.getHealth(), hand_card.getAttackDamage(), hand_card.getDescription(), hand_card.getColors(), hand_card.getName());
        game_p.getPlayer1().addHand_card(card1);
        game_p.getPlayer2().setMinions(playerTwoMinions);
        card2 = new Minions(hand_card2.getMana(), hand_card2.getHealth(), hand_card2.getAttackDamage(), hand_card2.getDescription(), hand_card2.getColors(), hand_card2.getName());
        game_p.getPlayer2().addHand_card(card2);
    }
}

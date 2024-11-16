package Game;

import fileio.CardInput;
import fileio.StartGameInput;

import java.util.ArrayList;

public class Prep {
    private Players player1 = new Players();
    private Players player2 = new Players();
    private Game_board board = new Game_board();
    private int index_player_curent ;
    private int player_rounds;
    public void start(StartGameInput startgame) {
        Hero hero1 = new Hero(startgame.getPlayerOneHero().getMana(),
                30, startgame.getPlayerOneHero().getDescription(),startgame.getPlayerOneHero().getColors(),startgame.getPlayerOneHero().getName());
        player1.setHero(hero1);
        player1.setMana(1);
        player1.setManazece(1);
        Hero hero2 = new Hero(startgame.getPlayerTwoHero().getMana(),
                30, startgame.getPlayerTwoHero().getDescription(), startgame.getPlayerTwoHero().getColors(), startgame.getPlayerTwoHero().getName());
        player2.setHero(hero2);
        player2.setMana(1);
        player2.setManazece(1);
        index_player_curent = startgame.getStartingPlayer();
    }
    public void setPlayer1(Players player1) {
        this.player1 = player1;
    }
    public Players getPlayer1() {
        return player1;
    }
    public void setPlayer2(Players player2) {
        this.player2 = player2;
    }
    public Players getPlayer2() {
        return player2;
    }
    public void setBoard(Game_board board) {
        this.board = board;
    }
    public Game_board getBoard() {
        return board;
    }
    public void setIndex_player_curent(int index_player_curent) {
        this.index_player_curent = index_player_curent;
    }
    public int getIndex_player_curent() {
        return index_player_curent;
    }
    public void setPlayer_rounds(int player_rounds) {
        this.player_rounds = player_rounds;
    }
    public int getPlayer_rounds() {
        return player_rounds;
    }
    public void StartRound() {
        player1.add_mana();
        player2.add_mana();
        if(player1.getMinions().size() > 0) {
            Minions card = player1.getMinions().get(0);
            player1.addHand_card(card);
            player1.getMinions().remove(0);
        }
        if(player2.getMinions().size() > 0) {
            Minions card = player2.getMinions().get(0);
            player2.addHand_card(card);
            player2.getMinions().remove(0);
        }
    }
}

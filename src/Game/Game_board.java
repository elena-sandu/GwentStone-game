package Game;

import java.util.ArrayList;

public class Game_board {
    private ArrayList<ArrayList<Minions>> board;

    public Game_board() {
        board = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            board.add(new ArrayList<>());
        }
    }

    public void setBoard(ArrayList<ArrayList<Minions>> board) {
        this.board = board;
    }

    public ArrayList<ArrayList<Minions>> getBoard() {
        return board;
    }

    public int fullrow(int player_index) {
        if (player_index == 1) {
            for (int i = 2; i < 4; i++) {
                if (board.get(i).size() < 5) {
                    return 0;
                }
            }
        } else {
            for (int i = 0; i < 2; i++) {
                if (board.get(i).size() < 5) {
                    return 0;
                }
            }
        }
        return 1;
    }

    public void addCard(int player_index, Minions minion) {
        if (player_index == 1) {
            if ((minion.getIs_tank() == 1) || (minion.getName().equals("The Ripper") || minion.getName().equals("Miraj"))) {
                ArrayList<Minions> i = board.get(2);
                i.add(minion);
            } else {
                ArrayList<Minions> i = board.get(3);
                i.add(minion);

            }
        } else {
            if ((minion.getIs_tank() == 1) || (minion.getName().equals("The Ripper") || minion.getName().equals("Miraj"))) {
                ArrayList<Minions> i = board.get(1);
                i.add(minion);
            } else {
                ArrayList<Minions> i = board.get(0);
                i.add(minion);
            }
        }
    }

    public int isEnemyCard(int player_index, int card_x, int card_y) {
        if (player_index == 1) {
            if (card_x == 0 || card_x == 1) {
                return 1;
            }
        } else {
            if (card_x == 2 || card_x == 3) {
                return 1;
            }
        }
        return 0;
    }

    public int isTank(int player_index) {
        if (player_index == 1) {
            for(int j = 0; j < board.get(1).size(); j++) {
                Minions minion = board.get(1).get(j);
                if (minion.getIs_tank() == 1) {
                    return j;
                }
            }
        } else {
            for(int j = 0; j < board.get(2).size(); j++) {
                Minions minion = board.get(2).get(j);
                if (minion.getIs_tank() == 1) {
                    return j;
                }
            }
        }
        return -1;
    }

    public void remove(int x, int y) {
        ArrayList<Minions> m = board.get(x);
        if (y >= 0 && y < m.size()) {
            m.remove(y);
        }
    }
}
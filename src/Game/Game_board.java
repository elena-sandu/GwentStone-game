package Game;

public class Game_board {
    private Minions[][] board;
    public Game_board() {
        board = new Minions[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = null;
            }
        }
    }
    public void setBoard(Minions[][] board) {
        this.board = board;
    }
    public Minions[][] getBoard() {
        return board;
    }
    public int fullrow(int player_index) {
        if(player_index == 1) {
            for(int i = 2; i < 4; i++) {
                for(int j = 0; j < 5; j++) {
                    if(board[i][j] == null) {
                        return 0;
                    }
                }
            }
        } else {
            for(int i = 0; i < 2; i++) {
                for(int j = 0; j < 5; j++) {
                    if(board[i][j] == null) {
                        return 0;
                    }
                }
            }
        }
        return 1;
    }
    public void addCard(int player_index, Minions minion) {
        if(player_index == 1) {
            if((minion.getName().equals("Goliath") || minion.getName().equals("Warden")) || (minion.getName().equals("The Ripper") || minion.getName().equals("Miraj"))) {
                for(int j = 0; j < 5; j++) {
                    if(board[2][j] == null) {
                        board[2][j] = minion;
                        break;
                    }
                }
            }
            else {
                for(int j = 0; j < 5; j++) {
                    if(board[3][j] == null) {
                        board[3][j] = minion;
                        break;
                    }
                }
            }
        } else {
            if((minion.getName().equals("Goliath") || minion.getName().equals("Warden")) || (minion.getName().equals("The Ripper") || minion.getName().equals("Miraj"))) {
                for(int j = 0; j < 5; j++) {
                    if(board[1][j] == null) {
                        board[1][j] = minion;
                        break;
                    }
                }
            }
            else {
                for(int j = 0; j < 5; j++) {
                    if(board[0][j] == null) {
                        board[0][j] = minion;
                        break;
                    }
                }
            }
        }
    }

}

package Game;

public class Winner {
    int winplayer1 = 0;
    int winplayer2 = 0;
    int gamesPlayed = 0;
    public Winner() {
        this.winplayer1 = 0;
        this.winplayer2 = 0;
        this.gamesPlayed = 0;
    }
    public void PlayerOneWins() {
        winplayer1++;
        gamesPlayed++;
    }
    public void PlayerTwoWins() {
        winplayer2++;
        gamesPlayed++;
    }
    public int getWinplayer1() {
        return winplayer1;
    }
    public void setWinplayer1(int winplayer1) {
        this.winplayer1 = winplayer1;
    }
    public int getWinplayer2() {
        return winplayer2;
    }
    public void setWinplayer2(int winplayer2) {
        this.winplayer2 = winplayer2;
    }
    public int getGamesPlayed() {
        return gamesPlayed;
    }
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}

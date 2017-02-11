package cn.michaelwang.party.domain;

public class Player {
    private String name;
    private int winCount;
    private boolean winCountFirst;
    private boolean winCountLast;
    private int score;
    private boolean scoreFirst;
    private boolean scoreLast;

    public Player(String playerName) {
        this.name = playerName;
        winCount = 0;
        winCountFirst = false;
        winCountLast = false;
        score = 0;
        scoreFirst = false;
        scoreLast = false;
    }

    public String getName() {
        return name;
    }

    public int getWinCount() {
        return winCount;
    }

    public boolean getWinCountFirst() {
        return winCountFirst;
    }

    public boolean getWinCountLast() {
        return winCountLast;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean getScoreFirst() {
        return scoreFirst;
    }

    public boolean getScoreLast() {
        return scoreLast;
    }
}

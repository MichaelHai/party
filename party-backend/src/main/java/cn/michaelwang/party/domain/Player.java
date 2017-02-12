package cn.michaelwang.party.domain;

public class Player {
    private String name;
    private int winCount;
    private boolean winCountFirst;
    private boolean winCountLast;
    private int score;
    private boolean scoreFirst;
    private boolean scoreLast;
    private int rank;

    public Player(String playerName) {
        this.name = playerName;
        winCount = 0;
        winCountFirst = false;
        winCountLast = false;
        score = 0;
        scoreFirst = false;
        scoreLast = false;
        rank = -1;
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

    public int getRank() {
        return rank;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public boolean isWinCountFirst() {
        return winCountFirst;
    }

    public void setWinCountFirst(boolean winCountFirst) {
        this.winCountFirst = winCountFirst;
    }

    public boolean isWinCountLast() {
        return winCountLast;
    }

    public void setWinCountLast(boolean winCountLast) {
        this.winCountLast = winCountLast;
    }

    public boolean isScoreFirst() {
        return scoreFirst;
    }

    public void setScoreFirst(boolean scoreFirst) {
        this.scoreFirst = scoreFirst;
    }

    public boolean isScoreLast() {
        return scoreLast;
    }

    public void setScoreLast(boolean scoreLast) {
        this.scoreLast = scoreLast;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void addScore(int score) {
        this.score += score;
        if (score > 0) {
            winCount++;
        }
    }
}

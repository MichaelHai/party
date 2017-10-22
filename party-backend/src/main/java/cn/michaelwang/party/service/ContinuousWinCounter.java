package cn.michaelwang.party.service;

class ContinuousWinCounter {
    private String winner;
    private int count;

    ContinuousWinCounter() {
        this.winner = null;
        this.count = 1;
    }

    void nextWinner(String nextWinner) {
        if (nextWinner.equals(this.winner)) {
            count++;
        } else {
            winner = nextWinner;
            count = 1;
        }
    }

    int getCount() {
        return count;
    }
}

package cn.michaelwang.party.domain;

import java.util.Collections;
import java.util.List;

public class Game {
    private List<Record> records;
    private List<Player> inGamePlayers;
    private List<Player> waitingPlayers;

    public Game() {
        records = Collections.emptyList();
        inGamePlayers = Collections.emptyList();
        waitingPlayers = Collections.emptyList();
    }

    public List<Record> getRecords() {
        return records;
    }

    public List<Player> getInGamePlayers() {
        return inGamePlayers;
    }

    public List<Player> getWaitingPlayers() {
        return waitingPlayers;
    }
}

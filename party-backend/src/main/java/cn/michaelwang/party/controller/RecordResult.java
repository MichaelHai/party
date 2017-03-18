package cn.michaelwang.party.controller;

import cn.michaelwang.party.domain.Player;
import cn.michaelwang.party.domain.Record;

import java.util.List;

public class RecordResult {
    private Record record;
    private List<Player> inGamePlayers;
    private List<Player> waitingPlayers;

    public RecordResult(Record record, List<Player> inGamePlayers, List<Player> waitingPlayers) {
        this.record = record;
        this.inGamePlayers = inGamePlayers;
        this.waitingPlayers = waitingPlayers;
    }

    public Record getRecord() {
        return this.record;
    }

    public List<Player> getInGamePlayers() {
        return this.inGamePlayers;
    }

    public List<Player> getWaitingPlayers() {
        return waitingPlayers;
    }
}

package cn.michaelwang.party.controller;

import cn.michaelwang.party.domain.Player;
import cn.michaelwang.party.domain.Record;

import java.util.List;

public class RecordResult {
    private Record record;
    private List<Player> inGamePlayers;

    public RecordResult(Record record, List<Player> inGamePlayers) {
        this.record = record;
        this.inGamePlayers = inGamePlayers;
    }

    public Record getRecord() {
        return this.record;
    }

    public List<Player> getInGamePlayers() {
        return this.inGamePlayers;
    }
}

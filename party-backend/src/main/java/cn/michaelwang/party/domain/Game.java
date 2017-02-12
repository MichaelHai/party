package cn.michaelwang.party.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private List<Record> records;
    private List<Player> inGamePlayers;
    private List<Player> waitingPlayers;
    private String id;

    public Game() {
        records = new ArrayList<>();
        inGamePlayers = new ArrayList<>();
        waitingPlayers = new ArrayList<>();
        id = new Date().toString();
    }

    public List<Record> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public void addRecord(Record record) {
        this.records.add(record);
    }

    public List<Player> getInGamePlayers() {
        return Collections.unmodifiableList(inGamePlayers);
    }

    public List<Player> getWaitingPlayers() {
        return Collections.unmodifiableList(waitingPlayers);
    }

    public String getId() {
        return this.id;
    }

    public void addInGamePlayer(Player player) {
        this.inGamePlayers.add(player);
    }

    public void addWaitingPlayer(Player player) {
        this.waitingPlayers.add(player);
    }

    public void changeInGamePlayers(List<String> newInGamePlayers) {
        List<Player> waitingToInGame = this.waitingPlayers.stream()
                .filter(player -> newInGamePlayers.contains(player.getName()))
                .collect(Collectors.toList());
        List<Player> inGameToWaiting = this.inGamePlayers.stream()
                .filter(player -> !newInGamePlayers.contains(player.getName()))
                .collect(Collectors.toList());

        this.inGamePlayers.removeAll(inGameToWaiting);
        this.inGamePlayers.addAll(waitingToInGame);
        this.waitingPlayers.removeAll(waitingToInGame);
        this.waitingPlayers.addAll(inGameToWaiting);

        this.inGamePlayers.sort(Comparator.comparingInt(player -> newInGamePlayers.indexOf(player.getName())));
    }
}

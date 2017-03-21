"use strict";

module.exports = class Game {
    constructor(players, waitingPlayers, records, id) {
        this._players = players;
        this._waitingPlayers = waitingPlayers;
        this._records = records;
        this._id = id;
    }

    get records() {
        return this._records;
    }

    get players() {
        return this._players;
    }

    set players(players) {
        this._players = players;
    }

    get waitingPlayers() {
        return this._waitingPlayers;
    }

    set waitingPlayers(waitingPlayers) {
        this._waitingPlayers = waitingPlayers;
    }

    get allPlayers() {
        return this._players.concat(this._waitingPlayers);
    }

    get winner() {
        if (this._records.length === 0) {
            return null;
        }
        return this._players.find((currentPlayer) => {
            return currentPlayer.rank == 1;
        }).name;
    }

    get id() {
        return this._id.toString();
    }

    getPlayer(name) {
        let player = this._players.find((currentPlayer) => {
            return name == currentPlayer.name;
        }, this);
        if (player === undefined) {
            player = this._waitingPlayers.find((currentPlayer) => {
                return name == currentPlayer.name;
            }, this);
        }
        return player;
    }

    addPlayer(player) {
        this._players.push(player);
    }

    addRecord(round) {
        this._records.push(round);
    }

    getRemainCards(player, index) {
        let recordOfPlayer = this._records[index].entities[player];
        return recordOfPlayer == undefined ? -1 : recordOfPlayer.remain;
    }

    getScore(player, index) {
        return this._records[index].entities[player].score;
    }
};
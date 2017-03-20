"use strict";

import Game from "assets/GanDengYan/Game"

module.exports = class GameController {
    constructor(http) {
        this.$http = http;
    }

    gameOver(game, success) {
        if (game.winner !== null) {
            let winnerMessage = game.winner + " wins!";
            let historyItem = {
                date: new Date().toDateString(),
                game: game
            };

            this.$http.get('party/webapi/GanDengYan/StartGame').then(response => {
                let data = response.body;
                let newGame = new Game(data.inGamePlayers, data.waitingPlayers, data.records, data.id);
                success(winnerMessage, historyItem, newGame);
            }, GameController.printError);
        }
    }

    addNewPlayer(playerName, playerSetter) {
        let retriever = new PlayerRetriever(playerSetter);
        this.$http.get('party/webapi/GanDengYan/AddPlayer/' + playerName).then(
            retriever.retrievePlayersFromResponse.bind(retriever),
            GameController.printError);
    }

    playerChanged(inGamePlayers, playerSetter) {
        let inGamePlayerNames = inGamePlayers.map(player => player.name);
        let retriever = new PlayerRetriever(playerSetter);
        this.$http.post('party/webapi/GanDengYan/ChangePlayers', inGamePlayerNames).then(
            retriever.retrievePlayersFromResponse.bind(retriever),
            GameController.printError);
    }

    static printError(response) {
        console.log("error: " + response);
    }

    record(data, recorded) {
        this.$http.post('party/webapi/GanDengYan/AddRecord/' + data.bang, data.record).then(
            response => {
                let data = response.body;
                recorded(data.record, data.inGamePlayers, data.waitingPlayers);
            }, GameController.printError
        );
    }

    getCurrentGame(gameReceived) {
        this.$http.get("/party/webapi/GanDengYan/CurrentGame").then(
            response => {
                let data = response.data;
                gameReceived(new Game(data.inGamePlayers, data.waitingPlayers, data.records, data.id));
            }, GameController.printError);
    }
};

class PlayerRetriever {
    constructor(playerSetter) {
        this._playerSetter = playerSetter;
    }

    retrievePlayersFromResponse(response) {
        let data = response.body;
        this._playerSetter(data.inGamePlayers, data.waitingPlayers);
    }
}
<template>
    <div id="wrapper">
        <h2>干瞪眼</h2>
        <el-tabs type="card" :activeName="activeTabName">
            <el-tab-pane v-for="game in games" label="Current" :key="game.id" :name="game.id">
                <el-row>
                    <el-col :span="6">
                        <OperationPanel :game="game" @GameOver="gameOver" @addNewPlayer="addNewPlayer"
                                        @PlayerChanged="playerChanged"></OperationPanel>
                    </el-col>
                    <el-col :span="18">
                        <RecordTable :game="game" ref="currentGame" key="table"></RecordTable>
                    </el-col>
                </el-row>
            </el-tab-pane>
            <el-tab-pane v-for="(historyGame, index) in history" :label="historyGame.date" :name="historyGame.game.id"
                         :key="historyGame.game.id"
                         closable>
                <RecordTable :game="historyGame.game"></RecordTable>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import RecordTable from "pages/GanDengYan/RecordTable"
    import OperationPanel from "pages/GanDengYan/OperationPanel"
    import Vue from "vue"

    class Player {
        constructor(name, winCount, winCountFirst, winCountLast, score, scoreFirst, scoreLast, rank) {
            this.name = name;
            this.winCount = winCount;
            this.winCountFirst = winCountFirst;
            this.winCountLast = winCountLast;
            this.score = score;
            this.scoreFirst = scoreFirst;
            this.scoreLast = scoreLast;
            this.rank = rank;
        }
    }

    class Game {
        constructor(players, waitingPlayers, records, id) {
            this._players = players;
            this._waitingPlayers = waitingPlayers;
            this._records = records;
            this._id = id;
            console.log(id);
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

        record(record) {
            // TODO communicate with backend
            console.log(record);
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
    }

    export default {
        data() {
            return {
                game: null,
                history: [],
                activeTabName: "0",
                games: []
            }
        },
        methods: {
            gameOver: function () {
                if (this.game.winner !== null) {
                    this.$message({
                        message: this.game.winner + " wins!",
                        type: "success"
                    });
                    this.history.push({
                        date: new Date().toDateString(),
                        game: this.game
                    });
                    this.games.pop();

                    this.$http.get('party/webapi/GanDengYan/StartGame').then(response => {
                        let data = response.body;
                        console.log(data);
                        this.game = new Game(data.inGamePlayers, data.waitingPlayers, data.records, data.id);
                        this.games.unshift(this.game);
                        this.activeTabName = this.game.id;
                    }, this.printError);
                }
            },
            addNewPlayer(playerName) {
                this.$http.get('party/webapi/GanDengYan/AddPlayer/' + playerName).then(
                    this.retrievePlayersFromResponse,
                    this.printError);
            },
            playerChanged() {
                let inGamePlayers = this.game.players.map(player => player.name);
                this.$http.post('party/webapi/GanDengYan/ChangePlayers', inGamePlayers).then(
                    this.retrievePlayersFromResponse,
                    this.printError);
            },
            retrievePlayersFromResponse(response) {
                let data = response.body;
                this.game.players = data.inGamePlayers;
                this.game.waitingPlayers = data.waitingPlayers;
            },
            printError(response) {
                console.log("error: " + response);
            }
        },
        components: {
            OperationPanel,
            RecordTable
        },
        mounted() {
            this.$http.get("/party/webapi/GanDengYan/CurrentGame").then(
                response => {
                    let data = response.data;
                    this.game = new Game(data.inGamePlayers, data.waitingPlayers, data.records, data.id);
                    this.games.push(this.game);
                    this.activeTabName = this.game.id;
                }, this.printError);
        }
    }
</script>

<style scope>
    #wrapper {
        padding-top: 20px;
    }
</style>
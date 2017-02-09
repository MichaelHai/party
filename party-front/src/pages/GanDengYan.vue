<template>
    <div id="wrapper">
        <h2>干瞪眼</h2>
        <el-tabs type="card" :activeName="activeTabName">
            <el-tab-pane v-for="game in games" label="Current" :key="game.id" :name="game.id">
                <el-row>
                    <el-col :span="6">
                        <OperationPanel :game="game" @GameOver="gameOver"></OperationPanel>
                    </el-col>
                    <el-col :span="18">
                        <RecordTable :game="game" ref="currentGame" key="table"></RecordTable>
                    </el-col>
                </el-row>
            </el-tab-pane>
            <el-tab-pane v-for="(historyGame, index) in history" :label="historyGame.date" :name="historyGame.game.id" :key="historyGame.game.id"
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
        constructor(players, waitingPlayers, records) {
            this._players = players;
            this._waitingPlayers = waitingPlayers;
            this._records = records;
            this._id = new Date().getTime();
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

        addNewPlayer(playerName) {
            this.addPlayer(new Player(playerName, 0, false, false, 0, false, false, 4));
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

        get waitingPlayers() {
            return this._waitingPlayers;
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
                game: new Game([], [], []),
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
                        this.game = new Game(data.inGamePlayers, data.waitingPlayers, data.records);
                        this.games.unshift(this.game);
                        this.activeTabName = this.game.id;
                    }, response => {
                        console.log("error: "&response);
                    });
                }
            }
        },
        components: {
            OperationPanel,
            RecordTable
        },
        mounted() {
            this.game.addPlayer(new Player("wh", 1, true, false, 10, true, false, 1));
            this.game.addPlayer(new Player("cxy", 0, false, true, 0, false, true, 2));
            this.game.addPlayer(new Player("zxc", 0, false, false, 0, false, true, 3));
            this.game.addRecord({
                wh: {
                    remain: 0,
                    score: 1
                },
                cxy: {
                    remain: 1,
                    score: -1
                },
                bang: 0
            });
            this.game.addRecord({
                wh: {
                    remain: 22,
                    score: 1
                },
                cxy: {
                    remain: 5,
                    score: -10
                },
                zxc: {
                    remain: 1,
                    score: -2
                },
                bang: 1
            });
            this.game.addRecord({
                wh: {
                    remain: 1,
                    score: -4
                },
                cxy: {
                    remain: 0,
                    score: -8
                },
                zxc: {
                    remain: 1,
                    score: -4
                },
                bang: 2
            });
            this.game.addRecord({
                wh: {
                    remain: 1,
                    score: -8
                },
                cxy: {
                    remain: 1,
                    score: -8
                },
                zxc: {
                    remain: 0,
                    score: 16
                },
                bang: 3
            });
            this.game.addRecord({
                wh: {
                    remain: 1,
                    score: -16
                },
                cxy: {
                    remain: 0,
                    score: 32
                },
                zxc: {
                    remain: 1,
                    score: -16
                },
                bang: 4
            });
            this.game.addRecord({
                wh: {
                    remain: 0,
                    score: 352
                },
                cxy: {
                    remain: 1,
                    score: -32
                },
                zxc: {
                    remain: 5,
                    score: -320
                },
                bang: 5
            });

            this.games.push(this.game);
            this.activeTabName = this.game.id;
        }
    }
</script>

<style scope>
    #wrapper {
        padding-top: 20px;
    }
</style>
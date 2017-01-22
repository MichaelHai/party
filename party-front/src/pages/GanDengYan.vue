<template>
    <div id="wrapper">
        <h2>干瞪眼</h2>
        <el-tabs type="card" :activeName="activeTabName" @tab-click="tabClicked" @tab-remove="tabRemoved">
            <el-tab-pane label="Current" name="0">
                <el-row>
                    <el-col :span="6">
                        <OperationPanel :game="game"></OperationPanel>
                    </el-col>
                    <el-col :span="18">
                        <RecordTable :game="game" ref="recordTable"></RecordTable>
                    </el-col>
                </el-row>
            </el-tab-pane>
            <el-tab-pane v-for="(historyGame, index) in history" :label="historyGame.date" :name="'' + (index+1)"
                         closable>
                {{ historyGame.date }}
            </el-tab-pane>
            <el-tab-pane label="+" name="Add">
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
        constructor() {
            this._players = [];
            this._records = [];
            this._waitingPlayers = [];
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
    }

    export default {
        data() {
            return {
                game: new Game(),
                history: [],
                activeTabName: "0",
                tabCount: 0
            }
        },
        methods: {
            tabClicked: function (tab) {
                if (tab.name == "Add") {
                    this.addHistory();
                    this.activeTabName = '' + this.history.length;
                } else {
                    this.activeTabName = tab.name;
                }
            },
            tabRemoved: function() {
                this.tabCount--;
                this.activeTabName =  '' + this.tabCount;
            },
            addHistory: function() {
                this.history.push({
                    date: "2016-01-14"
                });
                this.tabCount++;
            }
        },
        watch: {
            activeTabName: function(val) {
                if (val == "Add") {
                    this.activeTabName =  '' + this.tabCount;
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
        }
    }
</script>

<style scope>
    #wrapper {
        padding-top: 20px;
    }
</style>
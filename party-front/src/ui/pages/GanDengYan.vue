<template>
    <div id="wrapper">
        <h2>干瞪眼</h2>
        <el-tabs type="card" :activeName="activeTabName">
            <el-tab-pane v-for="game in games" label="Current" :key="game.id" :name="game.id">
                <el-row>
                    <el-col :span="6">
                        <OperationPanel :game="game" @gameOver="gameOver" @addNewPlayer="addNewPlayer"
                                        @playerChanged="playerChanged" @record="record"></OperationPanel>
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
    import Game from "assets/GanDengYan/Game"
    import GanDengYanController from "assets/GanDengYan/GanDengYanController"

    export default {
        data() {
            return {
                game: null,
                history: [],
                activeTabName: "0",
                games: [],
                ganDengYanController: new GanDengYanController(this.$http)
            }
        },
        methods: {
            gameOver() {
                this.ganDengYanController.gameOver(this.game,
                    (message, historyItem, newGame) => {
                        this.$message({
                            message: message,
                            type: "success"
                        });

                        this.history.push(historyItem);
                        this.games.pop();
                        this.setGame(newGame);
                    }
                );
            },
            addNewPlayer(playerName) {
                this.ganDengYanController.addNewPlayer(playerName, this.setPlayers);
            },
            playerChanged() {
                this.ganDengYanController.playerChanged(this.game.players, this.setPlayers);
            },
            record(data) {
                this.ganDengYanController.record(data,
                    (record, inGamePlayers, waitingPlayers) => {
                        this.game.records.push(record);
                        this.setPlayers(inGamePlayers, waitingPlayers);
                    }
                );
            },
            setPlayers(inGamePlayers, waitingPlayers) {
                this.game.players = inGamePlayers;
                this.game.waitingPlayers = waitingPlayers;
            },
            setGame(game) {
                this.game = game;
                this.games.push(this.game);
                this.activeTabName = this.game.id;
            }
        },
        components: {
            OperationPanel,
            RecordTable
        },
        mounted() {
            this.ganDengYanController.getCurrentGame(this.setGame);
        }
    }
</script>

<style scope>
    #wrapper {
        padding-top: 20px;
    }
</style>
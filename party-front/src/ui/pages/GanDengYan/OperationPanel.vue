<template>
    <div>
        <el-form>
            <el-radio-group v-model="winner" @change="winnerChanged">
                <template v-for="(player, index) in game.players">
                    <el-form-item>
                        <el-row>
                            <el-col :span="6">
                                <el-radio class="name" :label="player.name" text-color="#32CD32"></el-radio>
                            </el-col>
                            <el-col :span="18">
                                <el-rate v-model="remain[index]" :disabled="disabled[index]"></el-rate>
                            </el-col>
                        </el-row>
                    </el-form-item>
                </template>
            </el-radio-group>
            <el-form-item v-if="game.players.length > 0" label="Bang!">
                <el-input-number v-model="bang" :min="0" size="small"></el-input-number>
            </el-form-item>
            <span>
                <el-button @click="record" type="info" size="small">Record</el-button>
                <el-button @click="gameOver" type="danger" size="small">Game Over</el-button>
                <el-button @click="playerSettingVisible = true" type="primary" icon="setting" size="small"></el-button>
            </span>
        </el-form>
        <el-dialog title="Change Players" v-model="playerSettingVisible" size="tiny" @close="playerChanged">
            <el-row>
                <el-col :span="12">
                    <h2 class="listTitle">In Game Players</h2>
                    <draggable :list="game.players" class="dragArea" :options="{group:'people'}" @change="initData">
                        <el-card v-for="player in game.players" :key="player.name" class="playerCard">
                            {{ player.name }}
                        </el-card>
                    </draggable>
                </el-col>
                <el-col :span="12">
                    <h2 class="listTitle">Waiting Players</h2>
                    <draggable :list="game.waitingPlayers" class="dragArea" :options="{group:'people'}">
                        <el-card v-for="player in game.waitingPlayers" :key="player.name" class="playerCard">
                            {{ player.name }}
                        </el-card>
                    </draggable>
                </el-col>
            </el-row>
            <el-input v-model="nameToAdd" placeholder="Add to game" v-on:keyup.enter.native="addPlayer"></el-input>
        </el-dialog>
    </div>
</template>

<script>
    import Draggable from "vuedraggable"
    import OperationPanelController from "assets/GanDengyan/OperationPanelController"

    export default {
        props: ["game"],
        data() {
            return {
                remain: new Array(this.game.players.length).fill(0),
                winner: null,
                disabled: new Array(this.game.players.length).fill(false),
                bang: 0,
                playerSettingVisible: false,
                nameToAdd: null,
                controller: new OperationPanelController()
            }
        },
        components: {
            Draggable
        },
        methods: {
            winnerChanged: function () {
                let players = this.game.players;
                let winnerIndex = players.findIndex((player) => {
                    return player.name == this.winner;
                }, this);
                this.remain[winnerIndex] = 0;
                this.disabled = new Array(this.game.players.length).fill(false);
                this.disabled[winnerIndex] = true;
            },
            record: function () {
                this.controller.record(this.game, this.remain, this.winner,
                    record => {
                        this.$emit("record", {
                            record: record,
                            bang: this.bang
                        });
                        this.initData();
                    },
                    failMessage => {
                        this.$message.error(failMessage);
                    }
                );
            },
            addPlayer: function () {
                if (this.nameToAdd !== null) {
                    this.$emit("addNewPlayer", this.nameToAdd);
                    this.nameToAdd = null;
                }
            },
            initData: function () {
                this.remain = new Array(this.game.players.length).fill(0);
                this.winner = null;
                this.disabled = new Array(this.game.players.length).fill(false);
                this.bang = 0;
            },
            gameOver: function () {
                this.$emit('gameOver');
            },
            playerChanged: function() {
                this.$emit("playerChanged");
            }
        }
    }
</script>

<style>
    .el-rate {
        padding-top: 10px;
        margin-left: 5px;
    }

    .el-form-item .el-col {
        height: 30px;
    }

    .el-form-item__label {
        font-size: 20px;
    }

    .el-input-number__decrease, .el-input-number__increase {
        top: 4px;
    }

    .el-rate {
        display: flex;
    }

    .listTitle {
        margin: auto;
        text-align: center;
    }

    .playerCard {
        width: 100px;
        margin: 10px auto;
    }

    .playerCard > div {
        margin: auto;
        padding: 10px;
        text-align: center;
    }

    .dragArea {
        min-height: 10px;
        width: 100%;
    }
</style>
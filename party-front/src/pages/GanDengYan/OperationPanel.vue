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
            <el-form-item label="Bang!">
                <el-input-number v-model="bang" :min="0" size="small"></el-input-number>
            </el-form-item>
            <el-button @click="record" type="info">Record</el-button>
            <el-button @click="addPlayer">Add player</el-button>
        </el-form>
    </div>
</template>

<script>
    export default {
        props: ["game"],
        data() {
            return {
                remain: [],
                winner: null,
                disabled: [],
                bang: 0
            }
        },
        methods: {
            addPlayer: function () {
                this.$prompt("Player's name:", "Add Player", {
                    confirmButtonText: "Confirm",
                    cancelButtonText: "Cancel"
                }).then(({value}) => {
                    this.game.addNewPlayer(value);
                }).catch();
            },
            winnerChanged: function () {
                let players = this.game.players;
                let winnerIndex = players.findIndex((player) => {
                    return player.name == this.winner;
                }, this);
                this.remain[winnerIndex] = 0;
                this.disabled = [];
                this.disabled[winnerIndex] = true;
            },
            record: function () {
                if (this.winner == null) {
                    this.$message.error("Please choose a winner!");
                    return;
                }

                let record = {};
                let empty = [];
                this.game.players.findIndex((player, index) => {
                    if (this.remain[index] === 0 && player.name != this.winner) {
                        empty.push(player.name);
                    }
                    record[player.name] = this.remain[index];
                });
                if (empty.length !== 0) {
                    this.$message.error("Please fill in the status for " + empty + ".");
                    return;
                }
                record.bang = this.bang;
                console.log(record);

                this.remain = [];
                this.winner = null;
                this.disabled = [];
                this.bang = 0;
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
</style>
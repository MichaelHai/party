<template id="record_table_template">
    <el-table
            :data="game.records"
            style="width: 100%"
            border
    >
        <el-table-column
                fixed
                prop="bang"
                label="Bang!"
                align="center"
        >
            <template scope="scope">
                <div :class="'bangLevel-' + ((scope.row.bang <= 5) ? scope.row.bang : 5)">{{ scope.row.bang }}
                </div>
            </template>
        </el-table-column>
        <el-table-column
                v-for="player in game.allPlayers"
                :prop="player.name"
                :label="player.name"
                align="center"
        >
            <el-table-column
                    :prop="player.name"
                    align="center"
                    :render-header="winCountHeaderRenderer"
            >
                <template scope="scope">
                    <div :class="getRemainingCardIndicator(scope.column.property, scope.$index)">
                        {{ getRemain(scope.column.property, scope.$index) }}
                    </div>
                </template>
            </el-table-column>
            <el-table-column
                    :prop="player.name"
                    align="center"
                    :render-header="scoreHeaderRenderer"
            >
                <template scope="scope">
                    <div :class="getRemainingCardIndicator(scope.column.property, scope.$index)">
                        {{ getScore(scope.column.property, scope.$index) }}
                    </div>
                </template>
            </el-table-column>
        </el-table-column>
    </el-table>
</template>

<script>

    export default {
        props: ["game"],
        methods: {
            winCountHeaderRenderer: function (h, data) {
                let player = this.game.getPlayer(data.column.property);
                if (player === undefined) {
                    player = {
                        winCountFirst: false,
                        winCountLast: false,
                        winCount: 0
                    };
                }
                return h(
                    "div",
                    {
                        'class': {
                            first: player.winCountFirst,
                            last: player.winCountLast
                        }
                    },
                    [player.winCount]
                )
            },
            scoreHeaderRenderer: function (h, data) {
                let player = this.game.getPlayer(data.column.property);
                if (player === undefined) {
                    player = {
                        scoreFirst: false,
                        scoreLast: false,
                        score: 0
                    };
                }
                return h(
                    "div",
                    {
                        'class': {
                            first: player.scoreFirst,
                            last: player.scoreLast
                        }
                    },
                    [player.score]
                )
            },
            getRemainingCardIndicator: function (player, index) {
                let remain = this.game.getRemainCards(player, index);
                return remain === 0 ? 'empty_hand' : (remain === 5 ? 'full_hand' : '');
            },
            getRemain: function (player, index) {
                let remain = this.game.getRemainCards(player, index);
                return remain === -1 ? '/' : remain;
            },
            getScore: function (player, index) {
                return this.game.getRemainCards(player, index) === -1 ? '/' : this.game.getScore(player, index);
            }
        }
    }
</script>

<style>
    thead div.first, tbody td .empty_hand {
        color: limegreen;
        font-weight: bolder;
    }

    thead div.last, tbody td .full_hand {
        color: #ff4444;
        font-weight: bolder;
    }

    tbody td .bangLevel-1 {
        color: #ff4444;
    }

    tbody td .bangLevel-2 {
        color: #ff4444;
    }

    tbody td .bangLevel-3 {
        color: #ff4444;
        font-weight: bold;
    }

    tbody td .bangLevel-4 {
        color: #ff4444;
        font-weight: bold;
    }

    tbody td .bangLevel-5 {
        color: #ff4444;
        font-weight: bolder;
    }

    .waiting, .waiting div {
        color: darkgray !important;
        background-color: darkgray !important;
    }

    .hide {
        display: none;
    }
</style>
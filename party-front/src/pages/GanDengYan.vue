<template>
    <div id="wrapper">
        <el-table
                :data="records"
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
                    <div :class="'bangLevel-' + ((scope.row.bang <= 5) ? scope.row.bang : 5)">{{ scope.row.bang }}</div>
                </template>
            </el-table-column>
            <el-table-column
                    v-for="player in players"
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
                        <div :class="scope.row[scope.column.property+'_remain'] === 0 ? 'empty_hand' : (scope.row[scope.column.property+'_remain'] === 5 ? 'full_hand' : '')">
                            {{ scope.row[scope.column.property+'_remain'] == -1 ? '/' : scope.row[scope.column.property+'_remain'] }}
                        </div>
                    </template>
                </el-table-column>
                <el-table-column
                        :prop="player.name"
                        align="center"
                        :render-header="scoreHeaderRenderer"
                >
                    <template scope="scope">
                        <div :class="scope.row[scope.column.property+'_remain'] === 0 ? 'empty_hand' : (scope.row[scope.column.property+'_remain'] === 5 ? 'full_hand' : '')">
                            {{ scope.row[scope.column.property+'_remain'] == -1 ? '/' : scope.row[scope.column.property+'_score'] }}
                        </div>
                    </template>
                </el-table-column>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    class Player {
        constructor(name, winCount, winCountFirst, winCountLast, score, scoreFirst, scoreLast) {
            this.name = name;
            this.winCount = winCount;
            this.winCountFirst = winCountFirst;
            this.winCountLast = winCountLast;
            this.score = score;
            this.scoreFirst = scoreFirst;
            this.scoreLast = scoreLast;
        }
    }

    export default {
        data() {
            return {
                players: [
                    new Player("wh", 1, true, false, 10, true, false),
                    new Player("cxy", 0, false, true, 0, false, true)
                ],
                records: []
            }
        },
        methods: {
            winCountHeaderRenderer: function (h, data) {
                let player = this.players[data.$index / 2];
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
                let player = this.players[parseInt(data.$index / 2)];
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
            }
        },
        mounted() {
            this.players.push(new Player("zxc", 0, false, false, 0, false, true));
            this.records.push({
                wh_remain: 0,
                wh_score: 1,
                cxy_remain: 1,
                cxy_score: -1,
                zxc_remain: -1,
                zxc_score: -1,
                bang: 0
            });
            this.records.push({
                wh_remain: 22,
                wh_score: 1,
                cxy_remain: 5,
                cxy_score: -20,
                zxc_remain: 1,
                zxc_score: -2,
                bang: 1
            });
            this.records.push({
                wh_remain: 1,
                wh_score: -4,
                cxy_remain: 0,
                cxy_score: 8,
                zxc_remain: 1,
                zxc_score: -4,
                bang: 2
            });
            this.records.push({
                wh_remain: 1,
                wh_score: -8,
                cxy_remain: 1,
                cxy_score: -8,
                zxc_remain: 0,
                zxc_score: 16,
                bang: 3
            });
            this.records.push({
                wh_remain: 1,
                wh_score: -16,
                cxy_remain: 0,
                cxy_score: 32,
                zxc_remain: 1,
                zxc_score: -16,
                bang: 4
            });
            this.records.push({
                wh_remain: 0,
                wh_score: 352,
                cxy_remain: 1,
                cxy_score: -32,
                zxc_remain: 5,
                zxc_score: -320,
                bang: 5
            });
        }
    }
</script>

<style scope>
    #wrapper {
        padding-top: 20px;
    }

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
</style>
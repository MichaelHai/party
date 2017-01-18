<template>
    <div id="wrapper">
        <el-table
                :data="records"
                style="width: 100%">
            <el-table-column
                    v-for="name in players"
                    v-bind:prop="name"
                    v-bind:label="name"
                    align="center"
            >
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                players: ["wh", "cxy"],
                records: []
            }
        },
        mounted() {
            this.players.push("zxc");
            this.records.push({
               wh: 1,
                cxy: 1,
                zxc: 0
            });
        }
    }
</script>

<style scope>
    #wrapper {
        padding-top: 20px;
    }
</style>
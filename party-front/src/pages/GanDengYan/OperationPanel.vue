<template>
    <div>
        <el-button @click="addPlayer">Add player</el-button>
    </div>
</template>

<script>
    export default {
        props: ["game"],
        methods: {
            addPlayer: function () {
                this.$prompt("Player's name:", "Add Player", {
                    confirmButtonText: "Confirm",
                    cancelButtonText: "Cancel"
                }).then(({value}) => {
                    this.game.addNewPlayer(value);
                });
            }
        },
    }
</script>
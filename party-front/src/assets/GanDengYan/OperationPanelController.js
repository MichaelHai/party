"use strict";

module.exports = class OperationPanelController {
    record(game, remain, winner, success, fail) {
        if (winner == null) {
            fail("Please choose a winner!");
            return;
        }

        let record = {};
        let empty = [];
        game.players.findIndex((player, index) => {
            if (remain[index] === 0 && player.name != winner) {
                empty.push(player.name);
            }
            record[player.name] = remain[index];
        });
        if (empty.length !== 0) {
            fail("Please fill in the status for " + empty + ".");
            return;
        }

        success(record);
    }
};
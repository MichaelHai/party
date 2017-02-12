package cn.michaelwang.party.service;

import cn.michaelwang.party.domain.Game;
import cn.michaelwang.party.domain.Player;
import cn.michaelwang.party.domain.Record;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RecordCalculator implements IRecordCalculator {
    @Override
    public Record calculate(Map<String, Integer> rawCards, int bang) {
        Record record = new Record();

        String winner = null;
        int winnerScore = 0;
        for (Map.Entry<String, Integer> entry : rawCards.entrySet()) {
            String player = entry.getKey();
            Integer numberOfCards = entry.getValue();
            if (numberOfCards == 0) {
                winner = player;
            } else {
                int scoreLost = calculateLostScore(numberOfCards, bang);
                record.addRecord(player, numberOfCards, scoreLost);
                winnerScore += -scoreLost;
            }
        }
        record.addRecord(winner, 0, winnerScore);
        record.setBang(bang);

        return record;
    }

    @Override
    public void updateGame(Game game, Record record) {
        game.addRecord(record);
        for (Player player: game.getInGamePlayers()) {
            int score = record.getScore(player.getName());
            player.addScore(score);
        }

        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(game.getInGamePlayers());
        allPlayers.addAll(game.getWaitingPlayers());
        updateRank(allPlayers);
        updateFirstAndLast(allPlayers);
    }

    private void updateRank(List<Player> allPlayers) {
        allPlayers.sort((player1, player2) -> {
            if (player1.getScore() == player2.getScore()) {
                if (player1.getWinCount() == player2.getWinCount()) {
                    return player1.getName().compareTo(player2.getName());
                } else {
                    return player2.getWinCount() - player1.getWinCount();
                }
            } else {
                return player2.getScore() - player1.getScore();
            }
        });
        for (int i = 0; i < allPlayers.size(); i++) {
            allPlayers.get(i).setRank(i+1);
        }
    }

    private void updateFirstAndLast(List<Player> allPlayers) {
        int scoreMax = Integer.MIN_VALUE;
        int scoreMin = Integer.MAX_VALUE;
        int winCountMax = Integer.MIN_VALUE;
        int winCountMin = Integer.MAX_VALUE;

        for (Player player: allPlayers) {
            int score = player.getScore();
            if (scoreMax < score) {
                scoreMax = score;
            }
            if (scoreMin > score) {
                scoreMin = score;
            }

            int winCount = player.getWinCount();
            if (winCountMax < winCount) {
                winCountMax = winCount;
            }
            if (winCountMin > winCount) {
                winCountMin = winCount;
            }
        }

        for (Player player: allPlayers) {
            player.setScoreFirst(player.getScore() == scoreMax);
            player.setScoreLast(player.getScore() == scoreMin);
            player.setWinCountFirst(player.getWinCount() == winCountMax);
            player.setWinCountLast(player.getWinCount() == winCountMin);
        }
    }

    private int calculateLostScore(int numberOfCards, int bang) {
        return (numberOfCards == 5 ? -10 : -numberOfCards) * (int)Math.pow(2, bang);
    }
}

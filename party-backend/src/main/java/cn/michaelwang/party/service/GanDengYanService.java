package cn.michaelwang.party.service;

import cn.michaelwang.party.domain.Game;
import cn.michaelwang.party.domain.Player;
import cn.michaelwang.party.domain.Record;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GanDengYanService implements IGanDengYanService {
    private Game game = new Game();
    private ContinuousWinCounter continuousWinCounter = new ContinuousWinCounter();

    private IRecordCalculator recordCalculator;

    public GanDengYanService(IRecordCalculator recordCalculator) {
        this.recordCalculator = recordCalculator;
    }

    @Override
    public Map<String, List<Player>> addPlayer(String playerName) {
        Player newPlayer = new Player(playerName);
        game.addInGamePlayer(newPlayer);

        return getPlayersMap();
    }

    @Override
    public Game startGame() {
        game = new Game();
        return game;
    }

    @Override
    public Map<String, List<Player>> changePlayers(List<String> inGamePlayers) {
        game.changeInGamePlayers(inGamePlayers);

        return getPlayersMap();
    }

    @Override
    public Game getCurrentGame() {
        return this.game;
    }

    @Override
    public Record addRecord(Map<String, Integer> rawCards, int bang) {
        Record record = recordCalculator.calculate(rawCards, bang, continuousWinCounter);
        recordCalculator.updateGame(game, record);

        return record;
    }

    private Map<String, List<Player>> getPlayersMap() {
        Map<String, List<Player>> result = new HashMap<>();

        result.put("inGamePlayers", game.getInGamePlayers());
        result.put("waitingPlayers", game.getWaitingPlayers());
        return result;
    }
}

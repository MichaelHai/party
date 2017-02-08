package cn.michaelwang.party.service;

import cn.michaelwang.party.domain.Game;
import cn.michaelwang.party.domain.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GanDengYanService implements IGanDengYanService {
    private Game game;

    @Override
    public Map<String, List<Player>> addPlayer(String playerName) {
        return null;
    }

    @Override
    public Game startGame() {
        game = new Game();
        return game;
    }
}

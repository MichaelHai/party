package cn.michaelwang.party.service;

import cn.michaelwang.party.domain.Game;
import cn.michaelwang.party.domain.Player;

import java.util.List;
import java.util.Map;

public interface IGanDengYanService {
    Map<String, List<Player>> addPlayer(String playerName);

    Game startGame();

    Map<String, List<Player>> changePlayers(List<String> inGamePlayers);
}

package cn.michaelwang.party.controller;

import cn.michaelwang.party.domain.Game;
import cn.michaelwang.party.domain.Player;
import cn.michaelwang.party.domain.Record;
import cn.michaelwang.party.service.IGanDengYanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/GanDengYan")
public class GanDengYanController {
    private final IGanDengYanService service;

    @Autowired
    public GanDengYanController(IGanDengYanService service) {
        this.service = service;
    }

    @GetMapping("/AddPlayer/{playerName}")
    public Map<String, List<Player>> addPlayer(@PathVariable String playerName) {
        return service.addPlayer(playerName);
    }

    @GetMapping("/StartGame")
    public Game startGame() {
        return service.startGame();
    }

    @PostMapping("/ChangePlayers")
    public Map<String, List<Player>> changePlayers(@RequestBody List<String> inGamePlayers) {
        return service.changePlayers(inGamePlayers);
    }

    @GetMapping("/CurrentGame")
    public Game currentGame() {
        return service.getCurrentGame();
    }

    @PostMapping("/AddRecord/{bang}")
    public RecordResult addRecord(@RequestBody Map<String, Integer> rawCards, @PathVariable int bang) {
        Record record = service.addRecord(rawCards, bang);
        List<Player> inGamePlayers = service.getCurrentGame().getInGamePlayers();
        List<Player> waitingPlayers = service.getCurrentGame().getWaitingPlayers();

        return new RecordResult(record, inGamePlayers, waitingPlayers);
    }
}

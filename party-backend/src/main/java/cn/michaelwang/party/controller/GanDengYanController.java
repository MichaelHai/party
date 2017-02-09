package cn.michaelwang.party.controller;

import cn.michaelwang.party.domain.Game;
import cn.michaelwang.party.domain.Player;
import cn.michaelwang.party.service.IGanDengYanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

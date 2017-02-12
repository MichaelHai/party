package cn.michaelwang.party.controller;

import cn.michaelwang.party.domain.Game;
import cn.michaelwang.party.domain.Player;
import cn.michaelwang.party.service.IGanDengYanService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class GanDengYanControllerTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private GanDengYanController controller;

    @Mock
    private IGanDengYanService service;

    @Before
    public void setup() {
        controller = new GanDengYanController(service);
    }

    @Test
    public void testStartGame() {
        Game game = mock(Game.class);
        when(service.startGame()).thenReturn(game);

        Game result = controller.startGame();
        assertSame(game, result);
    }

    @Test
    public void testAddPlayers() {
        Map<String, List<Player>> players = new HashMap<>();

        when(service.addPlayer("playerName")).thenReturn(players);

        Map<String, List<Player>> playersResult = controller.addPlayer("playerName");
        assertSame(players, playersResult);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testChangePlayers() {
        List<String> inGamePlayers = mock(List.class);
        Map<String, List<Player>> currentPlayers = mock(Map.class);
        when(service.changePlayers(inGamePlayers)).thenReturn(currentPlayers);

        Map<String, List<Player>> result = controller.changePlayers(inGamePlayers);
        assertSame(result, currentPlayers);
    }

    @Test
    public void testCurrentGame() {
        Game game = mock(Game.class);
        when(service.getCurrentGame()).thenReturn(game);

        Game result = controller.currentGame();
        assertSame(game, result);
    }
}

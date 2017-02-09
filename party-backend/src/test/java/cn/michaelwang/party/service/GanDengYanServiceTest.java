package cn.michaelwang.party.service;

import cn.michaelwang.party.domain.Game;
import cn.michaelwang.party.domain.Player;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GanDengYanServiceTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private GanDengYanService service;

    @Before
    public void setup() {
        service = new GanDengYanService();
    }

    @Test
    public void testStartGame() {
        Game game = service.startGame();
        assertNotNull(game);
        assertEquals(0, game.getInGamePlayers().size());
        assertEquals(0, game.getWaitingPlayers().size());
        assertEquals(0, game.getRecords().size());
    }

    @Test
    public void testAddPlayer() {
        Game game = service.startGame();
        Player inGamePlayer = mock(Player.class);
        game.getInGamePlayers().add(inGamePlayer);
        Player waitingPlayer = mock(Player.class);
        game.getWaitingPlayers().add(waitingPlayer);

        Map<String, List<Player>> result = service.addPlayer("wh");

        assertNotNull(result);
        assertEquals(2, result.size());

        List<Player> inGamePlayers = result.get("inGamePlayers");
        assertNotNull(inGamePlayers);
        assertEquals(2, inGamePlayers.size());
        assertEquals(inGamePlayer, inGamePlayers.get(0));
        Player newPlayer = inGamePlayers.get(1);
        assertEquals("wh", newPlayer.getName());
        assertEquals(0, newPlayer.getWinCount());
        assertFalse(newPlayer.getWinCountFirst());
        assertFalse(newPlayer.getWinCountLast());
        assertEquals(0, newPlayer.getScore());
        assertFalse(newPlayer.getScoreFirst());
        assertFalse(newPlayer.getScoreLast());

        List<Player> waitingPlayers = result.get("waitingPlayers");
        assertNotNull(waitingPlayer);
        assertEquals(1, waitingPlayers.size());
        assertEquals(waitingPlayer, waitingPlayers.get(0));
    }
}

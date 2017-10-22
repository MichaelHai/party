package cn.michaelwang.party.service;

import cn.michaelwang.party.domain.Game;
import cn.michaelwang.party.domain.Player;
import cn.michaelwang.party.domain.Record;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GanDengYanServiceTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private GanDengYanService service;

    @Mock
    private IRecordCalculator recordCalculator;

    @Before
    public void setup() {
        service = new GanDengYanService(recordCalculator);
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
        game.addInGamePlayer(inGamePlayer);
        Player waitingPlayer = mock(Player.class);
        game.addWaitingPlayer(waitingPlayer);

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

    @Test
    public void testChangePlayers() {
        Game game = service.startGame();
        Player player1 = mock(Player.class, "player1");
        when(player1.getName()).thenReturn("player1");
        when(player1.getScore()).thenReturn(-20);
        game.addInGamePlayer(player1);
        Player player2 = mock(Player.class, "player2");
        when(player2.getName()).thenReturn("player2");
        when(player2.getScore()).thenReturn(15);
        game.addInGamePlayer(player2);
        Player player3 = mock(Player.class, "player3");
        when(player3.getName()).thenReturn("player3");
        when(player3.getScore()).thenReturn(5);
        game.addWaitingPlayer(player3);
        Player player4 = mock(Player.class, "player4");
        when(player4.getName()).thenReturn("player4");
        game.addWaitingPlayer(player4);

        List<String> newInGamePlayers = Arrays.asList("player2", "player3", "player1");
        Map<String, List<Player>> result = service.changePlayers(newInGamePlayers);

        assertNotNull(result);
        assertEquals(2, result.size());

        List<Player> inGamePlayers = result.get("inGamePlayers");
        assertNotNull(inGamePlayers);
        assertEquals(3, inGamePlayers.size());
        assertSame(player2, inGamePlayers.get(0));
        assertSame(player3, inGamePlayers.get(1));
        assertSame(player1, inGamePlayers.get(2));
        List<Player> waitingPlayers = result.get("waitingPlayers");
        assertNotNull(waitingPlayers);
        assertEquals(1, waitingPlayers.size());
        assertSame(player4, waitingPlayers.get(0));
    }

    @Test
    public void testCurrentGame() {
        Game game = service.startGame();

        Game result = service.getCurrentGame();

        assertSame(game, result);
    }

    @Test
    public void testAddRecord() {
        Game game = service.startGame();
        game.addInGamePlayer(new Player("player1"));
        game.addInGamePlayer(new Player("player2"));
        game.addInGamePlayer(new Player("player3"));

        @SuppressWarnings("unchecked") Map<String, Integer> rawCards = mock(Map.class);
        Record record = mock(Record.class);
        when(recordCalculator.calculate(eq(rawCards), eq(4), any())).thenReturn(record);
        when(record.getScore("player1")).thenReturn(-32);
        when(record.getScore("player2")).thenReturn(-160);
        when(record.getScore("player3")).thenReturn(192);

        Record result = service.addRecord(rawCards, 4);

        assertSame(record, result);
        verify(recordCalculator).updateGame(game, record);
    }
}

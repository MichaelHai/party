package cn.michaelwang.party.service;

import cn.michaelwang.party.domain.Game;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

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
        service.addPlayer("wh");
    }
}

package cn.michaelwang.party.controller;

import cn.michaelwang.party.domain.Player;
import cn.michaelwang.party.service.IGanDengYanService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

public class TestGanDengYanController {
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
    public void testAddPlayers() {
        Map<String, List<Player>> players = new HashMap<>();

        when(service.addPlayer("playerName")).thenReturn(players);

        Map<String, List<Player>> playersResult = controller.addPlayer("playerName");
        assertSame(players, playersResult);
    }
}
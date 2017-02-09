package cn.michaelwang.party;

import cn.michaelwang.party.configuration.PartyConfiguration;
import cn.michaelwang.party.domain.Game;
import cn.michaelwang.party.domain.Player;
import cn.michaelwang.party.service.IGanDengYanService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PartyConfiguration.class)
@WebAppConfiguration()
public class GanDengYanIntegrationTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGameStart() throws Exception {
        mockMvc.perform(get("/GanDengYan/StartGame"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.records", hasSize(0)))
                .andExpect(jsonPath("$.inGamePlayers", hasSize(0)))
                .andExpect(jsonPath("$.waitingPlayers", hasSize(0)));
    }

    @Test
    public void testAddPlayer() throws Exception {
        IGanDengYanService service = wac.getBean(IGanDengYanService.class);
        Game game = service.startGame();
        game.getInGamePlayers().add(new Player("inGamePlayer"));
        game.getWaitingPlayers().add(new Player("waitingPlayer"));

        mockMvc.perform(get("/GanDengYan/AddPlayer/wh"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inGamePlayers", hasSize(2)))
                .andExpect(jsonPath("$.inGamePlayers[0].name", equalTo("inGamePlayer")))
                .andExpect(jsonPath("$.inGamePlayers[1].name", equalTo("wh")))
                .andExpect(jsonPath("$.inGamePlayers[1].winCount", equalTo(0)))
                .andExpect(jsonPath("$.inGamePlayers[1].winCount", equalTo(0)))
                .andExpect(jsonPath("$.inGamePlayers[1].winCountFirst", equalTo(false)))
                .andExpect(jsonPath("$.inGamePlayers[1].winCountLast", equalTo(false)))
                .andExpect(jsonPath("$.inGamePlayers[1].score", equalTo(0)))
                .andExpect(jsonPath("$.inGamePlayers[1].scoreFirst", equalTo(false)))
                .andExpect(jsonPath("$.inGamePlayers[1].scoreLast", equalTo(false)))
                .andExpect(jsonPath("$.waitingPlayers", hasSize(1)))
                .andExpect(jsonPath("$.waitingPlayers[0].name", equalTo("waitingPlayer")));
    }
}

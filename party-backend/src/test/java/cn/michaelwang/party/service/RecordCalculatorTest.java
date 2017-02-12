package cn.michaelwang.party.service;

import cn.michaelwang.party.domain.Game;
import cn.michaelwang.party.domain.Player;
import cn.michaelwang.party.domain.Record;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class RecordCalculatorTest {
    private RecordCalculator recordCalculator;

    @Before
    public void setup() {
        recordCalculator = new RecordCalculator();
    }

    @Test
    public void testWithoutBangAndFullCards() {
        Map<String, Integer> rawRecord = new HashMap<>();
        rawRecord.put("player1", 0);
        rawRecord.put("player2", 2);
        rawRecord.put("player3", 1);

        Record record = recordCalculator.calculate(rawRecord, 0);
        assertNotNull(record);
        Map<String, Record.RecordEntity> recordEntities = record.getEntities();
        assertNotNull(recordEntities);
        Record.RecordEntity player1Record = recordEntities.get("player1");
        assertNotNull(player1Record);
        assertEquals(0, player1Record.getRemain());
        assertEquals(3, player1Record.getScore());
        Record.RecordEntity player2Record = recordEntities.get("player2");
        assertNotNull(player1Record);
        assertEquals(2, player2Record.getRemain());
        assertEquals(-2, player2Record.getScore());
        Record.RecordEntity player3Record = recordEntities.get("player3");
        assertNotNull(player1Record);
        assertEquals(1, player3Record.getRemain());
        assertEquals(-1, player3Record.getScore());
        assertEquals(0, record.getBang());
    }

    @Test
    public void testWithoutBangButFullCards() {
        Map<String, Integer> rawRecord = new HashMap<>();
        rawRecord.put("player1", 5);
        rawRecord.put("player2", 0);
        rawRecord.put("player3", 1);

        Record record = recordCalculator.calculate(rawRecord, 0);
        assertNotNull(record);
        Map<String, Record.RecordEntity> recordEntities = record.getEntities();
        assertNotNull(recordEntities);
        Record.RecordEntity player1Record = recordEntities.get("player1");
        assertNotNull(player1Record);
        assertEquals(5, player1Record.getRemain());
        assertEquals(-10, player1Record.getScore());
        Record.RecordEntity player2Record = recordEntities.get("player2");
        assertNotNull(player1Record);
        assertEquals(0, player2Record.getRemain());
        assertEquals(11, player2Record.getScore());
        Record.RecordEntity player3Record = recordEntities.get("player3");
        assertNotNull(player1Record);
        assertEquals(1, player3Record.getRemain());
        assertEquals(-1, player3Record.getScore());
        assertEquals(0, record.getBang());
    }

    @Test
    public void testWith1BangButWithoutFullCards() {
        Map<String, Integer> rawRecord = new HashMap<>();
        rawRecord.put("player1", 2);
        rawRecord.put("player2", 4);
        rawRecord.put("player3", 0);

        Record record = recordCalculator.calculate(rawRecord, 1);
        assertNotNull(record);
        Map<String, Record.RecordEntity> recordEntities = record.getEntities();
        assertNotNull(recordEntities);
        Record.RecordEntity player1Record = recordEntities.get("player1");
        assertNotNull(player1Record);
        assertEquals(2, player1Record.getRemain());
        assertEquals(-4, player1Record.getScore());
        Record.RecordEntity player2Record = recordEntities.get("player2");
        assertNotNull(player1Record);
        assertEquals(4, player2Record.getRemain());
        assertEquals(-8, player2Record.getScore());
        Record.RecordEntity player3Record = recordEntities.get("player3");
        assertNotNull(player1Record);
        assertEquals(0, player3Record.getRemain());
        assertEquals(12, player3Record.getScore());
        assertEquals(1, record.getBang());
    }

    @Test
    public void testWith3BangsButWithoutFullCards() {
        Map<String, Integer> rawRecord = new HashMap<>();
        rawRecord.put("player1", 4);
        rawRecord.put("player2", 0);
        rawRecord.put("player3", 1);

        Record record = recordCalculator.calculate(rawRecord, 3);
        assertNotNull(record);
        Map<String, Record.RecordEntity> recordEntities = record.getEntities();
        assertNotNull(recordEntities);
        Record.RecordEntity player1Record = recordEntities.get("player1");
        assertNotNull(player1Record);
        assertEquals(4, player1Record.getRemain());
        assertEquals(-32, player1Record.getScore());
        Record.RecordEntity player2Record = recordEntities.get("player2");
        assertNotNull(player1Record);
        assertEquals(0, player2Record.getRemain());
        assertEquals(40, player2Record.getScore());
        Record.RecordEntity player3Record = recordEntities.get("player3");
        assertNotNull(player1Record);
        assertEquals(1, player3Record.getRemain());
        assertEquals(-8, player3Record.getScore());
        assertEquals(3, record.getBang());
    }

    @Test
    public void testWith4BangsAndFullCards() {
        Map<String, Integer> rawRecord = new HashMap<>();
        rawRecord.put("player1", 2);
        rawRecord.put("player2", 5);
        rawRecord.put("player3", 0);

        Record record = recordCalculator.calculate(rawRecord, 4);
        assertNotNull(record);
        Map<String, Record.RecordEntity> recordEntities = record.getEntities();
        assertNotNull(recordEntities);
        Record.RecordEntity player1Record = recordEntities.get("player1");
        assertNotNull(player1Record);
        assertEquals(2, player1Record.getRemain());
        assertEquals(-32, player1Record.getScore());
        Record.RecordEntity player2Record = recordEntities.get("player2");
        assertNotNull(player1Record);
        assertEquals(5, player2Record.getRemain());
        assertEquals(-160, player2Record.getScore());
        Record.RecordEntity player3Record = recordEntities.get("player3");
        assertNotNull(player1Record);
        assertEquals(0, player3Record.getRemain());
        assertEquals(192, player3Record.getScore());
        assertEquals(4, record.getBang());
    }

    @Test
    public void testUpdateGame() {
        Game game = new Game();
        Player player1 = new Player("player1");
        game.addInGamePlayer(player1);
        Player player2 = new Player("player2");
        game.addInGamePlayer(player2);
        Player player3 = new Player("player3");
        game.addInGamePlayer(player3);
        Player player4 = new Player("player4");
        game.addWaitingPlayer(player4);

        player1.setScore(100);
        player1.setWinCount(2);
        player2.setScore(-30);
        player2.setWinCount(1);
        player3.setScore(50);
        player3.setWinCount(5);
        player4.setScore(-120);
        player4.setWinCount(0);

        Record record = new Record();
        record.addRecord("player1", 1, -4);
        record.addRecord("player2", 0, 12);
        record.addRecord("player3", 2, -8);
        record.setBang(2);

        recordCalculator.updateGame(game, record);

        assertEquals(2, player1.getWinCount());
        assertEquals(false, player1.getWinCountFirst());
        assertEquals(false, player1.getWinCountLast());
        assertEquals(96, player1.getScore());
        assertEquals(true, player1.getScoreFirst());
        assertEquals(false, player1.getScoreLast());
        assertEquals(1, player1.getRank());

        assertEquals(2, player2.getWinCount());
        assertEquals(false, player2.getWinCountFirst());
        assertEquals(false, player2.getWinCountLast());
        assertEquals(-18, player2.getScore());
        assertEquals(false, player2.getScoreFirst());
        assertEquals(false, player2.getScoreLast());
        assertEquals(3, player2.getRank());

        assertEquals(5, player3.getWinCount());
        assertEquals(true, player3.getWinCountFirst());
        assertEquals(false, player3.getWinCountLast());
        assertEquals(42, player3.getScore());
        assertEquals(false, player3.getScoreFirst());
        assertEquals(false, player3.getScoreLast());
        assertEquals(2, player3.getRank());

        assertEquals(0, player4.getWinCount());
        assertEquals(false, player4.getWinCountFirst());
        assertEquals(true, player4.getWinCountLast());
        assertEquals(-120, player4.getScore());
        assertEquals(false, player4.getScoreFirst());
        assertEquals(true, player4.getScoreLast());
        assertEquals(4, player4.getRank());
    }
}

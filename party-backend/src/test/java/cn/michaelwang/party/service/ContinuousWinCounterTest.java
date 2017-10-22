package cn.michaelwang.party.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContinuousWinCounterTest {
    private ContinuousWinCounter continuousWinCounter;

    @Before
    public void setup() {
        this.continuousWinCounter = new ContinuousWinCounter();
        continuousWinCounter.nextWinner("player1");
    }

    @Test
    public void testNewWinnerWillResetTheCounter() {
        continuousWinCounter.nextWinner("player2");
        assertEquals(1, continuousWinCounter.getCount());
    }

    @Test
    public void testOldWinnerWillIncreaseTheCounter() {
        continuousWinCounter.nextWinner("player1");
        assertEquals(2, continuousWinCounter.getCount());
    }
}

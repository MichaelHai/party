package cn.michaelwang.party.service;

import cn.michaelwang.party.domain.Game;
import cn.michaelwang.party.domain.Record;

import java.util.Map;

public interface IRecordCalculator {
    Record calculate(Map<String, Integer> rawCards, int bang);

    void updateGame(Game game, Record record);
}

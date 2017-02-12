package cn.michaelwang.party.domain;

import java.util.HashMap;
import java.util.Map;

public class Record {
    private Map<String, RecordEntity> entities = new HashMap<>();
    private int bang = 0;

    public void addRecord(String player, int remain, int score) {
        this.entities.put(player, new RecordEntity(remain, score));
    }

    public void setBang(int bang) {
        this.bang = bang;
    }

    public Map<String,RecordEntity> getEntities() {
        return entities;
    }

    public int getBang() {
        return bang;
    }

    public int getScore(String player) {
        return this.entities.get(player).getScore();
    }

    public static class RecordEntity {
        private int remain;
        private int score;

        RecordEntity(int remain, int score) {
            this.remain = remain;
            this.score = score;
        }

        public int getRemain() {
            return remain;
        }

        public int getScore() {
            return score;
        }
    }
}

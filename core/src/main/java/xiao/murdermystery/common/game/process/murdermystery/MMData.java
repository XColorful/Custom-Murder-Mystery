package xiao.murdermystery.common.game.process.murdermystery;

import xiao.battleroyale.common.game.AbstractGameManagerData;

public class MMData extends AbstractGameManagerData {

    private static final String DATA_NAME = "MurderMysteryData";

    public MMData() {
        super(DATA_NAME);
    }

    @Override
    public void clear() {
    }

    @Override
    public void startGame() {
        if (locked) return;

        lockData();
    }

    @Override
    public void endGame() {
        if (locked) {
            unlockData();
        }
    }
}

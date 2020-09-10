package events;

import models.CandleStick;

public class HistoricalDataEvent extends Event {

    private CandleStick candleStick;

    public HistoricalDataEvent(CandleStick candleStick) {
        this.candleStick = candleStick;
    }

    public CandleStick getCandleStick() {
        return candleStick;
    }

    public void setCandleStick(CandleStick candleStick) {
        this.candleStick = candleStick;
    }
}

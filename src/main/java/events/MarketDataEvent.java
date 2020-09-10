package events;

import models.Tick;

public class MarketDataEvent extends Event {

    private Tick tick;

    public MarketDataEvent(Tick tick) {
        this.tick = tick;
    }

    public Tick getTick() {
        return tick;
    }

    public void setTick(Tick tick) {
        this.tick = tick;
    }
}

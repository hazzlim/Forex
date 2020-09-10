package signals;

import models.Tick;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Signal {

    private static final AtomicInteger iDCount = new AtomicInteger(0);
    private final int ID;
    private String instrument;

    public Signal(String instrument) {
        ID = iDCount.incrementAndGet();
        this.instrument = instrument;
    }

    public abstract void updatePrice(Tick tick);

    public int getID() {
        return ID;
    }

    public String getInstrument() {
        return instrument;
    }

}

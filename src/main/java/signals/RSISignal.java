package signals;

import enums.TimeInterval;
import models.Tick;

public class RSISignal extends Signal {
    public RSISignal(String instrument, TimeInterval timeInterval) {
        super(instrument);
    }

    @Override
    public void updatePrice(Tick tick) {
        System.out.println("RSISignal - Price update: " + tick);
    }
}

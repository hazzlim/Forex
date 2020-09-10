package strategies;

import enums.TimeInterval;
import events.SignalEvent;
import signals.RSISignal;
import signals.Signal;

import java.util.ArrayList;
import java.util.List;

public class RSIStrategy implements Strategy {

    List<Signal> signals = new ArrayList<Signal>();

    public RSIStrategy() {
        signals.add(new RSISignal("EUR/USD", TimeInterval.SEC10));
    }

    @Override
    public void processSignal(SignalEvent signalEvent) {
        System.out.println(signalEvent);
    }

    @Override
    public List<Signal> getSignals() {
        return signals;
    }
}

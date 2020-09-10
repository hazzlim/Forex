package events;

import signals.Signal;

import java.util.List;

public class RegisterSignalEvent extends Event {

    private List<Signal> signals;

    public RegisterSignalEvent(List<Signal> signals) {
        super();
        this.signals = signals;
    }

    public List<Signal> getSignals() {
        return signals;
    }
}

package strategies;

import events.SignalEvent;
import signals.Signal;

import java.util.List;

public interface Strategy {

    void processSignal(SignalEvent signalEvent);

    List<Signal> getSignals();

}

package channels;

import events.Event;
import events.RegisterSignalEvent;
import events.SignalEvent;
import strategies.RSIStrategy;

import java.util.concurrent.LinkedBlockingDeque;

public class StrategyChannel extends EventChannel {

    private LinkedBlockingDeque<Event> eventQueue;
    private RSIStrategy strategy;

    public StrategyChannel(LinkedBlockingDeque<Event> eventQueue) {
        super();
        this.eventQueue = eventQueue;

        strategy = new RSIStrategy();

        RegisterSignalEvent registerSignalEvent = new RegisterSignalEvent(strategy.getSignals());
        eventQueue.add(registerSignalEvent);
    }

    @Override
    protected void processEvent(Event event) {
        SignalEvent signalEvent = (SignalEvent) event;
        strategy.processSignal(signalEvent);
    }
}

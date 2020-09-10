package channels;

import events.*;
import models.Tick;
import signals.Signal;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class SignalChannel extends EventChannel {

    private LinkedBlockingDeque<Event> eventQueue;
    private HashMap<Integer, Signal> signals = new HashMap<Integer, Signal>();

    public SignalChannel(LinkedBlockingDeque<Event> eventQueue) {
        super();
        this.eventQueue = eventQueue;
    }

    @Override
    protected void processEvent(Event event) {

        if(event instanceof MarketDataEvent) {
            MarketDataEvent marketDataEvent = (MarketDataEvent) event;
            Tick tick = marketDataEvent.getTick();

            for(Signal signal : signals.values()) {
                if(signal.getInstrument().equals(tick.getInstrument())) {
                    signal.updatePrice(tick);
                }
            }
        }
        else if(event instanceof RegisterSignalEvent) {
            RegisterSignalEvent signalEvent = (RegisterSignalEvent) event;

            for(Signal signal : signalEvent.getSignals()) {
                if(!signals.containsKey(signal.getID())) {
                    signals.put(signal.getID(), signal);
                }
            }
        }
        else {
            System.out.println("Unknown event: " + event);
        }
    }

}

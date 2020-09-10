package channels;

import events.Event;

import java.util.concurrent.LinkedBlockingDeque;

public abstract class EventChannel extends Thread implements MessageChannelInterface<Event> {

    private LinkedBlockingDeque<Event> eventQueue = new LinkedBlockingDeque<Event>();

    protected abstract void processEvent(Event event);

    @Override
    public void run() {
        while(true) {
            try {
                Event event = eventQueue.take();
                processEvent(event);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dispatch(Event event) {
        eventQueue.add(event);
    }
}

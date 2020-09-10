package routers;

import channels.EventChannel;
import channels.MessageChannelInterface;
import events.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventRouter implements MessageRouterInterface<Event> {

    private Map<Class<? extends Event>, List<EventChannel>> handlers;

    public EventRouter() {
        handlers = new HashMap<Class<? extends Event>, List<EventChannel>>();
    }

    @Override
    public void registerChannel(Class<? extends Event> contentType, MessageChannelInterface<? extends Event> channel) {
        EventChannel eventChannel = (EventChannel) channel;

        if(!eventChannel.isAlive()) {   // start eventChannel thread if not yet alive
            eventChannel.start();
        }

        // check if Event class exists in handlers map, and if not add it
        if(handlers.get(contentType) == null) {
            handlers.put(contentType, new ArrayList<EventChannel>());
        }

        // add the eventChannel to the list of handlers for the given Event class
        handlers.get(contentType).add(eventChannel);
    }

    public void dispatch(Event content) {
        for(EventChannel eventChannel : handlers.get(content.getType())) {
            eventChannel.dispatch(content);
        }
    }

}

package routers;

import channels.MessageChannelInterface;
import events.MessageInterface;

public interface MessageRouterInterface<E extends MessageInterface> {
    public void registerChannel(Class<? extends E> contentType, MessageChannelInterface<? extends E> channel);
    public abstract void dispatch(E content);
}

package channels;

import events.MessageInterface;

public interface MessageChannelInterface<E extends MessageInterface> {
    public void dispatch(E message);
}

package events;

public class Event implements MessageInterface {
    public Class<? extends MessageInterface> getType() {
        return getClass();
    }
}

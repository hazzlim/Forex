package events;

public interface MessageInterface {

    public Class<? extends MessageInterface> getType();
}

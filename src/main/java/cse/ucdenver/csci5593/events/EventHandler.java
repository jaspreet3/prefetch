package cse.ucdenver.csci5593.events;

/**
 * Created by max on 3/14/16.
 */
public interface EventHandler {
    void handleEvent(EventType type, Object... input);
}

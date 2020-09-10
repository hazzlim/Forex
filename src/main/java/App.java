import events.Event;
import managers.DataManager;
import routers.EventRouter;
import org.apache.log4j.Logger;
import programs.FXCMAPIProgram;
import util.ConfigurationHelper;
import java.util.Calendar;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Class containing main method to run and test program.
 */
public class App {

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        ConfigurationHelper config = new ConfigurationHelper();
        Logger log = Logger.getLogger(App.class);

        DataManager dataManager = new DataManager();

        LinkedBlockingDeque<Event> eventQueue = new LinkedBlockingDeque<Event>();
        EventRouter router = new EventRouter();

        FXCMAPIProgram program = new FXCMAPIProgram(config, dataManager, router, eventQueue);

        Calendar instance = Calendar.getInstance();
        instance.roll(Calendar.HOUR, -1);
        program.subscribeToRates("EUR/USD");

        while(true) {
            try {
                router.dispatch(eventQueue.take());
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

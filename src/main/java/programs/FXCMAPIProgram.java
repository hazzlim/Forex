package programs;

import brokerageclients.fxcm.FXCMAPIBrokerageClient;
import events.Event;
import managers.DataManager;
import routers.EventRouter;
import util.ConfigurationHelper;

import java.util.concurrent.LinkedBlockingDeque;

public class FXCMAPIProgram extends Program {

    public FXCMAPIProgram(ConfigurationHelper config, DataManager dataManager, EventRouter router, LinkedBlockingDeque<Event> eventQueue) {
        super(config, dataManager, router, eventQueue, new FXCMAPIBrokerageClient(config, eventQueue));
    }

}

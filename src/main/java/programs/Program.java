package programs;

import brokerageclients.BrokerageClient;
import channels.StrategyChannel;
import enums.TimeInterval;
import events.*;
import managers.DataManager;
import routers.EventRouter;
import channels.HistoryChannel;
import channels.SignalChannel;
import util.ConfigurationHelper;

import java.util.Calendar;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class Program {

    ConfigurationHelper config;
    BrokerageClient client;

    public Program(ConfigurationHelper config, DataManager dataManager, EventRouter router, LinkedBlockingDeque<Event> eventQueue, BrokerageClient client) {
        this.config = config;
        this.client = client;

        SignalChannel signalChannel = new SignalChannel(eventQueue);
        StrategyChannel strategyChannel = new StrategyChannel(eventQueue);
        HistoryChannel historyChannel = new HistoryChannel(dataManager);


        router.registerChannel(MarketDataEvent.class, signalChannel);
        router.registerChannel(RegisterSignalEvent.class, signalChannel);

        router.registerChannel(SignalEvent.class, strategyChannel);

        router.registerChannel(HistoricalDataEvent.class, historyChannel);
    }

    public void requestHistory(String instrumentName, TimeInterval interval, Calendar startTime) {
        client.requestHistory(instrumentName, interval, startTime);
    }

    public void subscribeToRates(String instrumentName) {
        client.marketDataSubscribe(instrumentName);
    }

    public void unsubscribeToRates(String instrumentName) {
        client.marketDataUnsubscribe(instrumentName);
    }

    public boolean isStreaming() {
        return client.isStreaming();
    }
}

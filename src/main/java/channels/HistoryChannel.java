package channels;

import events.Event;
import events.HistoricalDataEvent;
import managers.DataManager;
import models.CandleStick;

public class HistoryChannel extends EventChannel {

    //private Map<String, LinkedList<CandleStick>> candleMap = new HashMap<String, LinkedList<CandleStick>>();
    private DataManager dataManager;

    public HistoryChannel(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    protected void processEvent(Event event) {
        HistoricalDataEvent historicalDataEvent = (HistoricalDataEvent) event;
        CandleStick candleStick = historicalDataEvent.getCandleStick();

        dataManager.add(candleStick);

        System.out.println("History Channel " + candleStick);
    }

}

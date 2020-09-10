package models;

import enums.TimeInterval;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class FXInstrument {

    private String instrumentName;

    private Map<TimeInterval, Set<CandleStick>> candleMap;

    public FXInstrument(String instrumentName) {

        this.instrumentName = instrumentName;
        candleMap = new HashMap<TimeInterval, Set<CandleStick>>();
    }

    public void addData(CandleStick candleStick) {

        if(candleMap.get(candleStick.getInterval()) == null) {
            candleMap.put(candleStick.getInterval(), new ConcurrentSkipListSet<CandleStick>());
        }
        candleMap.get(candleStick.getInterval()).add(candleStick);
    }

    public Set<CandleStick> getData(TimeInterval timeInterval) {
        return candleMap.get(timeInterval);
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }
}

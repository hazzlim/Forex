package managers;

import enums.TimeInterval;
import models.CandleStick;
import models.FXInstrument;

import java.util.*;

public class DataManager {

    private Map<String, FXInstrument> instrumentsMap = new HashMap<String, FXInstrument>();

    public void add(CandleStick candleStick) {
        if(instrumentsMap.get(candleStick.getInstrument()) == null) {
            instrumentsMap.put(candleStick.getInstrument(), new FXInstrument(candleStick.getInstrument()));
        }
        instrumentsMap.get(candleStick.getInstrument()).addData(candleStick);
    }

    public Set<CandleStick> getData(String instrumentName, TimeInterval timeInterval) {
        if(instrumentsMap.get(instrumentName) != null) {
            return instrumentsMap.get(instrumentName).getData(timeInterval);
        }
        return null;
    }
}

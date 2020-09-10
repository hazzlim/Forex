package models;

import brokerageclients.fxcm.FXCMDataTypeConverter;
import com.fxcm.fix.NotDefinedException;
import com.fxcm.fix.pretrade.MarketDataSnapshot;
import enums.TimeInterval;

import java.text.DecimalFormat;
import java.util.Date;

public class CandleStick implements Comparable<CandleStick> {

    private String instrument;
    private TimeInterval interval;
    private Date timeStamp;
    private double askOpen;
    private double askLow;
    private double askHigh;
    private double askClose;
    private double bidOpen;
    private double bidLow;
    private double bidHigh;
    private double bidClose;
    private int volume;

    private static DecimalFormat decimalFormat = new DecimalFormat("#.00000");

    public CandleStick(MarketDataSnapshot snapshot) throws NotDefinedException {
        this.interval = FXCMDataTypeConverter.convertFXCMToInterval(snapshot.getFXCMTimingInterval());
        this.instrument = snapshot.getInstrument().getSymbol();
        this.timeStamp = snapshot.getTimestamp().toDate();
        this.askOpen = snapshot.getAskOpen();
        this.askLow = snapshot.getAskLow();
        this.askHigh = snapshot.getAskHigh();
        this.askClose = snapshot.getAskClose();
        this.bidOpen = snapshot.getBidOpen();
        this.bidLow = snapshot.getBidLow();
        this.bidHigh = snapshot.getBidHigh();
        this.bidClose = snapshot.getBidClose();
        this.volume = snapshot.getTickVolume();
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getAskOpen() {
        return askOpen;
    }

    public void setAskOpen(double askOpen) {
        this.askOpen = askOpen;
    }

    public double getAskLow() {
        return askLow;
    }

    public void setAskLow(double askLow) {
        this.askLow = askLow;
    }

    public double getAskHigh() {
        return askHigh;
    }

    public void setAskHigh(double askHigh) {
        this.askHigh = askHigh;
    }

    public double getAskClose() {
        return askClose;
    }

    public void setAskClose(double askClose) {
        this.askClose = askClose;
    }

    public double getBidOpen() {
        return bidOpen;
    }

    public void setBidOpen(double bidOpen) {
        this.bidOpen = bidOpen;
    }

    public double getBidLow() {
        return bidLow;
    }

    public void setBidLow(double bidLow) {
        this.bidLow = bidLow;
    }

    public double getBidHigh() {
        return bidHigh;
    }

    public void setBidHigh(double bidHigh) {
        this.bidHigh = bidHigh;
    }

    public double getBidClose() {
        return bidClose;
    }

    public void setBidClose(double bidClose) {
        this.bidClose = bidClose;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public TimeInterval getInterval() {
        return interval;
    }

    public void setInterval(TimeInterval interval) {
        this.interval = interval;
    }

    public String toString() {
        return "SYMBOL: " + instrument + " - " +
                "time: " + timeStamp + " - " +
                "ask open: " + decimalFormat.format(askOpen) + " - " +
                "ask low: " + decimalFormat.format(askLow) + " - " +
                "ask high: " + decimalFormat.format(askHigh) + " - " +
                "ask close: " + decimalFormat.format(askClose) + " - " +
                "bid open: " + decimalFormat.format(bidOpen) + " - " +
                "bid low: " + decimalFormat.format(bidLow) + " - " +
                "bid high: " + decimalFormat.format(bidHigh) + " - " +
                "bid close: " + decimalFormat.format(bidClose);
    }

    @Override
    public boolean equals(Object obj) {
        // check if references same object
        if(obj == this) {
            return true;
        }
        // check if obj is an instance of CandleStick
        if(!(obj instanceof CandleStick)) {
            return false;
        }
        // cast to Candlestick and compare
        CandleStick other = (CandleStick) obj;
        return other.getInstrument().equals(instrument) && other.getTimeStamp().equals(timeStamp);
    }

    @Override
    public int compareTo(CandleStick o) {
        return 0;
    }
}

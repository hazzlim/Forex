package models;

public class Tick {

    private double bid;
    private double ask;
    private String instrument;
    private String time;

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String toString() {
        return instrument + "- " + "Time: " + time +  " - Bid: " + bid + " - Ask: " + ask;
    }


}

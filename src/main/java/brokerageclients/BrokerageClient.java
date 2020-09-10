package brokerageclients;

import enums.TimeInterval;

import java.util.Calendar;

public interface BrokerageClient {
    void requestHistory(String instrumentName, TimeInterval interval, Calendar startTime);
    void marketDataSubscribe(String instrumentName);
    void marketDataUnsubscribe(String instrumentName);
    boolean isStreaming();
}

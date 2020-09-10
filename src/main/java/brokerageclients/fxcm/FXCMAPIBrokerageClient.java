package brokerageclients.fxcm;

import brokerageclients.BrokerageClient;
import com.fxcm.external.api.transport.FXCMLoginProperties;
import com.fxcm.external.api.transport.GatewayFactory;
import com.fxcm.external.api.transport.IGateway;
import com.fxcm.external.api.transport.listeners.IGenericMessageListener;
import com.fxcm.external.api.transport.listeners.IStatusMessageListener;
import com.fxcm.fix.*;
import com.fxcm.fix.pretrade.*;
import com.fxcm.messaging.ISessionStatus;
import com.fxcm.messaging.ITransportable;
import enums.TimeInterval;
import events.Event;
import events.HistoricalDataEvent;
import events.MarketDataEvent;
import models.CandleStick;
import models.Tick;
import util.ConfigurationHelper;
import java.util.Calendar;
import java.util.concurrent.LinkedBlockingDeque;

public class FXCMAPIBrokerageClient implements BrokerageClient, IGenericMessageListener, IStatusMessageListener {

    private IGateway gateway;
    private FXCMLoginProperties loginProperties;
    private LinkedBlockingDeque<Event> eventQueue;
    private MarketDataRequest currentHistoryRequest;
    private UTCTimestamp openTimestamp;
    private boolean receivingData;

    public FXCMAPIBrokerageClient(ConfigurationHelper config, LinkedBlockingDeque<Event> eventQueue) {

        this.eventQueue = eventQueue;
        this.receivingData = false;

        loginProperties = new FXCMLoginProperties(config.getProperty("username"), config.getProperty("password"), config.getProperty("terminal"), config.getProperty("server"));
        gateway = GatewayFactory.createGateway();
        gateway.registerGenericMessageListener(this);
        gateway.registerStatusMessageListener(this);

        // login
        try {
            // login and send TSS request to subscribe to responses from FXCM platform
            gateway.login(loginProperties);
            gateway.requestTradingSessionStatus();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void marketDataSubscribe() {
        // create MarketDataRequest object to be sent
        MarketDataRequest mdr = new MarketDataRequest();
        // send the request
        sendMessage(mdr);
    }

    private void marketDataUnsubscribe() {
        // create MarketDataRequest object to be sent
        MarketDataRequest mdr = new MarketDataRequest();
        // set to unsubscribe (ALL)
        mdr.setSubscriptionRequestType(SubscriptionRequestTypeFactory.UNSUBSCRIBE);
        // send the request
        sendMessage(mdr);
    }

    public void marketDataSubscribe(String instrumentName) {
        // create MarketDataRequest object to be sent
        MarketDataRequest mdr = new MarketDataRequest();
        // set instrument
        Instrument instrument = new Instrument(instrumentName);
        mdr.addRelatedSymbol(instrument);
        // send the request
        sendMessage(mdr);
    }

    public void marketDataUnsubscribe(String instrumentName) {
        // create MarketDataRequest object to be sent
        MarketDataRequest mdr = new MarketDataRequest();
        // set instrument
        Instrument instrument = new Instrument(instrumentName);
        mdr.addRelatedSymbol(instrument);
        // set to unsubscribe (ALL)
        mdr.setSubscriptionRequestType(SubscriptionRequestTypeFactory.UNSUBSCRIBE);
        // send the request
        sendMessage(mdr);
    }

    private MarketDataRequest requestMarketData(ISubscriptionRequestType requestType, IFXCMTimingInterval timingInterval, UTCDate startDate, UTCTimeOnly startTime, Instrument instrument) {
        // create MarketDataRequest object to be sent
        MarketDataRequest mdr = new MarketDataRequest();
        // set request type
        mdr.setSubscriptionRequestType(requestType);
        // set the response format
        mdr.setResponseFormat(IFixDefs.MSGTYPE_FXCMRESPONSE);
        // set timing interval
        mdr.setFXCMTimingInterval(timingInterval);
        // set start Date and Time
        mdr.setFXCMStartDate(startDate);
        mdr.setFXCMStartTime(startTime);
        // set the instrument
        mdr.addRelatedSymbol(instrument);
        // send the request
        return (MarketDataRequest) sendMessage(mdr);
    }

    private ITransportable sendMessage(ITransportable mdr) {
        try {
            gateway.sendMessage(mdr);
            return mdr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // in case of error
        return null;
    }

    @Override
    public void messageArrived(ITransportable message) {

        // decide what to do with message
        try {
            if (message instanceof MarketDataSnapshot) {
                messageArrived((MarketDataSnapshot) message);
            }
            if (message instanceof MarketDataRequestReject) {
                receivingData = false;
                System.out.println(message);    // do nothing for now
            }
            else if (message instanceof TradingSessionStatus)
                System.out.println(message);    // do nothing for now
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void messageArrived(MarketDataSnapshot snapshot) {

        try {
            // check for history request
            if (snapshot.getRequestID() != null && currentHistoryRequest != null && snapshot.getRequestID().equals(currentHistoryRequest.getRequestID())) {

                CandleStick candleStick = new CandleStick(snapshot);
                HistoricalDataEvent historicalDataEvent = new HistoricalDataEvent(candleStick);
                eventQueue.add(historicalDataEvent);

                if(openTimestamp == null) {
                    openTimestamp = snapshot.getOpenTimestamp();
                }

                if(snapshot.getFXCMContinuousFlag() == IFixDefs.FXCMCONTINUOUS_END) {

                    if(!snapshot.getOpenTimestamp().equals(openTimestamp)) {
                        followUpHistoryRequest(snapshot.getInstrument());
                        openTimestamp = null;
                    } else {
                        receivingData = false;
                        currentHistoryRequest = null;
                    }
                }

            } else {
                Tick tick = new Tick();
                tick.setBid(snapshot.getBidClose());
                tick.setAsk(snapshot.getAskClose());
                tick.setInstrument(snapshot.getInstrument().getSymbol());
                tick.setTime(snapshot.getTimestamp().toString());

                MarketDataEvent marketDataEvent = new MarketDataEvent(tick);
                eventQueue.add(marketDataEvent);
            }
        } catch (NotDefinedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(ISessionStatus aStatus) {
        // check the status code
        if (aStatus.getStatusCode() == ISessionStatus.STATUSCODE_ERROR
                || aStatus.getStatusCode() == ISessionStatus.STATUSCODE_DISCONNECTING
                || aStatus.getStatusCode() == ISessionStatus.STATUSCODE_CONNECTING
                || aStatus.getStatusCode() == ISessionStatus.STATUSCODE_CONNECTED
                || aStatus.getStatusCode() == ISessionStatus.STATUSCODE_CRITICAL_ERROR
                || aStatus.getStatusCode() == ISessionStatus.STATUSCODE_EXPIRED
                || aStatus.getStatusCode() == ISessionStatus.STATUSCODE_LOGGINGIN
                || aStatus.getStatusCode() == ISessionStatus.STATUSCODE_LOGGEDIN
                || aStatus.getStatusCode() == ISessionStatus.STATUSCODE_PROCESSING
                || aStatus.getStatusCode() == ISessionStatus.STATUSCODE_DISCONNECTED) {
            // display status message
            System.out.println("\t\t" + aStatus.getStatusMessage());
        }
    }

    @Override
    public void requestHistory(String instrumentName, TimeInterval interval, Calendar startTime) {
        // set up request parameters
        ISubscriptionRequestType requestType = SubscriptionRequestTypeFactory.SNAPSHOT;
        IFXCMTimingInterval intervalFXCM = FXCMDataTypeConverter.convertIntervalToFXCM(interval);
        UTCDate startDateFXCM = new UTCDate(startTime.getTime());
        UTCTimeOnly startTimeFXCM = new UTCTimeOnly(startTime.getTime());
        Instrument instrument = new Instrument(instrumentName);
        // make request
        currentHistoryRequest = requestMarketData(requestType, intervalFXCM, startDateFXCM, startTimeFXCM, instrument);
        // set flag
        receivingData = true;
    }

    @Override
    public boolean isStreaming() {
        return receivingData;
    }

    private void followUpHistoryRequest(Instrument instrument) {
        if((openTimestamp.getTime() - currentHistoryRequest.getFXCMStartDate().getTime()) > currentHistoryRequest.getFXCMTimingInterval().getDuration(new UTCTimestamp()))
        {
            MarketDataRequest mdr = new MarketDataRequest();
            mdr.setSubscriptionRequestType(SubscriptionRequestTypeFactory.SNAPSHOT);
            mdr.setResponseFormat(IFixDefs.MSGTYPE_FXCMRESPONSE);
            mdr.setFXCMTimingInterval(currentHistoryRequest.getFXCMTimingInterval());
            mdr.setFXCMStartDate(currentHistoryRequest.getFXCMStartDate());
            mdr.setFXCMStartTime(currentHistoryRequest.getFXCMStartTime());
            mdr.setFXCMEndDate(new UTCDate(openTimestamp));
            mdr.setFXCMEndTime(new UTCTimeOnly(openTimestamp));
            mdr.addRelatedSymbol(instrument);
            currentHistoryRequest = (MarketDataRequest) sendMessage(mdr);
        } else {
            receivingData = false;
        }
    }
}

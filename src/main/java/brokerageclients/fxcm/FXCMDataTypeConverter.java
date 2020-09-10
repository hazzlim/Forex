package brokerageclients.fxcm;

import com.fxcm.fix.FXCMTimingIntervalFactory;
import com.fxcm.fix.IFXCMTimingInterval;
import enums.TimeInterval;

public class FXCMDataTypeConverter {

    public static IFXCMTimingInterval convertIntervalToFXCM(TimeInterval interval) {

        switch(interval) {
            case DAY1:
                return FXCMTimingIntervalFactory.DAY1;
            case HOUR1:
                return FXCMTimingIntervalFactory.HOUR1;
            case HOUR2:
                return FXCMTimingIntervalFactory.HOUR2;
            case HOUR3:
                return FXCMTimingIntervalFactory.HOUR3;
            case HOUR4:
                return FXCMTimingIntervalFactory.HOUR4;
            case HOUR6:
                return FXCMTimingIntervalFactory.HOUR6;
            case HOUR8:
                return FXCMTimingIntervalFactory.HOUR8;
            case MIN1:
                return FXCMTimingIntervalFactory.MIN1;
            case MIN15:
                return FXCMTimingIntervalFactory.MIN15;
            case MIN30:
                return FXCMTimingIntervalFactory.MIN30;
            case MIN5:
                return FXCMTimingIntervalFactory.MIN5;
            case MONTH1:
                return FXCMTimingIntervalFactory.MONTH1;
            case SEC10:
                return FXCMTimingIntervalFactory.SEC10;
            case TICK:
                return FXCMTimingIntervalFactory.TICK;
            case WEEK1:
                return FXCMTimingIntervalFactory.WEEK1;
            default:
                return null;
        }
    }

    public static TimeInterval convertFXCMToInterval(IFXCMTimingInterval fxcmInterval) {

        if (FXCMTimingIntervalFactory.DAY1.equals(fxcmInterval)) {
            return TimeInterval.DAY1;
        } else if (FXCMTimingIntervalFactory.HOUR1.equals(fxcmInterval)) {
            return TimeInterval.HOUR1;
        } else if (FXCMTimingIntervalFactory.HOUR2.equals(fxcmInterval)) {
            return TimeInterval.HOUR2;
        } else if (FXCMTimingIntervalFactory.HOUR3.equals(fxcmInterval)) {
            return TimeInterval.HOUR3;
        } else if (FXCMTimingIntervalFactory.HOUR4.equals(fxcmInterval)) {
            return TimeInterval.HOUR4;
        } else if (FXCMTimingIntervalFactory.HOUR6.equals(fxcmInterval)) {
            return TimeInterval.HOUR6;
        } else if (FXCMTimingIntervalFactory.HOUR8.equals(fxcmInterval)) {
            return TimeInterval.HOUR8;
        } else if (FXCMTimingIntervalFactory.MIN1.equals(fxcmInterval)) {
            return TimeInterval.MIN1;
        } else if (FXCMTimingIntervalFactory.MIN15.equals(fxcmInterval)) {
            return TimeInterval.MIN15;
        } else if (FXCMTimingIntervalFactory.MIN30.equals(fxcmInterval)) {
            return TimeInterval.MIN30;
        } else if (FXCMTimingIntervalFactory.MIN5.equals(fxcmInterval)) {
            return TimeInterval.MIN5;
        } else if (FXCMTimingIntervalFactory.MONTH1.equals(fxcmInterval)) {
            return TimeInterval.MONTH1;
        } else if (FXCMTimingIntervalFactory.SEC10.equals(fxcmInterval)) {
            return TimeInterval.SEC10;
        } else if (FXCMTimingIntervalFactory.TICK.equals(fxcmInterval)) {
            return TimeInterval.TICK;
        } else if (FXCMTimingIntervalFactory.WEEK1.equals(fxcmInterval)) {
            return TimeInterval.WEEK1;
        }
        return null;
    }
}

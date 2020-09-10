package UI;

import models.CandleStick;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.OHLCDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OHLCChart {

    private static OHLCDataItem[] data;
    private static OHLCDataset dataset;
    private static XYPlot plot;

    public static ChartPanel createChart(String chartName, LinkedList<CandleStick> candleSticks) {
        List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();

        for(CandleStick cs : candleSticks) {
            dataItems.add(new OHLCDataItem(cs.getTimeStamp(), cs.getAskOpen(), cs.getAskHigh(), cs.getAskLow(), cs.getAskClose(), cs.getVolume()));
        }

        data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
        dataset = new DefaultOHLCDataset(chartName, data);

        JFreeChart chart = ChartFactory.createCandlestickChart(chartName, "Time", "Price", dataset, false);
        chart.setBackgroundPaint(Color.WHITE);

        plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        ((NumberAxis) plot.getRangeAxis()).setAutoRangeIncludesZero(false);

        plot.getDomainAxis().setAutoRange(true);

        return new ChartPanel(chart);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.charts;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.ZoomableChart;
import info.monitorenter.gui.chart.axis.AAxis;
import info.monitorenter.gui.chart.axis.AxisLinear;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Robin
 */
public class LinienChart implements Chart {

    private int[] range;
    private ZoomableChart chart;
    private ITrace2D trace;

    public LinienChart() {

        range = new int[2];
//        range[0] = 0;
//        range[1] = 10;

        // Create a chart
        chart = new ZoomableChart();

        //create a new axis - for test by anadi
        AAxis yAxis = new AxisLinear();
        chart.addAxisYRight(yAxis);
        AAxis xAxis = new AxisLinear();
        chart.addAxisXBottom(xAxis);
        // Create ITrace
        trace = new Trace2DSimple();
        chart.addTrace(trace, xAxis, yAxis);
        trace.setColor(Color.RED);
//        trace.setStroke(new BasicStroke(2));
        trace.setColor(Color.GREEN);
        chart.setBackground(Color.cyan);

//        zoomAllButton.addActionListener(new MultiAxisZoomTest.ZoomAllAdapter(chart));
//        trace.setColor(Color.RED);
//        ChartPanel chartPanel = new ChartPanel(chart);
        // add a titlede border:
//        chartPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
//                .createLineBorder(Color.BLACK), "Chart", TitledBorder.CENTER, TitledBorder.CENTER));
    }

    @Override
    public Chart2D generateChart(ArrayList<Double[]> data) {
        if (data != null) {
            for (int i = 0; i <= data.size() - 1; i++) {
                if (data.size() <= (i - 1)) {
                    break;
                }
                trace.addPoint(data.get(i)[0], data.get(i)[1]);
            }
        }

        return chart;
    }

}

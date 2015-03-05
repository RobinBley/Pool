/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.charts;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.chart.views.ChartPanel;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Robin
 */
public class LinienChart implements Chart {

    private int[] range;
    private Chart2D chart;
    private ITrace2D trace;

    public LinienChart() {
        chart = new Chart2D();
        range = new int[2];
        range[0] = 0;
        range[1] = 10;
        // Create an ITrace:
        // Note that dynamic charts need limited amount of values!!!
        // ITrace2D trace = new Trace2DLtd(200);
        trace = new Trace2DSimple();
        // Add the trace to the chart:
        chart.addTrace(trace);
//        trace.setColor(Color.RED);

//        ChartPanel chartPanel = new ChartPanel(chart);
        // add a titlede border:

//        chartPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
//                .createLineBorder(Color.BLACK), "Chart", TitledBorder.CENTER, TitledBorder.CENTER));

    }

    @Override
    public Chart2D generateChart(ArrayList<Double[]> data) {
        if (data != null) {
            for (int i = 0; i <= range[1]; i++) {
                if (data.size() <= (i - 1)) {
                    break;
                }
                trace.addPoint(data.get(i)[0], data.get(i)[1]);
            }
        }

        return chart;
    }

}

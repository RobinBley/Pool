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

    /**
     * Komponenten der Klasse werden initialisiert.
     */
    public LinienChart() {

        range = new int[2];

        //Ein ZoomableChart-Objekt wird erzeugt.
        chart = new ZoomableChart();

        //Axen des Charts werden erzeugt und zugewiesen.
        AAxis yAxis = new AxisLinear();
        chart.addAxisYRight(yAxis);
        AAxis xAxis = new AxisLinear();
        chart.addAxisXBottom(xAxis);

        //Ein Trace2D-Objekt wird erzeugt, welches die Axen zugewiesen bekommt und dem Chart zugewiesen wird.
        trace = new Trace2DSimple();
        chart.addTrace(trace, xAxis, yAxis);
        trace.setColor(Color.RED);

        //Farben des Hintergrunds und des Grafen werden gesetzt.
        trace.setColor(Color.GREEN);
        chart.setBackground(Color.cyan);

    }

    @Override
    public Chart2D generateChart(ArrayList<Double[]> data) {
        //Es wird geprueft ob die uebergebenen Daten ungleich Null sind.
        if (data != null) {
            //Dem Chart werden die uebergebenen Daten als Punkte uebergeben.
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

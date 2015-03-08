/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.charts;

import info.monitorenter.gui.chart.Chart2D;
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

    /**
     * Komponenten der Klasse werden initialisiert.
     */
    @Override
    public Chart2D generateChart(ArrayList<Double[]> data) {
        int[] range = new int[2];

        //Ein ZoomableChart-Objekt wird erzeugt.
        ZoomableChart chart = new ZoomableChart();

        //Axen des Charts werden erzeugt und zugewiesen.
        AAxis yAxis = new AxisLinear();
        chart.addAxisYRight(yAxis);
        AAxis xAxis = new AxisLinear();
        chart.addAxisXBottom(xAxis);

        //Ein Trace2D-Objekt wird erzeugt, welches die Axen zugewiesen bekommt und dem Chart zugewiesen wird.
        Trace2DSimple trace = new Trace2DSimple();
        chart.addTrace(trace, xAxis, yAxis);
        trace.setColor(Color.RED);

        //Farben des Hintergrunds und des Grafen werden gesetzt.
        trace.setColor(Color.GREEN);
        chart.setBackground(Color.cyan);
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

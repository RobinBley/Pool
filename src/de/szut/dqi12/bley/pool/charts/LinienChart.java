/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.charts;

import info.monitorenter.gui.chart.Chart2D;
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
    private Chart2D currentChart;
    private ArrayList<Double[]> currentData;
    private int[] range;

    /**
     * Komponenten der Klasse werden initialisiert.
     */
    @Override
    public Chart2D generateChart(ArrayList<Double[]> data) {
        this.range = new int[2];
        range[0] = 0;
        range[1] = 100;

        //Ein ZoomableChart-Objekt wird erzeugt.
        Chart2D chart = new Chart2D();
        //Axen des Charts werden erzeugt und zugewiesen.
        AAxis yAxis = new AxisLinear();
        AAxis xAxis = new AxisLinear();

        //Ein Trace2D-Objekt wird erzeugt, welches die Axen zugewiesen bekommt und dem Chart zugewiesen wird.
        Trace2DSimple trace = new Trace2DSimple();
        chart.addTrace(trace);
        
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
        this.currentData = data;
        this.currentChart = chart;
        return chart;
    }

    @Override
    public Chart2D setAxis(ArrayList<Double> Data, boolean axis) {
        Trace2DSimple trace = (Trace2DSimple) currentChart.getTraces().last();
        trace.removeAllPoints();
        ArrayList<Double[]> currentDataCopy = (ArrayList<Double[]>) this.currentData.clone();
        this.currentData.clear();
        Double[] element = new Double[2];

        if (axis) {
            for (int i = range[0]; i < range[1]; i++) {
                if (Data.size() <= i + 1) {
                    break;
                }
                trace.addPoint(Data.get(i), (double) currentDataCopy.get(i)[1]);
                element[0] = Data.get(i);
                element[1] = (double) currentDataCopy.get(i)[0];
                this.currentData.add(element.clone());

            }

        } else {
            for (int i = range[0]; i < range[1]; i++) {
                if (Data.size() <= i + 1 ) {
                    break;
                }
                trace.addPoint((double) currentDataCopy.get(i)[0], Data.get(i));
                element[0] = (double) currentDataCopy.get(i)[0];
                element[1] = Data.get(i);
                this.currentData.add(element.clone());

            }

        }

        return this.currentChart;
        
    }

  
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.model;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import java.util.ArrayList;

/**
 *
 * @author Robin
 */
public class Model {

    private ArrayList<Double[]> data;

    public void addValues(ArrayList<Double[]> values) {

    }

    public void setData(ArrayList<Double[]> values) {
        this.data = values;
        Chart2D chart = new Chart2D();
        ITrace2D trace = new Trace2DSimple();
        chart.addTrace(trace);
        for (int i = 0; i < data.size(); i++) {
            trace.addPoint(values.get(i)[0], values.get(i)[1]);
        }
    }

    public Model() {
        data = new ArrayList();

    }

}

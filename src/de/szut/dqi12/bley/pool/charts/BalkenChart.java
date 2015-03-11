/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.charts;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.IPointHighlighter;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.pointhighlighters.PointHighlighterConfigurable;
import info.monitorenter.gui.chart.pointpainters.PointPainterDisc;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyFixedViewport;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.chart.traces.painters.TracePainterVerticalBar;
import info.monitorenter.util.Range;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Robin
 */
public class BalkenChart implements Chart {

    private Chart2D currentChart;
    private ArrayList<Double[]> currentData;
    private int[] range;

    @Override
    public Chart2D generateChart(ArrayList<Double[]> data) {
        //Eine Defaultrange wird angelegt.
        this.range = new int[2];
        range[0] = 0;
        range[1] = 100;
        range = range;

        //Ein neues Chart2D-Objekt wird erzeugt.
        Chart2D chart = new Chart2D();

        //Axen des Charts werden erzeugt und zugewiesen.
        IAxis axisX = chart.getAxisX();
        IAxis axisY = chart.getAxisY();
        axisX.setPaintGrid(true);
        axisY.setPaintGrid(true);
        IAxis.AxisTitle axisTitle = axisY.getAxisTitle();
        axisX.setRangePolicy(new RangePolicyFixedViewport(new Range(range[0], range[1])));

        //Ein Trace2D-Objekt wird erzeugt und dem Chart hinzugeeuegt.
        ITrace2D trace = new Trace2DSimple();
        chart.addTrace(trace);
        trace.setTracePainter(new TracePainterVerticalBar(6, chart));

        //Farben des Grafen und des Hintergrunds werden gesetzt.
        trace.setColor(Color.GREEN);
        chart.setBackground(Color.cyan);

        chart.setToolTipType(Chart2D.ToolTipType.VALUE_SNAP_TO_TRACEPOINTS);
        Set<IPointHighlighter<?>> highlighters = trace.getPointHighlighters();
        highlighters.clear();
        trace.addPointHighlighter(new PointHighlighterConfigurable(new PointPainterDisc(20), true));
        chart.enablePointHighlighting(true);
        //Es wird geprueft ob die uebergebenen Daten ungleich Null sind.
        if (data != null) {
            //Wenn die Range die Groesze der Daten uebertifft, wird die Range auf die Groesze der Daten gesetzt.
            if (range[1] > data.size()) {
                range[1] = data.size();
                axisX.setRangePolicy(new RangePolicyFixedViewport(new Range(range[0], range[1])));

            }
            //Dem Chart werden die uebergebenen Daten als Punkte uebergeben.
            for (int i = range[0]; i < range[1]; i++) {
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
        if (Data.size() < this.currentData.size()){
            range[1] = this.currentData.size();
        }else{
            
            range[1] = Data.size() - 1;
        }

        if (axis) {
            for (int i = range[0]; i < range[1]; i++) {
                if (Data.size() <= i + 1) {
                    element[0] = 0.0;
                } else {
                    element[0] = Data.get(i);

                }
                try{
                    element[1] = (double) currentDataCopy.get(i)[0];
                }catch(Exception e){
                    element[1] = 0.0;

                }
                trace.addPoint(element[0], element[1]);
                this.currentData.add(element.clone());

            }

        } else {
            for (int i = range[0]; i < range[1]; i++) {
                if (Data.size() <= i + 1) {
                    element[1] = 0.0;
                } else {
                    element[1] = Data.get(i);

                }
                try{
                    element[0] = (double) currentDataCopy.get(i)[0];
                }catch(Exception e){
                    element[0] = 0.0;

                }
                trace.addPoint(element[0], element[1]);
                this.currentData.add(element.clone());

            }

            
        }
        
        return this.currentChart;
    }
}

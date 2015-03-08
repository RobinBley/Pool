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
import java.awt.Font;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.UIManager;

/**
 *
 * @author Robin
 */
public class BalkenChart implements Chart {

    /**
     * Komponenten der Klasse werden initialisiert.
     */
    public BalkenChart() {

    }

    @Override
    public Chart2D generateChart(ArrayList<Double[]> data) {
        //Eine Defaultrange wird angelegt.
        int[] range = new int[2];
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
        Font titleFont = UIManager.getDefaults().getFont("Label.font").deriveFont(14f).deriveFont(
                Font.BOLD);
        IAxis.AxisTitle axisTitle = axisY.getAxisTitle();
        axisTitle.setTitleFont(titleFont);
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
            for (double i = range[0]; i < range[1]; i++) {
                trace.addPoint(data.get((int) i)[0], data.get((int) i)[1]);
            }
        }
        return chart;
    }

}

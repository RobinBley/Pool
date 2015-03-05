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
import info.monitorenter.gui.chart.labelformatters.LabelFormatterDate;
import info.monitorenter.gui.chart.pointhighlighters.PointHighlighterConfigurable;
import info.monitorenter.gui.chart.pointpainters.PointPainterDisc;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyFixedViewport;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.chart.traces.painters.TracePainterVerticalBar;
import info.monitorenter.util.Range;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.UIManager;

/**
 *
 * @author Robin
 */
public class BalkenChart implements Chart {

    private int[] range;
    private Chart2D chart;
    private ITrace2D trace;

    public void generateChart(int[] range, String xTitle, String yTitle) {
        this.range = range;
        chart = new Chart2D();

        // Obtain the basic default axes: 
        IAxis axisX = chart.getAxisX();
        IAxis axisY = chart.getAxisY();

        // Feature: Grids:
        chart.setGridColor(Color.LIGHT_GRAY);
        axisX.setPaintGrid(true);
        axisY.setPaintGrid(true);

        // Create an ITrace:
        trace = new Trace2DSimple();
        // Add the trace to the chart:
        chart.addTrace(trace);

        // Feature: trace painters: You are also able to specify multiple ones!
        trace.setTracePainter(new TracePainterVerticalBar(4, chart));

        // Feature: trace color. 
        trace.setColor(Color.ORANGE);

        // Feature: Axis title font. 
        Font titleFont = UIManager.getDefaults().getFont("Label.font").deriveFont(14f).deriveFont(
                Font.BOLD);
        IAxis.AxisTitle axisTitle = axisY.getAxisTitle();
        axisTitle.setTitleFont(titleFont);

        // Feature: axis title.
        axisTitle.setTitle(yTitle);
        // Feature: axis formatter.
        axisY.setFormatter(new LabelFormatterDate(new SimpleDateFormat()));

        // Feature: axis title (again).
//    axisTitle = axisX.getAxisTitle();
        axisTitle.setTitle(xTitle);
//    axisTitle.setTitleFont(titleFont);
        // Feature: range policy for axis. 
        axisX.setRangePolicy(new RangePolicyFixedViewport(new Range(this.range[0], this.range[1])));

        // Feature: turn on tool tips (recommended for use in static mode only): 
        chart.setToolTipType(Chart2D.ToolTipType.VALUE_SNAP_TO_TRACEPOINTS);

        // Feature: turn on highlighting: Two steps enable it on the chart and set a highlighter for the trace: 
        Set<IPointHighlighter<?>> highlighters = trace.getPointHighlighters();
        highlighters.clear();
        trace.addPointHighlighter(new PointHighlighterConfigurable(new PointPainterDisc(20), true));
        chart.enablePointHighlighting(true);
        chart.setBackground(Color.cyan);
    }

    @Override
    public Chart2D generateChart(ArrayList<Double[]> data) {
        for (double i = range[0]; i < range[1]; i++) {
            if (data.size() < i){
                break;
            }
            trace.addPoint(data.get((int) i)[0], data.get((int) i)[1]);

        }
        return chart;
    }

}

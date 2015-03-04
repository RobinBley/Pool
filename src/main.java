
import de.szut.dqi12.bley.pool.gui.Gui;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.IPointHighlighter;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.ZoomableChart;
import info.monitorenter.gui.chart.axis.AAxis;
import info.monitorenter.gui.chart.axis.AxisLinear;
import info.monitorenter.gui.chart.labelformatters.LabelFormatterDate;
import info.monitorenter.gui.chart.pointhighlighters.PointHighlighterConfigurable;
import info.monitorenter.gui.chart.pointpainters.PointPainterDisc;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyFixedViewport;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.chart.traces.painters.TracePainterVerticalBar;
import info.monitorenter.gui.chart.views.ChartPanel;
import info.monitorenter.util.Range;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import vccc.MultiAxisZoomTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Robin
 */
public class main {

    public static void main(String[] args) {
          
    // Make it visible:
        new Gui().setGraph(chart);
    }

}

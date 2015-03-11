/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.charts;

import info.monitorenter.gui.chart.Chart2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Robin
 */
public interface Chart {
    public Chart2D generateChart(ArrayList<Double[]> data);
    
    public Chart2D setAxis(ArrayList<Double> Data, boolean axis);
}

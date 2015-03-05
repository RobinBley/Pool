/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.controller;

import de.szut.dqi12.bley.pool.charts.BalkenChart;
import de.szut.dqi12.bley.pool.charts.Chart;
import de.szut.dqi12.bley.pool.charts.LinienChart;
import de.szut.dqi12.bley.pool.controller.source.DataSource;
import de.szut.dqi12.bley.pool.controller.source.SourceCSV;
import de.szut.dqi12.bley.pool.gui.Gui;
import de.szut.dqi12.bley.pool.properties.PropertyHandler;
import java.util.ArrayList;

/**
 *
 * @author Robin
 */
public class Controller {

    private Gui gui;
    private DataSource source;
    private Chart chart;
    private PropertyHandler property;
    private String selectedItem;
    private Chart balkenChart;
    private Chart linieChart;
    private ArrayList<Double[]> data;
    private static Controller INSTANCE = null;

    public static Controller getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Controller();
        }
        return INSTANCE;
    }

    public Controller() {
        try {
            source = (DataSource) Class.forName("de.szut.dqi12.bley.pool.source." + property.getProperty("dataSource")).newInstance();
        } catch (Exception e) {
            source = new SourceCSV();
        }
        try {
            chart = (Chart) Class.forName("de.szut.dqi12.bley.pool.charts." + property.getProperty("chart")).newInstance();
        } catch (Exception e) {
            chart = new BalkenChart();
        }

        property = new PropertyHandler();
        gui = new Gui();
        selectedItem = (property.getProperty("selectedItem"));

        linieChart = new LinienChart();
        balkenChart = new BalkenChart();
        data = null;

    }

    public void setSelectedItem(String selectedItem) {
        if (selectedItem != null) {
            this.selectedItem = selectedItem;

            //DO SOMETHING
        }
    }

    public void setChart(String path, String chartName) {
        if (path == null && chartName != null) {
            try {
                chart = (Chart) Class.forName("de.szut.dqi12.bley.pool.charts." + chartName).newInstance();
                property.savePropertie("chart", chartName);

            } catch (Exception ex) {
                ex.printStackTrace();
                chart = new BalkenChart();
            }
            gui.setGraph(chart.generateChart(data));

        } else if (chartName != null) {
            try {
                chart = (Chart) Class.forName("de.szut.dqi12.bley.pool.charts." + chartName).newInstance();
                property.savePropertie("chart", chartName);
            } catch (Exception ex) {
                chart = new BalkenChart();
            }
            gui.setGraph(chart.generateChart(source.getData(path)));
        } else if (path != null) {
            data = source.getData(path);
            gui.setGraph(chart.generateChart(data));

        }
    }

    public void connect() {
    }

    public void start() {
    }

}

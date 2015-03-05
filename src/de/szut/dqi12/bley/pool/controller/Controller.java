/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.controller;

import de.szut.dqi12.bley.pool.charts.BalkenChart;
import de.szut.dqi12.bley.pool.charts.Chart;
import de.szut.dqi12.bley.pool.controller.fileoperations.DataSource;
import de.szut.dqi12.bley.pool.controller.fileoperations.SourceCSV;
import de.szut.dqi12.bley.pool.gui.Gui;
import de.szut.dqi12.bley.pool.properties.PropertyHandler;

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

    private static Controller INSTANCE = null;

    public static Controller getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Controller();
        }
        return INSTANCE;
    }

    public Controller() {
        try {
            source = (DataSource) Class.forName(property.getProperty("dataSource")).newInstance();
        } catch (Exception e) {
            source = new SourceCSV();
        }
        try {
            chart = (Chart) Class.forName(property.getProperty("chart")).newInstance();
        } catch (Exception e) {
            chart = new BalkenChart();
        }
        
        gui = new Gui();

    }

    public void setSelectedItem(String selectedItem) {
        if (selectedItem != null) {
            this.selectedItem = selectedItem;
            property.savePropertie("selectedItem", selectedItem);
        }
    }

    public void connect() {
    }
    
    
    
    
    public void start(){
    }

}

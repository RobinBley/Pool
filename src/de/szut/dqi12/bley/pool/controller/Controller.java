/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.controller;

import de.szut.dqi12.bley.pool.charts.BalkenChart;
import de.szut.dqi12.bley.pool.charts.Chart;
import de.szut.dqi12.bley.pool.charts.LinienChart;
import de.szut.dqi12.bley.pool.source.DataSource;
import de.szut.dqi12.bley.pool.database.DatabaseOperator;
import de.szut.dqi12.bley.pool.source.TxtSource;
import de.szut.dqi12.bley.pool.gui.Gui;
import de.szut.dqi12.bley.pool.properties.Charts;
import de.szut.dqi12.bley.pool.properties.PropertyHandler;
import de.szut.dqi12.bley.pool.properties.Sources;
import info.monitorenter.gui.chart.Chart2D;
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
    private Chart balkenChart;
    private Chart linieChart;
    private ArrayList<Double[]> data;
    private static Controller INSTANCE = null;
    private DatabaseOperator dbSource;
    private String path;
    private Chart2D currentGraph;

    /**
     * Singleton Pattern. Diese Funktion Liefert ein Objekt vom Typ Controller
     * zurueck. Es wird ein neues Controller-Objekt erzeugt, fasl noch keins
     * existiert. Diese Funktion sorgt dafuer, dass waehrnd der Laufzeit nur ein
     * Objekt dieser Klasse existiert.
     *
     * @return Eine Instance eines Controller-Objekts.
     */
    public static Controller getInstance() {
        //Wenn INSTANCE = NULL ist, wird ihm ein neues Controller-Objekt zugewiesen. Dieses wird zurueckgegeben.
        if (INSTANCE == null) {
            INSTANCE = new Controller();
        }
        return INSTANCE;
    }

    /**
     * Komponenten der Klasse werden initialisiert.
     */
    public Controller() {
        //Initialisierung der Komponenten
        gui = new Gui();
        linieChart = new LinienChart();
        balkenChart = new BalkenChart();
        dbSource = new DatabaseOperator();
        data = null;
        property = new PropertyHandler();
        //Es wird versucht ein neues DataSource-Objekt zu initialisieren, dessen Klassenname sich in den Properties befindet.
        //Wenn dies nicht gelingt wird ein neues SourceCSV-Objekt initialisiert.
        try {
            source = (DataSource) Class.forName("de.szut.dqi12.bley.pool.source." + property.getProperty("dataSource")).newInstance();
        } catch (Exception e) {
            source = new TxtSource();
        }
        //Es wird versucht ein neues Chart-Objekt zu initialisieren, dessen Klassenname sich in den Properties befindet.
        //Wenn dies nicht gelingt wird ein BalkenChart-Objekt initialisiert.
        try {
            chart = (Chart) Class.forName("de.szut.dqi12.bley.pool.charts." + property.getProperty("chart")).newInstance();
        } catch (Exception e) {
            chart = new BalkenChart();
        }
        try {
            path = property.getProperty("path");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Der Oberflaeche wird ein Graph hinzugefuegt, welcher die Daten der letzten Nutzung der Applikation enthaelt.
        setChart(null, null, null);

    }

    /**
     * Die Tablle, welche sich in der Nutzeroberflaeche befindet wird mit Daten
     * gefuellt.
     *
     * @param selectedItem Ausgewaehlte Tabelle, welche angezeigt werden soll.
     */
    public void setTable(String selectedItem) {
        //Wenn der Tabellenname ungleich null ist, wird der Name der Tabelle in den Properties gespeichert.
        if (selectedItem != null) {
            property.savePropertie("selectedItem", selectedItem);
            //Die Tabelle der Oberflaeche wird gefuellt.
            gui.fillTable(dbSource.getTable(selectedItem));
        } else {
            gui.showHint("Ein Fehler ist aufgetreten.\nBitte ueberpruefen sie ihre Einstellungen");
        }
    }

    /**
     * Es wird ein Graph in die Nutzeroberflaeche geladen.
     *
     * @param path Pfad zur Datenquelle
     * @param chartName Name des Diagrammtyps
     */
    public void setChart(String path, Sources sources, Charts chartName) {
        if (sources != null) {
            try {
                this.source = (DataSource) Class.forName("de.szut.dqi12.bley.pool.source." + sources.name()).newInstance();
                property.savePropertie("dataSource", sources.name());
            } catch (Exception ex) {
                ex.printStackTrace();
                this.source = new TxtSource();
            }
        }
        if (path != null) {
            this.path = path;
            saveProperty("path", path);
        }
        if (chartName != null) {
            try {
                chart = (Chart) Class.forName("de.szut.dqi12.bley.pool.charts." + chartName.name()).newInstance();
                //Der Name des Diagramtyps wird in den Properties gespeichert.
                property.savePropertie("chart", chartName.name());
            } catch (Exception ex) {
                ex.printStackTrace();
                //Als Default-Diagrammtyp wird BalkenChart verwendet.
                chart = new BalkenChart();
            }
        }

        try {
            this.data = source.getData(this.path, property.getProperty("splittingChar").charAt(0));
        } catch (Exception e) {
            this.data = source.getData(this.path, property.getProperty(";").charAt(0));
        }
        gui.setGraph(chart.generateChart(this.data));
        if (data == null) {
            gui.showHint("Ein Fehler ist aufgetreten.\nBitte ueberpruefen sie ihre Einstellungen");
        }
    }

    /**
     * Es wird eine Verbindung zu einer Datenbank aufgebaut. JComboBox Der
     * Nutzeroberflaeche wird mit den Tabellennamen der Datenbank gefuellt.
     */
    public void connect() {
        //Verbindung zur Datenbanl wirdaufgebaut
        this.dbSource.connect(property.getProperty("url"), property.getProperty("user"), property.getProperty("password"));
        //JComboBox Der Nutzeroberflaeche wird mit den Tabellennamen der Datenbank gefuellt.
        gui.fillComboBox(this.dbSource.getTableNames());

    }

    public void start() {
    }

    /**
     * Eine Property wird gespeichert.
     *
     * @param key Name der Property.
     * @param property Wert der Property.
     */
    public void saveProperty(String key, String property) {
        //Eine Property wird gespeichert
        this.property.savePropertie(key, property);
    }

    /**
     * Eine Property wird geladen.
     *
     * @param key Name der Property.
     * @param property Wert der Property.
     */
    public String getProperty(String key) {
        return this.property.getProperty(key);
    }

    /**
     *
     * @param selected
     * @param table
     */
    public boolean setAxis(int selected, String table, String column) {
        ArrayList<Double> colData = new ArrayList<Double>();
        for (String node : this.dbSource.showColumn(table, column)) {
            try{
                colData.add(Double.valueOf(node));
            }catch(Exception e){
                gui.showHint("Daten koennen nicht verwendet werden!");
                return false;
            }
        }

        //Es wird geprueft ob der X-Achse die anegegebenen Werte hinzugefuegt werden sollen.
        if (selected == 0) {
            this.gui.setGraph(this.chart.setAxis(colData, true));

            //Es wird geprueft ob der Y-Achse die anegegebenen Werte hinzugefuegt werden sollen.
        } else if (selected == 1) {
            this.gui.setGraph(this.chart.setAxis(colData, false));
        }
        return true;
    }

}

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
import de.szut.dqi12.bley.pool.database.DatabaseOperator;
import de.szut.dqi12.bley.pool.controller.source.SourceCSV;
import de.szut.dqi12.bley.pool.gui.Gui;
import de.szut.dqi12.bley.pool.properties.Charts;
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
    private Chart balkenChart;
    private Chart linieChart;
    private ArrayList<Double[]> data;
    private static Controller INSTANCE = null;
    private DatabaseOperator dbSource;

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
            source = new SourceCSV();
        }
        //Es wird versucht ein neues Chart-Objekt zu initialisieren, dessen Klassenname sich in den Properties befindet.
        //Wenn dies nicht gelingt wird ein BalkenChart-Objekt initialisiert.
        try {
            chart = (Chart) Class.forName("de.szut.dqi12.bley.pool.charts." + property.getProperty("chart")).newInstance();
        } catch (Exception e) {
            chart = new BalkenChart();
        }

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
        }
    }

    /**
     * Es wird ein Graph in die Nutzeroberflaeche geladen.
     *
     * @param path Pfad zur Datenquelle
     * @param chartName Name des Diagrammtyps
     */
    public void setChart(String path, Charts chartName) {
        //Es wird geprueft ob der angegebene Pfad und der Angegebene Name des Diagrammtyps null sind.
        if (path == null && chartName != null) {
            //Es wird versucht ein Objekt von dem uebergebenen Diagrammtyp zu erzeugen.
            try {
                chart = (Chart) Class.forName("de.szut.dqi12.bley.pool.charts." + chartName.name()).newInstance();
                //Der Name des Diagramtyps wird in den Properties gespeichert.
                property.savePropertie("chart", chartName.name());

            } catch (Exception ex) {
                ex.printStackTrace();
                //Als Default-Diagrammtyp wird BalkenChart verwendet.
                chart = new BalkenChart();
            }
            //Der Oberflaeche wird ein generiertes Diagramm uebergeben.
            gui.setGraph(chart.generateChart(data));

            //Es wird geprueft ob Pfad und Diagrammtyp ungleich null sind.
        } else if (chartName != null && path != null) {
            try {
                //Es wird versucht ein Objekt von dem uebergebenen Diagrammtyp zu erzeugen.
                chart = (Chart) Class.forName("de.szut.dqi12.bley.pool.charts." + chartName.name()).newInstance();
                //Der Name des Diagramtyps wird in den Properties gespeichert.
                property.savePropertie("chart", chartName.name());
            } catch (Exception ex) {
                //Als Default-Diagrammtyp wird BalkenChart verwendet.
                chart = new BalkenChart();
            }
            //Der Oberflaeche wird ein generiertes Diagramm uebergeben
            gui.setGraph(chart.generateChart(source.getData(path)));
            //Wenn der Pfad ungleich null ist, werden die Daten aus der Datenquelle geladen.
        } else if (path != null) {
            data = source.getData(path);
            //Der Oberflaeche wird ein generiertes Diagramm uebergeben
            gui.setGraph(chart.generateChart(data));

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

}

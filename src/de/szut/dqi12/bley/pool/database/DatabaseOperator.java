package de.szut.dqi12.bley.pool.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 *
 * @author Robin Bley
 */
public class DatabaseOperator {

    private String user;
    private String pass;
    private String url;
    private Connection conn;
    private int start;
    private int end;

    /**
     * Konstruktor
     *
     * @param url Pfad zur Datenbank
     * @param password Password des Datenbankbenutzers
     * @param user Benutzername zur Datenbank
     */
    public boolean connect(String url, String user, String password) {
        // Komponenten der Klasse werden initailisiert.
        this.url = url;
        this.pass = password;
        this.user = user;
        this.start = 0;
        this.end = 20;

        try {
            //Treiber der Datenbank wird geladen.
            Class.forName("org.sqlite.JDBC");

            //Verbindung zur Datenbank wird aufgebaut
            this.conn = DriverManager.getConnection(this.url, this.user, this.pass);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    /**
     * Ein SQL-Befehl wird auf der Datenbank ausgefuehrt
     *
     * @param query Der Auszuführende SQL-Befehl
     * @return Die Daten, welche die Datenbank zurueckliefert werden geliefert
     */
    public HashMap executeQuery(String query) {
        HashMap data = new HashMap<>();
        Statement stat;
        ResultSet rs = null;
        ArrayList<ArrayList<String>> values;
        try {
            //Ein "Query" wird ausgefuehrt
            stat = this.conn.createStatement();
            rs = stat.executeQuery(query);
            // Es wird durch die Spalten einer Tabelle einer Datenbank iteriert.
            ResultSetMetaData resultMeta = rs.getMetaData();
            int columnCount = resultMeta.getColumnCount();

            ArrayList<String> cLabels = new ArrayList<>();

            values = new ArrayList<>();

            for (int i = 1; i <= columnCount; i++) {
                cLabels.add(resultMeta.getColumnLabel(i));
                values.add(new ArrayList<>());
            }

            //Die Namen der Spalten der Tabelle werden ausgelesen und durch ihnen iterriert.
            while (rs.next()) {
                String cLabel = null;
                for (int i = 1; i <= columnCount; i++) {
                    cLabel = resultMeta.getColumnLabel(i);

                    values.get(i - 1).add(rs.getString(cLabel));
                }
            }

            for (int i = 1; i <= columnCount; i++) {
                data.put(cLabels.get(i - 1), values.get(i - 1).clone());
            }
        } catch (SQLException ex) {
            //Bei einem Fehler wird null zurueckgeliefert
            return null;
        }
        //Die HashMap mit Spaltennamen und Werten wird zurueckgeliefert
        return data;
    }

    /**
     * Die Tabellennamen der Datenbank werden ausgelesen
     *
     * @return Es werden die Tabellennamen in einer ArrayList zurueckgegeben
     */
    public ArrayList<String> getTableNames() {
        ResultSet rs = null;
        ArrayList<String> tableNames = new ArrayList<>();
        try {
            //Die namen der Tabellen werden aus den MataDaten der Verbindung zur Datenbank ausgelesen.
            rs = conn.getMetaData().getTables(null, null, null,
                    new String[]{"TABLE"});
            //Die Namen der Tabellen werden einer ArrayList hinzugefuegt.
            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //Die ArrayList wird zurueckgeliefert.
        return tableNames;

    }

    /**
     * Eine Tabelle einer Datenbank wird ausgelesen und Convertiert
     * zurueckgeliefert
     *
     * @param name Name der Tabelle
     * @param orderCol Name der Spalte, nach welcher die Tabelle geordnet wird.
     * @return Eine HashMap, welche als Key den jeweiligen Spaltennamen enthaelt
     * und als Value eine Arraylist vom Typ String, mit den jeweiligen Eintragen
     */
    public HashMap getTable(String name) {
        HashMap data = new HashMap<String, ArrayList>();
        Statement stat;
        ResultSet rs = null;
        ArrayList<ArrayList<String>> values = null;
        try {

            //Ein Statement wird vorbereitet, welches die Daten einer Tabelle ausliest.
            stat = this.conn.createStatement();
            PreparedStatement ps;
            if (this.start == 0) {
                ps = conn.prepareStatement("select * FROM " + name);
            } else {
                ps = conn.prepareStatement("select * FROM " + name + " LIMIT " + this.start);
            }
            rs = ps.executeQuery();

            ResultSetMetaData resultMeta = rs.getMetaData();
            int columnCount = resultMeta.getColumnCount();

            ArrayList<String> cLabels = new ArrayList<>();

            values = new ArrayList<>();

            for (int i = 1; i <= columnCount; i++) {
                cLabels.add(resultMeta.getColumnLabel(i));
                values.add(new ArrayList<>());
            }

            //Die Namen der Spalten der Tabelle werden ausgelesen und durch ihnen iterriert.
            while (rs.next()) {
                String cLabel = null;
                for (int i = 1; i <= columnCount; i++) {
                    cLabel = resultMeta.getColumnLabel(i);

                    values.get(i - 1).add(rs.getString(cLabel));
                }
            }

            for (int i = 1; i <= columnCount; i++) {
                data.put(cLabels.get(i - 1), values.get(i - 1).clone());
            }

        } catch (SQLException ex) {
            //Bei einem Fehler wird null zurueckgegeben
            ex.printStackTrace();
        }
        //Die HashMap mit Spaltennamen und Werten wird zurueckgegeben
        return data;
    }

    public String getDatabaseName() {
        return this.url.substring(12);
    }

    /**
     * Die Verbindung zur Datenbank wird geschlossen
     */
    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Double[]> getData(String path) {
        ArrayList<Double[]> data = new ArrayList<Double[]>();
        if (this.url == null || this.user == null || this.pass == null) {
            this.connect("jdbc\\:sqlite\\:aue_2012.db3", "root", "");
        } else {
            connect(this.url, this.user, this.pass);
        }
        getTable(path);

        /////
        return data;
    }
}

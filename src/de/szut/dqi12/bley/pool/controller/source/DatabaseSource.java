package de.szut.dqi12.bley.pool.controller.source;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * @author Robin Bley
 */
public class DatabaseSource implements DataSource{

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
     * @param driver Name des zu ladenenden Treibers
     */
    public DatabaseSource(String url, String password, String user, String driver) {
        // Komponenten der Klasse werden initailisiert.
        this.url = url;
        this.pass = password;
        this.user = user;
        this.start = 0;
        this.end = 20;

        try {
            //Treiber der Datenbank wird geladen.
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //Verbindung zur Datenbank wird aufgebaut
            this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseSource.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * Eine Verbindung zu einer Datenbank wird aufgebaut
     *
     * @return Eine Verbindung zur Datenbank
     */
    public Connection getConnection() {
        try {
            //Verbindung zur Datenbank wird aufgebaut.
            conn = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public void updateConnection(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
        setConnection(getConnection());
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
     * Eine Tabelle der Datenbank wird entfernt
     *
     * @param table name der Tabelle der Datenbank
     * @return liefert true oder false zurueck ob die operation erfolgte
     */
    public boolean deleteTable(String table) {
        try {
            //Ein Statement wird vorbereitet, welches eine biliebige Tabelle entfernt.
            PreparedStatement ps = conn.prepareStatement("DROP TABLE " + table);
            //Das Statement wird ausgefuehrt.
            ps.executeUpdate();
            //es wird true zurueckgelifert
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //es wird false zurueckgelifert
        return false;
    }

    /**
     * Eine neue Tabelle wird in der Datenbank erstellt
     *
     * @param name name der Tabelle der Datenbank
     * @param keys Spaltennamen der neuen Tabelle
     * @return liefert true oder false zurueck ob die operation erfolgte
     */
    public boolean createTable(String name, HashMap<String, String> keys) {
        Statement stat;
        try {
            
            //Ein Query wird vorbereitet, welches einen Table erstellt mit den uebergebenen Attributen.
            StringBuilder buffer = new StringBuilder("CREATE TABLE IF NOT EXISTS " + name + "(");
            keys.keySet().stream().forEach((key) -> {
                buffer.append(key).append(" ").append(keys.get(key)).append(" NOT NULL,");
            });
            buffer.deleteCharAt(buffer.length() - 1);
            buffer.append(");");
            //Ein Statement wird vorbereitet, welches eine Tabelle erzeugt
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement(buffer.toString());
            //Das Statement wird ausgefuehrt.
            ps.executeQuery();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

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
    public HashMap getTable(String name, String orderCol) {
        HashMap data = new HashMap<String, ArrayList>();
        Statement stat;
        ResultSet rs = null;
        ArrayList<ArrayList<String>> values = null;
        try {
            if (orderCol != null) {
                //Ein Statement wird vorbereitet, welches die Daten einer Tabelle ausliest.
                stat = this.conn.createStatement();
                PreparedStatement ps = conn.prepareStatement("select * from ? LIMIT ? OFFSET ? ORDER BY ?");
                //Der Name der Tabelle, ein start der Werte, einer Reihenfolge der Ordnung 
                //und ein Ende der Werte werden dem Statement hinzugefuegt.
                ps.setCursorName("?");
                ps.setString(1, name);
                ps.setInt(2, this.end);
                ps.setInt(3, this.start);
                ps.setString(4, orderCol);
                //Das Statement wird ausgefuehrt.
                rs = ps.executeQuery();
            } else {
                //Ein Statement wird vorbereitet, welches die Daten einer Tabelle ausliest.
                stat = this.conn.createStatement();
                PreparedStatement ps;
                if (this.start == 0) {
                    ps = conn.prepareStatement("select * FROM " + name);
                } else {
                    ps = conn.prepareStatement("select * FROM " + name + " LIMIT " + this.start);
                }
                rs = ps.executeQuery();
            }

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

    /**
     * Der Inhalt einer Datenbank wird zurueckgegeben
     *
     * @param table name der Tabelle
     * @param col Die Spalte die gezeigt werden soll
     * @return Liefert den Inhalt der Tabelle in einer ArrayList zurueck
     */
    public ArrayList<String> showColumn(String table, String col) {

        Statement stat;
        ResultSet rs = null;
        try {
            //Ein Statement wird vorberitet, wleches eine bestimmte Spalte einer Tabelle zurueckliefert.
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("SELECT ? from ?");
            //Dem Statement wird ein Tabellennamen und ein Spaltennamen uebergeben.
            ps.setString(1, col);
            ps.setString(2, table);
            //Das Staement wird ausgefuehrt.
            rs = ps.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();
            //Bei einem Fehler wird null zurueckgegeben.
            return null;
        }
        //Die werde der Spalte werden in einer ArrayList gespeichert und zurueckgegeben.
        ArrayList<String> resultArray = new ArrayList();
        try {
            while (rs.next()) {
                resultArray.add(rs.getString(col));
                if (resultArray.size() > 20) {
                    break;
                }
            }
        } catch (SQLException ex) {
            return null;
        }
        return resultArray;
    }

    /**
     *
     * @param table name der Tabelle der Datenbank
     * @param col Spalte, welche hinzugefuegt werden sollen
     * @return liefert true oder false zurueck ob die operation erfolgte
     */
    public boolean addCol(String col, String table) {
        Statement stat;
        try {
            //Ein Statement wird vorbereitet, welches einer Tabelle eine Spalte hinzufuegt.
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("ALTR TABLE ? ADD ? STRING");
            //Dem Statement werden Tabellennamen und Spaltennamen hinzugefuegt.
            ps.setString(1, table);
            ps.setString(2, col);
            //Das Statement wird ausgefuehrt.
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public boolean addRow(HashMap dataSet, String tableName) {
        try {
            //Ein Statement wird vorbereitetm wleches einer Tabelle eine anzahl von Werten in bestimmte Spalten hinzufuegt.
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ? (?) VALUES (?)");
            //Es werden Stringzwischenspeicher erzeugt.
            StringBuffer bufferCol = new StringBuffer();
            StringBuffer bufferValue = new StringBuffer();
            //Es wird durch die zuveraendernden Spalten iterriert.
            for (Object key : dataSet.keySet()) {
                //Ein String mit den Spaltennamen wird erzeugt.
                bufferCol.append(key + ", ");
                //Es wird ein String mit den Werten der Spalten erzeugt.
                bufferValue.append(dataSet.get(String.valueOf(key) + ", "));

            }
            // Dem Statement wird ein Tabellennamen, Spaltennamen und Werte Hinzugefuegt.
            ps.setString(1, tableName);
            ps.setString(2, bufferCol.substring(0, bufferCol.length() - 2));
            ps.setString(3, bufferValue.substring(0, bufferValue.length() - 2));
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Eine Spalte einer Tablle wird entfernt
     *
     * @param col Spalte, welche entfernt werden soll
     * @param table Tabelle, aus welcher die Spalte entfernt werden soll.
     * @return liefert true oder false zurueck ob die Operation erfolgte
     */
    public boolean delColumn(String col, String table) {
        Statement stat;
        try {
            //Es wird ein Statement vorbereitet, welches eine Spalte einer Tabelle entfernt.
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("ALTER TABLE ? DROP COLUMN ?");
            //Dem Statement wird ein Tabellennamen und ein Spaltennamen uebergeben.
            ps.setString(1, table);
            ps.setString(2, col);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * @deprecated Daten einer Datenbank werden aufbereitet
     *
     * @param resultSet Daten, welche aufbereitet werden sollen
     * @param columnName Name der Spalte, wessen Daten aufbereitet werden sollen
     * @return Aufbereitete Daten werden in einer ArrayList zurueck geliefert
     */
    public ArrayList<String> extractResult(ResultSet resultSet, String columnName) {
        ArrayList<String> resultArray = new ArrayList();

        try {
            while (resultSet.next()) {
                resultArray.add(resultSet.getString(columnName));
                if (resultArray.size() > 20) {
                    break;

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseSource.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return resultArray;
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

    @Override
    public ArrayList<Double[]> getData(String path) {
        ArrayList<Double[]> data = new ArrayList<Double[]>();
        this.url = "jdbc\\:sqlite\\:aue_2012.db3";
        this.user = "root";
        this.pass = "";
        getConnection();
        getTable(path, null);
        
        
        
        
        return data;
    }
}

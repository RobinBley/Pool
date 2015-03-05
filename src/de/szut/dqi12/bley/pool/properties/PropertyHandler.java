package de.szut.dqi12.bley.pool.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Verschiedene Einstellungen für den SQLiteBrowser.
 *
 * @author Till Schlechtweg
 */
public class PropertyHandler {

    private Properties properties;

    public PropertyHandler() {
        this.properties = new Properties();
        try {
            InputStream input = new FileInputStream("pool.properties");
            properties.load(input);
        } catch (IOException ex) {
            File file = new File("Pool.properties");
        }
    }

    /**
     * Liest die Einstellungen der jdbc.properties ein.
     *
     * @author Harm Hörnlein und Till Schlechtweg
     * @return false wenn Updaten der Properties fehlgeschlagen ist und true
     * wenn es erfolgreich war.
     */
    public boolean updateProperties() {
        File file = new File("Pool.properties");
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    return false;
                } else {
                    return saveProperties();
                }
            } catch (IOException ex) {
                Logger.getLogger(PropertyHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            try (InputStream inputStream = new FileInputStream(file)) {
                properties.load(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Standard Einstellungen, hart gecoded.
     *
     * @return Ob das Speichern der Standard Einstellungen funktioniert hat.
     */
    public boolean saveProperties(String source, String selectedItem, String chart) {
        properties.setProperty("dataSource", source);
        properties.setProperty("selectedItem", selectedItem);
        properties.setProperty("chart", chart);

        return updateProperties();
    }

    public void savePropertie(String key, String value) {
        properties.setProperty(key, value);
        updateProperties();
    }

    /**
     * Speichert die Properties ab.
     *
     * @return Ob das Abspeichern geklappt hat oder nicht.
     */
    public boolean saveProperties() {
        File file = new File("pool.properties");

        try {
            OutputStream outputStream = new FileOutputStream(file);
            properties.store(outputStream, "Settings for Pool");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getProperty(String property) {
        if (this.properties.containsKey(property)) {
            return this.properties.getProperty(property);
        }
        return null;
    }

}

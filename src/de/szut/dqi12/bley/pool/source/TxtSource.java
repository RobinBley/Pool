/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.source;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class TxtSource implements DataSource {

    @Override
    public ArrayList<Double[]> getData(String path, char splittingChar) {
        
        //==CSV
        
        CSVReader reader = null;
        ArrayList<Double[]> data = null;
        try {
            //Get the CSVReader instance with specifying the delimiter to be used
            reader = new CSVReader(new FileReader(path), splittingChar);
            String[] nextLine;
            data = new ArrayList<Double[]>();
            Double[] point = new Double[2];
            //Read one line at a time
            while ((nextLine = reader.readNext()) != null) {
                try {
                    point[0] = Double.valueOf(nextLine[0]);
                    point[1] = Double.valueOf(nextLine[1]);
                    data.add(point.clone());
                } catch (Exception e) {
                        return null;
                }
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    
}

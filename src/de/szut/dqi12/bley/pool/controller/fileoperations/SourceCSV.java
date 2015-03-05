/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.controller.fileoperations;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class SourceCSV implements DataSource {

    @Override
    public ArrayList<Double[]> getData(String path) {
        CSVReader reader = null;
        try
        {
            //Get the CSVReader instance with specifying the delimiter to be used
            reader = new CSVReader(new FileReader("path"),';');
            String [] nextLine;
            ArrayList<Double[]> data = new ArrayList<Double[]>();
            Double[] point = new Double[2];
            //Read one line at a time
            while ((nextLine = reader.readNext()) != null)
            {
                try{
                    point[0] = Double.valueOf(nextLine[0]);
                    point[1] =Double.valueOf(nextLine[1]);
                    data.add(point);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
            reader.close();
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
        return null;
    }
    
}

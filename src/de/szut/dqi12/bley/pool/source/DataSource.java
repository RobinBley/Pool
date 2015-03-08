/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.bley.pool.source;

import java.util.ArrayList;

/**
 *
 * @author Robin
 */
public interface DataSource {
    
    public ArrayList<Double[]> getData(String path, char splittingChar);
    
}

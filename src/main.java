
import de.szut.dqi12.bley.pool.controller.Controller;
import de.szut.dqi12.bley.pool.gui.Gui;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Robin
 */
public class main {

    public static void main(String[] args) {
          
    // Make it visible:
//        new Gui().setGraph(chart);
       
        Controller.getInstance().start();
        
    }

}

package de.szut.dqi12.bley.pool.gui;

import de.szut.dqi12.bley.pool.controller.Controller;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.views.ChartPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Robin
 */
public class Gui extends javax.swing.JFrame {

    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;

    /**
     * Komponenten der Klasse werden Initialisiert
     */
    public Gui() {
        initComponents();
        addListener();
        setVisible(true);

    }

    /**
     * Den Komponenten der Klasse Gui werden ActionListener inzugefuegt.
     */
    private void addListener() {
        this.jComboBox1.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JComboBox source = (JComboBox) e.getSource();
                        Controller.getInstance().setSelectedItem((String) source.getSelectedItem());
                    }
                }
        );
        this.jButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().connect();
            }
        });
    }

    /**
     * Komponenten der der Klasse werden Initialisiert
     */
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();

        jPanel1.setLayout(new BorderLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Verbinden");

        jTable1.setEnabled(false);
        jTable1.setDragEnabled(false);
        jTable1.setAutoCreateRowSorter(true);

        jTable1.setAutoCreateRowSorter(true);
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 376, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)))
                        .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    /**
     * Der Table des Frames wird mit Daten befuellt.
     *
     * @param data Daten in From einer Hashmap mit den entsprechenden Werten,
     * welche anzuzeigen sind.
     */
    public void fillTable(HashMap<String, ArrayList> data) {
        if(data != null){
        ArrayList<ArrayList<String>> tableData = new ArrayList<>();
        ArrayList<String> tableNames = new ArrayList<>();

        for (Object name : data.keySet().toArray()) {
            tableData.add(data.get(name.toString()));
            tableNames.add(name.toString());
        }

        DefaultTableModel tableModel = new DefaultTableModel();
        int i = 0;

        for (ArrayList<String> temp : tableData) {
            tableModel.addColumn(tableNames.get(i), temp.toArray());
            i++;
        }
        jTable1.setModel(tableModel);
        }

    }

    /**
     * In Dem Panels des Frames wird ein Chart2D eingefuegt.
     *
     * @param chart Chart2D-Object, welches ins Panel geladen wird.
     */
    public void setGraph(Chart2D chart) {
        if(chart != null){
        ChartPanel cp = new ChartPanel(chart);
        this.jPanel1.removeAll();
        this.jPanel1.setLayout(new BorderLayout());
        this.jPanel1.add(chart);
        this.jPanel1.validate();
        System.out.println(getBackground().toString());
        }
        
        
        
    }
    public void setSelectedItem(String item){
        jComboBox1.setSelectedItem(item);
    }
}

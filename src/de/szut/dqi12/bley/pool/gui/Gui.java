package de.szut.dqi12.bley.pool.gui;

import de.szut.dqi12.bley.pool.controller.Controller;
import de.szut.dqi12.bley.pool.properties.*;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.views.ChartPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private JMenu fileOpenItem;
    private JMenuItem databseMenu;
    private JMenu fileMenu;
    private JMenuBar bar;
    private JMenu chartMenu;
    private JMenuItem chartItemBalkenDiagramm;
    private JMenuItem chartItemLinienDiagramm;
    private JMenu options;
    private JMenuItem properties;
    private JMenuItem menuCsv;
    private JMenuItem menuTxt;
    private JMenuItem menuOther;

    /**
     * Komponenten der Klasse werden Initialisiert
     */
    public Gui() {
        initComponents();
        addListener();
        setVisible(true);

    }

    private void disableDatabaseComponents() {
        jButton1.setVisible(false);
        jTable1.setVisible(false);
        jComboBox1.setVisible(false);
    }

    private void enableDatabaseComponents() {
        jButton1.setVisible(true);
        jTable1.setVisible(true);
        jComboBox1.setVisible(true);

    }

    /**
     * Den Komponenten der Klasse Gui werden ActionListener inzugefuegt.
     */
    private void addListener() {

//        Controller.getInstance().setChart("getSelectedColumoderso", null);
        this.properties.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new PropertyDialog().setVisible(true);
            }
        });
        this.jComboBox1.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JComboBox source = (JComboBox) e.getSource();
                        Controller.getInstance().setTable((String) source.getSelectedItem());
                    }
                }
        );
        this.jButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().connect();
            }
        });

        chartItemBalkenDiagramm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().setChart(null, null, Charts.BalkenChart);
            }
        });
        chartItemLinienDiagramm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().setChart(null, null, Charts.LinienChart);

            }
        });
        databseMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enableDatabaseComponents();
            }
        });

        menuCsv.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "CSV", "csv");
                fileChooser.setFileFilter(filter);
                int selectedOption = fileChooser.showOpenDialog(menuCsv);

                if (selectedOption == JFileChooser.APPROVE_OPTION) {
                    Controller.getInstance().setChart(fileChooser.getSelectedFile().getPath(), Sources.SourceCSV, null);
                    disableDatabaseComponents();
                    jPanel1.setBounds(getBounds());
                    validate();
                }

            }
        });
        menuTxt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "TEXT", "txt");
                fileChooser.setFileFilter(filter);
                int selectedOption = fileChooser.showOpenDialog(menuTxt);

                if (selectedOption == JFileChooser.APPROVE_OPTION) {
                    Controller.getInstance().setChart(fileChooser.getSelectedFile().getPath(), Sources.TxtSource, null);
                    disableDatabaseComponents();
                    jPanel1.setBounds(getBounds());
                    validate();
                }

            }
        });
        menuOther.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int selectedOption = fileChooser.showOpenDialog(menuOther);

                if (selectedOption == JFileChooser.APPROVE_OPTION) {
                    Controller.getInstance().setChart(fileChooser.getSelectedFile().getPath(), Sources.TxtSource, null);
                    disableDatabaseComponents();
                    jPanel1.setBounds(getBounds());
                    validate();
                }

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

        //Intialisierung der MenuBar
        //Intialisierung des Menus:File
        bar = new JMenuBar();
        menuCsv = new JMenuItem();
        menuTxt = new JMenuItem();
        fileOpenItem = new JMenu();
        fileMenu = new JMenu();
        databseMenu = new JMenuItem();
        menuOther = new JMenuItem();
        menuCsv.setText("CSV-Datei");
        menuTxt.setText("TEXT-DATEI");
        menuOther.setText("try other...");
        fileMenu.setText("file");
        fileOpenItem.setText("Open File");
        fileMenu.add(fileOpenItem);
        databseMenu.setText("Datenbank");
        fileMenu.add(databseMenu);
        fileOpenItem.add(menuCsv);
        fileOpenItem.add(menuTxt);
        fileOpenItem.add(menuOther);
        bar.add(fileMenu);

        //Intialisierung des Menus:Chart
        chartItemBalkenDiagramm = new JMenuItem();
        chartItemLinienDiagramm = new JMenuItem();
        chartMenu = new JMenu();
        chartMenu.setText("Chart");
        chartItemBalkenDiagramm.setText("Balkendiagramm");
        chartItemLinienDiagramm.setText("Liniendiagramm");
        chartMenu.add(chartItemBalkenDiagramm);
        chartMenu.add(chartItemLinienDiagramm);
        bar.add(chartMenu);

        //Intialisierung des Menus:Chart
        options = new JMenu();
        properties = new JMenuItem();
        options.setText("Options");
        properties.setText("properties");
        options.add(properties);
        bar.add(options);

        setJMenuBar(bar);

        setTitle("Pool");
        jPanel1.setLayout(new BorderLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Verbinden");

        jTable1.setEnabled(false);
        jTable1.setDragEnabled(false);
        jTable1.setAutoCreateRowSorter(true);

        jTable1.setAutoCreateRowSorter(true);
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel());

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
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    /**
     * Der Table des Frames wird mit Daten befuellt.
     *
     * @param data Daten in From einer Hashmap mit den entsprechenden Werten,
     * welche anzuzeigen sind.
     */
    public void fillTable(HashMap<String, ArrayList<String>> data) {
        if (data != null) {
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

    public void fillComboBox(ArrayList<String> items) {
        jComboBox1.removeAllItems();
        for (String item : items) {
            jComboBox1.addItem(item);
        }
    }

    /**
     * In Dem Panels des Frames wird ein Chart2D eingefuegt.
     *
     * @param chart Chart2D-Object, welches ins Panel geladen wird.
     */
    public void setGraph(Chart2D chart) {
        if (chart != null) {
            ChartPanel cp = new ChartPanel(chart);
            this.jPanel1.removeAll();
            this.jPanel1.setLayout(new BorderLayout());
            this.jPanel1.add(chart);
            this.jPanel1.validate();
        }

    }

    public void showHint(String hint) {
        JOptionPane.showMessageDialog(this, hint);
    }

}

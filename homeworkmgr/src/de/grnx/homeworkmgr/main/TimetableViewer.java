package de.grnx.homeworkmgr.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.IntStream;

public class TimetableViewer extends JFrame {
    private JTable timetable;
    private JButton buttonSave;
    private JButton buttonLoad;
    private JButton buttonReset;

    public TimetableViewer(String[][] arr) {

        JTabbedPane tPane = new JTabbedPane();

        JPanel panel1 = new JPanel();

        tPane.addTab("Schedule", panel1);
        tPane.addTab("Settings", Settings.getSettingsComponent());

        setTitle("Timetable Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        timetable = new JTable() {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
        };

        JScrollPane scrollPane = new JScrollPane(timetable);
        panel1.setLayout(new BorderLayout());
        panel1.add(scrollPane, BorderLayout.CENTER);

        loadARRData(arr);
        timetable.getTableHeader().setReorderingAllowed(false);

        // Create buttons
        buttonSave = new JButton("Save");
        buttonLoad = new JButton("Load");
        buttonReset = new JButton("Reset");

        buttonSave.addActionListener(e -> {
            File file = FileHandler.saveFile(Settings.getTableLocation().getAbsolutePath(), new String[]{"ser"});
            if (!(file == null)) {
                Serializer.serializeObject(file.toString(), tableToNestedArray(timetable));
                Main.SETTINGS.setTableLocation(file);
            }
        });

        buttonLoad.addActionListener(e -> {
            File file = FileHandler.openFile(Settings.getTableLocation().getAbsolutePath(), new String[]{"ser"});
            if (!(file == null)) {
                loadARRData((String[][]) Serializer.deserializeObject(file.toString()));
                Main.SETTINGS.setTableLocation(file);
            }
        });

        buttonReset.addActionListener(e -> loadARRData(Timetable.timetableARR));

        // Create a panel for the buttons and add them
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        buttonPanel.add(buttonSave);
        buttonPanel.add(buttonLoad);
        buttonPanel.add(buttonReset);

        panel1.add(buttonPanel, BorderLayout.SOUTH);

        add(tPane);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }





    
    private void loadARRData(String[][] arr) {
    	DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // Make all cells non-editable
            }
        };
        
        	model.setColumnIdentifiers(arr[0]);
        		//headers
        	
        	for(int i =1; i<arr.length;i++) {
                model.addRow(arr[i]);

        	}
        	timetable.setModel(model);
    	
    }
    
    
    private String[][] tableToNestedArray(JTable t) {  
    	
        int columnCount = t.getColumnCount();
        int rowCount = t.getRowCount();
        String[][] nArr = new String[rowCount+1][columnCount];
        
        //ich bin besser als ihr weil ich benutze keine loops sondern streams
        nArr[0]= IntStream.range(0, t.getColumnCount())
                .mapToObj(t::getColumnName)
                .toArray(String[]::new);
        
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                nArr[i+1][j] = (t.getValueAt(i, j) != null) ? t.getValueAt(i, j).toString() : "";
            }
        }
        return nArr;
    }


    
    private void loadCSVData(String csvData) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        try (BufferedReader reader = new BufferedReader(new StringReader(csvData))) {
            String line;
            // Read the first line as headers
            if ((line = reader.readLine()) != null) {
                String[] headers = line.split(",");
                model.setColumnIdentifiers(headers);
            }

            // Read the remaining lines as data
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                model.addRow(rowData);
            }

            timetable.setModel(model);
        } catch (IOException e) {
            e.printStackTrace();
            ErrView.showStackTraceErrorDialog(null, "loadCSVdata", e);
        }
    }
}

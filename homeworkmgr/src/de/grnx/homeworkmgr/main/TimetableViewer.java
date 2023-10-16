//TODO: Table Resize Row / Y >:-(
package de.grnx.homeworkmgr.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.io.IOException;

public class TimetableViewer extends JFrame {
    private JTable timetable;
    private JButton buttonSave;
    private JButton buttonLoad;
    private JButton buttonReset;
//get table return /param TODO
    public TimetableViewer(String[][] arr) {
    	JTabbedPane tPane = new JTabbedPane();
    	JPanel panel2 = new JPanel();
    	
    	
        setTitle("Timetable Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        timetable = new JTable() {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
            
        };

        JScrollPane scrollPane = new JScrollPane(timetable);
        add(scrollPane, BorderLayout.CENTER);

        //loadCSVData(csvData);
        loadARRData(arr);
        timetable.getTableHeader().setReorderingAllowed(false);

        // Create buttons
        buttonSave = new JButton("Save");
        buttonLoad = new JButton("Load");
        buttonReset = new JButton("Reset");
        
        buttonSave.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  File file = FileHandler.saveFile(Settings.getTableLocation().getAbsolutePath(), new String[] {"ser"});
        		  if(!(file==null)) {
        			Serializer.serializeObject(file.toString(),tableToNestedArray(timetable));
        			  	Main.SETTINGS.setTableLocation(file);
        		  	}


        	  } 
        	} );
        
        buttonLoad.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) { 
    		  File file = FileHandler.openFile(Settings.getTableLocation().getAbsolutePath(), new String[] {"ser"});
    		  if(!(file==null)) {
    			  loadARRData((String[][])Serializer.deserializeObject(file.toString()));
  			  		Main.SETTINGS.setTableLocation(file);
    		  }
      	  } 
      	} );
        
        buttonReset.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  	loadARRData(Timetable.timetableARR);
        	 } 
        	} );
        
        
        // Create a panel for the buttons and add them
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,3));
        buttonPanel.add(buttonSave);
        buttonPanel.add(buttonLoad);
        buttonPanel.add(buttonReset);

        // Add the button panel below the table
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setSize((int)(1.5*timetable.getWidth()),timetable.getHeight()+4*buttonPanel.getHeight());
        
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

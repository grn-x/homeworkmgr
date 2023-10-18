package de.grnx.homeworkmgr.main;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.w3c.dom.traversal.DocumentTraversal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Vector;

public class HomeworkViewer extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public HomeworkViewer(ArrayList<Object[]> arrList) {
		switch (Main.FlatLafConfig) {
		case 0: {
			//skip for swing gui
		}case 1: {
			com.formdev.flatlaf.FlatDarkLaf.setup();
			break;
		}case 2: {
			com.formdev.flatlaf.FlatIntelliJLaf.setup();
			break;
		}case 3: {
			com.formdev.flatlaf.FlatDarculaLaf.setup();
			break;
		}case 4: {
			com.formdev.flatlaf.FlatLightLaf.setup();
			break;
		}
		default:
//			com.formdev.flatlaf.FlatDarkLaf.setup();
		}
        setTitle("Homework Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        setLayout(new BorderLayout());
        //add(this,BorderLayout.CENTER);
        
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,2));
        
        JButton saveButton = new JButton("Save Homework");
        
        JButton addColButton = new JButton("Add Homework");
        p.add(saveButton);
        p.add(addColButton);
        add(p,BorderLayout.SOUTH);
        
        
        JTextArea textArea = new JTextArea();//for the selection listener below
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        add(textArea, BorderLayout.NORTH);

        saveButton.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) {
              saveHomeworkState(table);

      	  }});
        
        


        		  
        	  
        	
        
        
        addColButton.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  addRow(null);
        	  }});
        
        String[] columnNames = {"Subject", "Time", "Note", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

        for (Object[] rowData : arrList) {
            tableModel.addRow(rowData);
        }
        
        String[] statusOptions = {"Pending", "Completed", "In Progress"};
        JComboBox<String> comboBox = new JComboBox<>(statusOptions);
        comboBox.setEditable(true);
        TableColumn statusColumn = table.getColumnModel().getColumn(3);
        statusColumn.setCellEditor(new DefaultCellEditor(comboBox));


        
        
        
        
        // Custom cell renderer for the text area
        TableCellRenderer tableCellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
            JTextArea area = new JTextArea();
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setText(value != null ? value.toString() : "");
            area.setEditable(false);
            return area;
        };

        table.getColumnModel().getColumn(2).setCellRenderer(tableCellRenderer);

        // ListSelectionListener to update the text area based on the selected row
        ListSelectionListener selectionListener = e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0 && selectedRow < arrList.size()) {
                    Object[] data = arrList.get(selectedRow);
                    if (data.length > 2 && data[2] != null) {
                        textArea.setText(data[2].toString());
                    }
                }
            }
        };

        table.getSelectionModel().addListSelectionListener(selectionListener);
    

        
        
        
        table.getColumnModel().getColumn(2).setCellRenderer(tableCellRenderer);
        table.getSelectionModel().addListSelectionListener(selectionListener);

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Handle key typed events here
            }

            @Override
            public void keyPressed(KeyEvent e) {

                if ((e.isShiftDown() || e.isControlDown() || e.isAltDown() || e.isMetaDown()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                	e.consume();

                    // Add a new line in the text area
                    textArea.append("\n");
                } else if (!(e.isShiftDown() || e.isControlDown() || e.isAltDown() || e.isMetaDown()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                	e.consume();
                    // Save the value and put it back into the table at the current row and column index 3
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
                        tableModel.setValueAt(textArea.getText(), selectedRow, 2);
                        
                        textArea.setFocusable(false);
                        textArea.setFocusable(false);
                        new Thread(() -> {
                            try {
                                Thread.sleep(100);
                                textArea.setFocusable(true);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }).start();
                        
          				//Popup.displayNotification(textArea, "Updated Homework Cell", 1000);

                        saveHomeworkState(table);

                    }
                }
             }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    
        
        
        
        
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Object[] getRowData(int rowIndex) {
        Vector<?> rowData = (Vector<?>) tableModel.getDataVector().elementAt(rowIndex);
        return rowData.toArray();
    }

    private void updateTable(ArrayList<Object[]> arrList) {
        tableModel.setRowCount(0); // Clear existing data
        for (Object[] rowData : arrList) {
            tableModel.addRow(rowData);
        }
    }

    private void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }
    
    private void saveHomeworkState(JTable table) {
		  ArrayList<EnSt> exList = new ArrayList<EnSt>();
		  for(int i =0; i<table.getRowCount();i++) {
//			  EnSt entry = new EnSt();
//			  entry.setStrSubject(getName());
//			  entry.setLocalDateTime();
//			  entry.setStrNote(getName());
			  
			  //System.out.println("\""+Main.isDateParsable(table.getValueAt(i, 1) != null ? table.getValueAt(i, 1).toString() : "")? (LocalDateTime) table.getValueAt(i, 1) : LocalDateTime.now() table.getValueAt(i, 2) != null ? table.getValueAt(i, 2).toString() : "")+"\"");
			  try {
			exList.add(new EnSt(
				    table.getValueAt(i, 0) != null ? table.getValueAt(i, 0).toString() : "",
				    		java.time.LocalDateTime.parse(table.getValueAt(i, 1)==null||table.getValueAt(i, 1).toString().isBlank()?LocalDateTime.now().format(Main.formatter).toString():table.getValueAt(i, 1).toString(), Main.formatter),
				    table.getValueAt(i, 2) != null ? table.getValueAt(i, 2).toString() : "",
				    table.getValueAt(i,3) != null?table.getValueAt(i, 3).toString():"Pending"
				));
			
				Popup.displayNotification(table, "Saved", 1000);
			  }catch (Exception e1) {
				  	e1.printStackTrace();
				  	ErrView.showStackTraceErrorDialog(null, "Error when saving Homework probably because date parsing error. Leave time cell empty for todays date", e1);
			  }
		  }
		  
		//NoteHandler.saveNote(comboBox.getSelectedItem().toString(), now, textArea.getText());
		  Serializer.serializeObject(Settings.getDataLocation().getAbsolutePath(),(Object)exList);

    }
    
}

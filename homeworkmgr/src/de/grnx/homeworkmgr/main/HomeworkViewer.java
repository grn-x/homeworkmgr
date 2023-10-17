package de.grnx.homeworkmgr.main;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.w3c.dom.traversal.DocumentTraversal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Vector;

public class HomeworkViewer extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public HomeworkViewer(ArrayList<Object[]> arrList) {
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

        saveButton.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) {
      		  ArrayList<EnSt> exList = new ArrayList<EnSt>();
      		  for(int i =0; i<table.getRowCount();i++) {
//      			  EnSt entry = new EnSt();
//      			  entry.setStrSubject(getName());
//      			  entry.setLocalDateTime();
//      			  entry.setStrNote(getName());
      			  
      			  //System.out.println("\""+Main.isDateParsable(table.getValueAt(i, 1) != null ? table.getValueAt(i, 1).toString() : "")? (LocalDateTime) table.getValueAt(i, 1) : LocalDateTime.now() table.getValueAt(i, 2) != null ? table.getValueAt(i, 2).toString() : "")+"\"");
      			  try {
      			exList.add(new EnSt(
      				    table.getValueAt(i, 0) != null ? table.getValueAt(i, 0).toString() : "",
      				    		java.time.LocalDateTime.parse(table.getValueAt(i, 1)==null||table.getValueAt(i, 1).toString().isBlank()?LocalDateTime.now().format(Main.formatter).toString():table.getValueAt(i, 1).toString(), Main.formatter),
      				    table.getValueAt(i, 2) != null ? table.getValueAt(i, 2).toString() : "",
      				    table.getValueAt(i,3) != null?table.getValueAt(i, 3).toString():"Pending"
      				));
      			
      				Popup.displayNotification(p, "Saved", 500);
      			  }catch (Exception e1) {
      				  	e1.printStackTrace();
      				  	ErrView.showStackTraceErrorDialog(null, "Error when saving Homework probably because date parsing error. Leave time cell empty for todays date", e1);
      			  }
      		  }
      		  
  			//NoteHandler.saveNote(comboBox.getSelectedItem().toString(), now, textArea.getText());
      		  Serializer.serializeObject(Settings.getDataLocation().getAbsolutePath(),(Object)exList);

      		  
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


        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Object[] getRowData(int rowIndex) {
        Vector<?> rowData = (Vector<?>) tableModel.getDataVector().elementAt(rowIndex);
        return rowData.toArray();
    }

    public void updateTable(ArrayList<Object[]> arrList) {
        tableModel.setRowCount(0); // Clear existing data
        for (Object[] rowData : arrList) {
            tableModel.addRow(rowData);
        }
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }
}

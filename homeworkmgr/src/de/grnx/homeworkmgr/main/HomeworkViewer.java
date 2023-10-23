package de.grnx.homeworkmgr.main;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.w3c.dom.traversal.DocumentTraversal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.util.EventObject;
import java.util.HashSet;
import java.util.Vector;

public class HomeworkViewer extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> dropdown;
    private HashSet<String> options;
    
    
    public HomeworkViewer(ArrayList<Object[]> arrList) {

        setTitle("Homework Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        setLayout(new BorderLayout());
        //add(this,BorderLayout.CENTER);
        
        
        
        	  
        	
        
        
       
        
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


        
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,2));
        
        JButton saveButton = new JButton("Save Homework");
        
        JButton addColButton = new JButton("Add Homework");
        p.add(saveButton);
        p.add(addColButton);
        
        JPanel btnfieldPanel = new JPanel();
       
        
         addColButton.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  addRow(null);
        	  }});
         
        JTextArea textAreaF = new JTextArea();//for the selection listener below
        textAreaF.setLineWrap(true);
        textAreaF.setWrapStyleWord(true);
        //add(textArea, BorderLayout.NORTH);
        btnfieldPanel.setLayout(new BorderLayout());
        btnfieldPanel.add(p, BorderLayout.SOUTH);
        btnfieldPanel.add(textAreaF, BorderLayout.CENTER);
        
        add(btnfieldPanel,BorderLayout.SOUTH);
        
        
        
        
        
        textAreaF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Handle key typed events here
            }

            @Override
            public void keyPressed(KeyEvent e) {
            	int row = table.getSelectedRow();
            	int col = table.getSelectedColumn();
            		//updateSelectedCell(textAreaF.getText());
            	if(table.getCellEditor()!=null){
            	table.getCellEditor().cancelCellEditing();
            	}
            	
            	table.setValueAt(textAreaF.getText()!=null?textAreaF.getText():"", row, 2);
            	;
            	System.out.println(table.getValueAt(row, 2));
            		System.out.println("typed");
            		
            		
                if ((e.isShiftDown() || e.isControlDown() || e.isAltDown() || e.isMetaDown()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                	e.consume();

                    // Add a new line in the text area
                    textAreaF.append("\n");
                } else if (!(e.isShiftDown() || e.isControlDown() || e.isAltDown() || e.isMetaDown()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                	e.consume();
                    // Save the value and put it back into the table at the current row and column index 3
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
                        tableModel.setValueAt(textAreaF.getText(), selectedRow, 2);
                        
                       /* textArea.setFocusable(false);
                        textArea.setFocusable(false);
                        new Thread(() -> {
                            try {
                                Thread.sleep(100);
                                textArea.setFocusable(true);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }).start();*/
                        
          				//Popup.displayNotification(textArea, "Updated Homework Cell", 1000);

                        saveHomeworkState(table);

                    }
                }
             }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    
        
        
        
        
        saveButton.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) {
              saveHomeworkState(table);

      	  }});
        
        

        // Add ListSelectionListener to the table
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Object note = table.getValueAt(selectedRow, 2);
                textAreaF.setText(note != null ? note.toString() : "");
            }
        });

      
        
        
        
        
        
     
        
        TableColumn noteColumn = table.getColumnModel().getColumn(2);

        DefaultCellEditor noteCellEditor = new DefaultCellEditor(new JTextField()) {
            JTextArea textArea = new JTextArea();
            {
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                textArea.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        // Your logic here when a key is typed in the text area
                        System.out.println("Key typed in the note cell.");
                        	
                        
                        textAreaF.setText((textArea.getText() != null) ? textArea.getText().toString() : "");


                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        // Your logic here when a key is pressed in the text area
                        System.out.println("Key pressed in the note cell.");


                        if ((e.isShiftDown() || e.isControlDown() || e.isAltDown() || e.isMetaDown()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        	e.consume();

                            textAreaF.append("\n");
                        } else if (!(e.isShiftDown() || e.isControlDown() || e.isAltDown() || e.isMetaDown()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        	e.consume();
                            int selectedRow = table.getSelectedRow();
                            if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
                                tableModel.setValueAt(textAreaF.getText(), selectedRow, 2);
                                
                                saveHomeworkState(table);

                            }
                        }
                     
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        // Your logic here when a key is released in the text area
                        System.out.println("Key released in the note cell.");
                    }
                });

                delegate = new EditorDelegate() {
                    @Override
                    public void setValue(Object value) {
                        textArea.setText((value != null) ? value.toString() : "");
                    }

                    @Override
                    public Object getCellEditorValue() {
                        return textArea.getText();
                    }

                    @Override
                    public boolean shouldSelectCell(EventObject anEvent) {
                        return true;
                    }
                };
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                textArea.setText((value != null) ? value.toString() : "");
                return textArea;
            }
        };

        noteColumn.setCellEditor(noteCellEditor);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
//        defaults.put("Table.alternateRowColor", new ColorUIResource(Color.LIGHT_GRAY));
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        	 @Override
             public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                 Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                 
                     Color color1, color2;

                     // Define color combinations based on the integer value
                     switch (Main.FlatLafConfig) {
                         case 0:
                             color1 = Color.WHITE;
                             color2 = Color.GRAY;
                             break;
                         case 1:
                             color1 = new Color(50,53,56);
                             color2 = new Color(60,63,66);
                             break;
                         case 2:
                             color1 = Color.BLUE;
                             color2 = Color.CYAN;
                             break;
                         case 3:
                             color1 = Color.GREEN;
                             color2 = Color.MAGENTA;
                             break;
                         case 4:
                             color1 = Color.ORANGE;
                             color2 = Color.PINK;
                             break;
                         default:
                             color1 = Color.WHITE;
                             color2 = Color.BLACK;
                     	}
                     
                     if (row % 2 == 0) {
                         c.setBackground(color1);
                     } else {
                         c.setBackground(color2);
                     }
                 return c;
            };
        });

    }
    
  
//    private void updateSelectedCell(String text) {
//        int selectedRow = table.getSelectedRow();
//        if (selectedRow != -1) {
//            table.setValueAt(text, selectedRow, 2);
//        }
//    }
    
    //added
    private void updateSelectedCell(String text) {
        int selectedRow = table.getSelectedRow();
        int noteColumnIndex = 2; // Note column index
        if (selectedRow != -1) {
            table.setValueAt(text, selectedRow, noteColumnIndex);
        }
    }

    // Add a public method to update the "Note" column from outside
    public void updateNoteCell(int row, String text) {
        int noteColumnIndex = 2; // Note column index
        table.setValueAt(text, row, noteColumnIndex);
    }
    
    //aded
    
    
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
			
				Popup.displayNotification(table, "Saved", 500);
			  }catch (Exception e1) {
				  	e1.printStackTrace();
				  	ErrView.showStackTraceErrorDialog(null, "Error when saving Homework probably because date parsing error. Leave time cell empty for todays date", e1);
			  }
		  }
		  
		//NoteHandler.saveNote(comboBox.getSelectedItem().toString(), now, textArea.getText());
		  Serializer.serializeObject(Settings.getDataLocation().getAbsolutePath(),(Object)exList);

    }
    
    private void updateDropdownOptions() {
        // Code to update options
        String[] updatedOptions = options.toArray(new String[0]);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(updatedOptions);
        dropdown.setModel(model);
    }
}




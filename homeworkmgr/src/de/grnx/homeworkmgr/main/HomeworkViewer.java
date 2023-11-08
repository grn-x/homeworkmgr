package de.grnx.homeworkmgr.main;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.accessibility.Accessible;
import javax.swing.*;
import javax.swing.plaf.basic.ComboPopup;
import javax.accessibility.Accessible;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.w3c.dom.traversal.DocumentTraversal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//public class HomeworkViewer extends JFrame {
public class HomeworkViewer extends JPanel {
    public static JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> dropdown;
    private HashSet<String> options;
    public double preferredWidthModifier;

    
    public HomeworkViewer(ArrayList<Object[]> arrList) {

//        setTitle("Homework Viewer");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    	if(Main.SETTINGS.getAdditionalSettings("Scaling factor for preferred HomeworkViewer cell width")==null||Main.SETTINGS.getAdditionalSettings("Scaling factor for preferred HomeworkViewer cell width").isBlank()) {
    		Main.SETTINGS.addAdditionalSettings("Scaling factor for preferred HomeworkViewer cell width", "1");
		}else {
			Main.SETTINGS.addAdditionalSettings("Scaling factor for preferred HomeworkViewer cell width", Main.SETTINGS.getAdditionalSettings("Scaling factor for preferred HomeworkViewer cell width"));
			try {
				preferredWidthModifier = Double.valueOf(Main.SETTINGS.getAdditionalSettings("Scaling factor for preferred HomeworkViewer cell width"));
			}catch(Exception e) {
				e.printStackTrace();
				ErrView.showStackTraceErrorDialog(null, "\"Scaling factor for preferred HomeworkViewer cell width\" Setting could not be parsed, Double Expected", e);
				preferredWidthModifier = 1;
			}
				
		}

        setLayout(new BorderLayout());
        //add(this,BorderLayout.CENTER);
        
        
        
        	  
        	
        
        
       
        
        String[] columnNames = {"Subject", "Time", "Note", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel){
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        //        table.setPreferredSize(new Dimension(1000, getPreferredSize().height));
        
        
        for (Object[] rowData : arrList) {
            tableModel.addRow(rowData);
        }
        
        String[] statusOptions = {"Pending", "Completed", "In Progress"};
        JComboBox<String> comboBox = new JComboBox<>(statusOptions);
        comboBox.setEditable(true);
        
        
        TableColumn statusColumn = table.getColumnModel().getColumn(3);
        statusColumn.setCellEditor(new DefaultCellEditor(comboBox));


        
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,3));
        
        JButton saveButton = new JButton("Save Homework");
        
        JButton addColButton = new JButton("Add Homework");
        p.add(saveButton);
        p.add(addColButton);
        
        
        
        p.add(new CheckedComboBox<>(makeModel()));
        
        
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
        btnfieldPanel.add(p, BorderLayout.NORTH);
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
            	if(table.getCellEditor()!=null)table.getCellEditor().cancelCellEditing();
            	
            	table.setValueAt(textAreaF.getText()!=null?textAreaF.getText():"", row, 2);
            	;

            		
            		
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

                        	
                        
                        textAreaF.setText((textArea.getText() != null) ? textArea.getText().toString() : "");


                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        // Your logic here when a key is pressed in the text area



                        if ((e.isShiftDown() || e.isControlDown() || e.isAltDown() || e.isMetaDown()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        	e.consume();

                            textAreaF.append("\n");
                        } else if (!(e.isShiftDown() || e.isControlDown() || e.isAltDown() || e.isMetaDown()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        	e.consume();
                            int selectedRow = table.getSelectedRow();
                            if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
                                tableModel.setValueAt(textAreaF.getText(), selectedRow, 2);
                                
                                
                            	if(table.getCellEditor()!=null)table.getCellEditor().cancelCellEditing();

                                saveHomeworkState(table);

                            }
                        }
                     
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        // Your logic here when a key is released in the text area

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
//        table.setPreferredScrollableViewportSize(new Dimension(600, 400));
//        scrollPane.setPreferredSize(new Dimension(600, 400));
        /*scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            	System.out.println(table.getSize() +" "+ table.getPreferredSize());
                int newWidth = (int) (table.getSize().width * preferredWidthModifier);
//                table.setPreferredScrollableViewportSize(new Dimension(newWidth, table.getPreferredScrollableViewportSize().height));
                table.setPreferredSize(new Dimension(newWidth, table.getSize().height));
                scrollPane.revalidate();
            }
        });*/

        add(scrollPane, BorderLayout.CENTER);
//        setSize(600, 400);
//        setLocationRelativeTo(null);
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
        
        new Thread(() -> {
            while (table.getSize().width == 0 || table.getSize().height == 0) {
                // Nothing
            }
            System.out.println(table.getSize());
            System.out.println(table.getPreferredSize());
            int columnCount = table.getColumnModel().getColumnCount();
            int preferredWidth = (int) Math.round(table.getSize().width * preferredWidthModifier / columnCount);
            for (int i = 0; i < columnCount; i++) {
                TableColumn column = table.getColumnModel().getColumn(i);
                column.setPreferredWidth(preferredWidth);
            }
            System.out.println(table.getPreferredSize());

        }).start();
      
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
    
    

    
//
//	  private ExampleComboBox() {
//		    super(new BorderLayout());
//		    JPanel p = new JPanel(new GridLayout(0, 1));
//		    p.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
//		    p.add(new JLabel("CheckedComboBox:"));
//		    p.add(new CheckedComboBox<>(makeModel()));
//		    add(p, BorderLayout.NORTH);
//		    setPreferredSize(new Dimension(320, 240));
//		  }

private static ComboBoxModel<CheckableItem> makeModel() {
 /* CheckableItem[] m = {
      new CheckableItem("aaa", false),
      new CheckableItem("bb", false),
      new CheckableItem("111", false),
      new CheckableItem("33333", false),
      new CheckableItem("2222", false),
      new CheckableItem("c", false)
  };*/
	  Set<Object> h = new HashSet<>();
	    IntStream.range(0, table.getRowCount())
	        .mapToObj(i -> table.getValueAt(i, table.getColumnCount()-1))
	        .forEach(h::add);

	    CheckableItem[] m = h.stream()
	        .map(obj -> new CheckableItem(obj.toString(), true))
	        .toArray(CheckableItem[]::new);

	    return new DefaultComboBoxModel<>(m);
	}



private static void createAndShowGui() {
  JFrame frame = new JFrame("@title@");
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  //frame.getContentPane().add(new ExampleComboBox());
  frame.pack();
  frame.setLocationRelativeTo(null);
  frame.setVisible(true);
}
}

class CheckableItem {
private final String text;
private boolean selected;

protected CheckableItem(String text, boolean selected) {
  this.text = text;
  this.selected = selected;
}

public boolean isSelected() {
  return selected;
}

public void setSelected(boolean selected) {
  this.selected = selected;
}

@Override
public String toString() {
  return text;
}
}

class CheckedComboBox<E extends CheckableItem> extends JComboBox<E> {
protected boolean keepOpen;
private final JPanel panel = new JPanel(new BorderLayout());



  @Override
public void setPopupVisible(boolean v) {
  if (keepOpen) {
    keepOpen = false;
  } else {
    super.setPopupVisible(v);
  }
}
 

protected CheckedComboBox(ComboBoxModel<E> model) {
	
  super(model);
}

@Override
public Dimension getPreferredSize() {
  return new Dimension(200, 20);
}

@Override
public void updateUI() {
  setRenderer(null);
  super.updateUI();
  Accessible a = getAccessibleContext().getAccessibleChild(0);
  if (a instanceof ComboPopup) {
    ((ComboPopup) a).getList().addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        JList<?> list = (JList<?>) e.getComponent();
        if (SwingUtilities.isLeftMouseButton(e)) {
          keepOpen = true;
          updateItem(list.locationToIndex(e.getPoint()));
        }
      }
    });
  }

  DefaultListCellRenderer renderer = new DefaultListCellRenderer();
  JCheckBox check = new JCheckBox();
  check.setOpaque(false);
  setRenderer((list, value, index, isSelected, cellHasFocus) -> {
    panel.removeAll();
    Component c = renderer.getListCellRendererComponent(
        list, value, index, isSelected, cellHasFocus);
    if (index < 0) {
    	String txt = getCheckedItemString(list.getModel()); 
      //String txt = "Visibility";
      JLabel l = (JLabel) c;
      l.setText(txt.isEmpty() ? " " : txt);
      l.setOpaque(false);
      l.setForeground(list.getForeground());
      panel.setOpaque(false);
    } else {
      check.setSelected(value.isSelected());
      panel.add(check, BorderLayout.WEST);
      panel.setOpaque(true);
      panel.setBackground(c.getBackground());
    }
    panel.add(c);
    return panel;
  });
  initActionMap();
}

protected void initActionMap() {
  KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
  getInputMap(WHEN_FOCUSED).put(ks, "checkbox-select");
  getActionMap().put("checkbox-select", new AbstractAction() {
    @Override
    public void actionPerformed(ActionEvent e) {
      Accessible a = getAccessibleContext().getAccessibleChild(0);
      if (a instanceof ComboPopup) {
        updateItem(((ComboPopup) a).getList().getSelectedIndex());
      }
    }
  });
}

protected void updateItem(int index) {
  if (isPopupVisible() && index >= 0) {
    E item = getItemAt(index);
    item.setSelected(!item.isSelected());
    setSelectedIndex(-1);
    setSelectedItem(item);
    System.out.println("combobox action");

  }
}



protected static <E extends CheckableItem> String getCheckedItemString(ListModel<E> model) {
  return IntStream.range(0, model.getSize())
      .mapToObj(model::getElementAt)
      .filter(CheckableItem::isSelected)
      .map(Objects::toString)
      .sorted()
      .collect(Collectors.joining(", "));
}
    
    
    
    
}




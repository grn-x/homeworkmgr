package de.grnx.homeworkmgr.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.channels.SelectableChannel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Popup extends JFrame {
    private JTextArea textArea;

    public Popup(String [][] table,LocalDateTime now) {
    	
    	//LocalDateTime now = LocalDateTime.now();
    	
    	Object[] objArr = TimetableSubjectMapper.Mapper(table, now);
    	String subject = (String) objArr[0];
    	int selectedIndex = (int) objArr[1]-1;
    	String[] todaysSubjects = (String[]) objArr[2];
    			System.out.println(Arrays.toString(todaysSubjects));
    	
    	/*todaysSubjects = Arrays.stream(todaysSubjects)
                .filter(Objects::nonNull)
                .filter(str -> !str.isBlank())
                .skip(1)  // Skip the first element
                .toArray(String[]::new);*///schade um diesen schönen stream. für den korrekten index muss ich aber wissen welche elemente mit welchem index ich entferne
    			
    	
    	ArrayList<String> strList = new ArrayList<String>();
    	for(int i =1; i < todaysSubjects.length;i++) {
    		if(!(todaysSubjects[i]==null||todaysSubjects[i].isBlank())){
    			strList.add(todaysSubjects[i]);
    			System.out.println("\""+todaysSubjects[i]+"\"");
    		}else {
    			if(i<=selectedIndex) {
    				selectedIndex--;
    			}
    		}
    	}
    	todaysSubjects=strList.toArray(new String[strList.size()]);
    	System.out.println("updated: " + Arrays.toString(todaysSubjects));
    	
    	
    	
        // Set the frame properties
    	//setUndecorated(true);
        setTitle(Constants.windowTitle + subject);
        setSize(2*512, 2*128);
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a JTextArea
        textArea = new JTextArea();
        textArea.setLineWrap(true); // Enable word wrapping
        textArea.setWrapStyleWord(true); // Wrap at word boundaries
        JScrollPane scrollPane = new JScrollPane(textArea);

        
        //MouseInfo.getPointerInfo().getLocation();
        
        
        
        JPanel p = new JPanel();
        JPanel westPanel = new JPanel();//for dropdown combobox and stuff
        JPanel eastPanel = new JPanel();//for table viewer and settings
        //JTextArea aSubject = new JTextArea("text");
        JRadioButton comboboxButton = new JRadioButton ("", true);
        JRadioButton textareaButton = new JRadioButton ("", false);
        ButtonGroup group = new ButtonGroup();
        group.add(comboboxButton);
        group.add(textareaButton);
        JTextArea aSubject = new JTextArea("Enter Subject Name");
      //jScrollPane1.setViewportView(textAreaDescription);
        aSubject.addFocusListener(new FocusListener() {
          public void focusGained(FocusEvent e) {
        	  aSubject.setText("");
        	  textareaButton.setSelected(true);
          }

          public void focusLost(FocusEvent e) {
        	if(aSubject.getText()==null||aSubject.getText().isBlank())
        	  aSubject.setText("Enter Subject Name");
          	}
      });
        
        
        JComboBox<String> comboBox = new JComboBox<>(todaysSubjects);
			comboBox.addFocusListener(new FocusListener(){
				public void focusGained(FocusEvent e) {
					comboboxButton.setSelected(true);
				}
			
				public void focusLost(FocusEvent e) {
				}
			});
        // Set the selected item to the element at index i
        comboBox.setSelectedIndex(selectedIndex);
        westPanel.setLayout(new FlowLayout());
        westPanel.add(new JLabel("Homework in "));
        westPanel.add(comboboxButton);
        westPanel.add(comboBox);
        westPanel.add(Box.createHorizontalStrut(36));
        westPanel.add(textareaButton);
        westPanel.add(aSubject);
        //replace both buttons with jtabbed panes maybe
        JButton homeworkButton = new JButton("View Homework");
        JButton tableButton = new JButton("Edit Schedule");
        
        homeworkButton.addActionListener(new ActionListener(){ 
      	  public void actionPerformed(ActionEvent e){ 
      		HomeworkViewer v = new HomeworkViewer(NoteHandler.getNotesStringArray());
      		  
      	  }});
        tableButton.addActionListener(new ActionListener(){ 
        	  public void actionPerformed(ActionEvent e){ 
        		  new TimetableViewer(table);
        		  //new TimetableViewer(NoteHandler.getTable());
        	  }});
        
        
        eastPanel.setLayout(new GridLayout(1,2));
        eastPanel.add(homeworkButton);
        eastPanel.add(tableButton);
        
        p.setLayout(new BorderLayout());
        p.add(westPanel, BorderLayout.WEST);
        p.add(eastPanel, BorderLayout.EAST);

        
        // Add components to the frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(p, BorderLayout.NORTH);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    window.dispose();
                }
                
                if(comboboxButton.isSelected()) {
                	if(textArea==null||textArea.getText().isBlank())return;
                	NoteHandler.saveNote(comboBox.getSelectedItem().toString(), now, textArea.getText());

                }else {
                	if(aSubject.getText()==null||aSubject.getText().isBlank()) {
                		
                		try {
                			throw new NullPointerException();
                		}catch (Exception e1) {
                			e1.printStackTrace();
                			ErrView.showStackTraceErrorDialog(null, Constants.nullPtrA +comboBox.getSelectedItem().toString(), e1);
                        	if(textArea==null||textArea.getText().isBlank())return;
                			NoteHandler.saveNote(comboBox.getSelectedItem().toString(), now, textArea.getText());
						}
                	}else {
                    	if(textArea==null||textArea.getText().isBlank())return;
                		NoteHandler.saveNote(aSubject.getText(), now, textArea.getText());

                	}
                }
                		//saveNote();
                /*Thread backgroundThread = new Thread(new Runnable() {
                    public void run() {
                        // Your background task logic here
                        System.out.println("Background task executed.");
                    }
                });
                backgroundThread.start();*/            
             }
        });
        
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent windowEvent) {
                textArea.requestFocus();
            }
        });
        
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            	
            	if(e.getKeyCode()==KeyEvent.VK_ENTER) {
            		if(e.isShiftDown()||e.isControlDown()||e.isAltDown()||e.isMetaDown()) {
            			e.consume();
            			textArea.setText(textArea.getText()+"\n");
            			
            		}else {
            			e.consume();
            			if(comboboxButton.isSelected()) {
                        	NoteHandler.saveNote(comboBox.getSelectedItem().toString(), now, textArea.getText());
                        	dispose();

                        }else {
                        	if(aSubject.getText()==null||aSubject.getText().isBlank()) {
                        		try {
                        			throw new NullPointerException();
                        		}catch (Exception e1) {
                        			e1.printStackTrace();
                        			ErrView.showStackTraceErrorDialog(null, Constants.nullPtrA +comboBox.getSelectedItem().toString(), e1);
                        			NoteHandler.saveNote(comboBox.getSelectedItem().toString(), now, textArea.getText());
                                	dispose();


        						}
                        	}else {
                        		NoteHandler.saveNote(aSubject.getText(), now, textArea.getText());
                            	dispose();


                        	}
                        }
            			
            		}
            	}
            	
            }
        });
        
//        requestFocus();
//        textArea.requestFocus();
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//            	textArea.requestFocus();
//            }
//          });
        setVisible(true);
        //pack();
        setLocationRelativeTo(null);
    }


    public static void displayNotification(JComponent parent, String message, int mSecDuration) {
    	if(!(mSecDuration >= 1)) {
    		mSecDuration=500;
    	}
    	final int duration = mSecDuration;
    	
        JLabel label = new JLabel(message);
        label.setOpaque(true);
        label.setBackground(new Color(0, 0, 0, 190));
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "", Dialog.ModalityType.MODELESS);
        dialog.setUndecorated(true);
        dialog.getContentPane().add(label, BorderLayout.CENTER);
        dialog.pack();
        Point parentLocation = parent.getLocationOnScreen();
        int x = parentLocation.x + (parent.getWidth() - dialog.getWidth()) / 2;
        int y = parentLocation.y + (parent.getHeight() - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);

        new Thread(() -> {
            try {
                Thread.sleep(duration); // Display duration in milliseconds
                dialog.dispose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        dialog.setVisible(true);
    }



}

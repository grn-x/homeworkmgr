////package de.grnx.homeworkmgr.main;
////
////import java.io.File;
////import java.nio.file.Paths;
////
////import javax.swing.JPanel;
////
////public class Settings {
////	public static long delay =0;//this gets subtracted from the time passed to the timetablesubjectmapper method in order to balance homework given after the lesson ended//should also work with negative values but why would you want to do that
////	public static File dataLocation = Paths.get(Main.FOLDER.toString()+"\\data.dat").toFile();
////	public static File tableLocation = Paths.get(Main.FOLDER.toString()+"\\table.ser").toFile();
////	public Settings() {
////		JPanel p = new JPanel();
////		
////		//default location string
////		
////		//where / what map to load 
////		
////		//"delay" in minutes if lesson
////	}
////}
//





//package de.grnx.homeworkmgr.main;
//
//import java.io.*;
//import java.nio.file.*;
//import java.sql.Time;
//import java.util.Date;
//import java.util.Properties;
//import javax.swing.*;
//import javax.swing.filechooser.FileSystemView;
//
//
//public class Settings {
//    private static long delay = 5; // this gets subtracted from the time passed to the timetablesubjectmapper method
//    private static File dataLocation = new File(Main.FOLDER.toString()+ "\\data.dat");
//    private static File tableLocation = new File(Main.FOLDER+"\\table.ser");
//    
//    public Settings() {
//        // Set default values
//        /*delay = 0;
//        dataLocation = Paths.get(Main.FOLDER.toString(), "data.dat").toFile();
//        tableLocation = Paths.get(Main.FOLDER.toString(), "table.ser").toFile();*/
//    	
//    	//TODO store array with all possible statusses
//    	
//        //System.out.println(new File(Main.propertiesPath).exists());
//        //System.out.println(!new File(Main.propertiesPath).exists());
//        //System.out.println((new File(Main.propertiesPath).exists()||Files.exists(Paths.get(Main.propertiesPath), null)));
//        if(!(Main.CONFIG).exists()) {
//        	try {
//            	//new File(Main.propertiesPath).createNewFile();
//
//        	Properties props = new Properties();
//		     
//		     props.setProperty("delay", "5");
//		     props.setProperty("dataLocation", dataLocation.getAbsolutePath());
//		     props.setProperty("tableLocation", tableLocation.getAbsolutePath());
//		     FileWriter writer = new FileWriter(Main.CONFIG);
//		     props.store(writer, "DO NOT MODIFY\nConfiguration-File for Homework Manager\n");
//	            //FileOutputStream outputStream = new FileOutputStream(Main.propertiesPath);
//		     //props.store(outputStream, null);
//		     writer.close();
//		     
//		     if(!tableLocation.exists()) {
//		    	 Serializer.serializeObject(tableLocation.getAbsolutePath(), Timetable.timetableARR);
//		     }
//		     
//		     
//        	}catch (Exception e) {
//        		e.printStackTrace();
//        		ErrView.showStackTraceErrorDialog(null, "Config Setup FileWriting crashed, exiting", e);
//        		System.exit(-1);//fuck
//
//        	}
//        }else {
//            FileReader reader;
//			try {
//				reader = new FileReader(Main.CONFIG);
//				Properties props = new Properties();
//	            props.load(reader);
//
//	            try {
//	            delay = Integer.valueOf(props.getProperty("delay"));
//	            }catch (Exception e1) {
//	            	e1.printStackTrace();
//	            	delay=5;
//	            	ErrView.showStackTraceErrorDialog(null, "Number format Exception when parsing Delay Value from config file. Delay will be set to 5. "+Main.CONFIG, e1);
//	            }
//	            dataLocation=new File(props.getProperty("dataLocation"));
//	            tableLocation = new File(props.getProperty("tableLocation"));
//	            
//	            System.out.println(delay);
//	            System.out.println(dataLocation);
//	            System.out.println(tableLocation);
//	            
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				ErrView.showStackTraceErrorDialog(null, "Error when reading Config File. "+Main.CONFIG, e);
//			}
//        	
//        }
////        try {
////        FileReader reader = new FileReader(Main.propertiesPath);
////		    Properties props = new Properties();
////		    props.load(reader);		    
////		    setDelay(Integer.parseInt(props.get("delay").toString()));
////		    setDataLocation(new File(props.get("dataLocation").toString()));
////		    setTableLocation(new File(props.get("tableLocation").toString())); 
////		    
////	    reader.close();
////        }catch (Exception e) {
////        	e.printStackTrace();
////    		ErrView.showStackTraceErrorDialog(null, "Config Setup FileReading crashed, exiting", e);
////    		System.exit(-1);//fuck
////    		
////        }
//    }
//
//    // Getter and Setter for delay
//    public static long getDelay() {
//        return delay;
//    }
//
//    public static void setDelay(long delayPar) {
//        delay = delayPar;
//        try {
//            FileReader reader = new FileReader(Main.CONFIG);
//            Properties props = new Properties();
//            props.load(reader);
//		     
//		     props.setProperty("delay", String.valueOf(delay));
//		     FileWriter writer = new FileWriter(Main.CONFIG);
//		     props.store(writer, "DO NOT MODIFY\nConfiguration-File for Homework Manager\n");
//		     writer.close();
//        	}catch (Exception e) {
//        		e.printStackTrace();
//        		ErrView.showStackTraceErrorDialog(null, "Config setting delay crashed", e);
//        		
//
//        	}
//    }
//
//    // Getter and Setter for dataLocation
//    public static File getDataLocation() {
//        return dataLocation;
//    }
//
//    public void setDataLocation(File dataLocationPar) {
//        dataLocation = dataLocationPar; 
//        try {
//            FileReader reader = new FileReader(Main.CONFIG);
//            Properties props = new Properties();
//            props.load(reader);
//		     
//		     props.setProperty("dataLocation", dataLocation.getAbsolutePath());
//		     FileWriter writer = new FileWriter(Main.CONFIG);
//		     props.store(writer, "DO NOT MODIFY\nConfiguration-File for Homework Manager\n");
//		     writer.close();
//        	}catch (Exception e) {
//        		e.printStackTrace();
//        		ErrView.showStackTraceErrorDialog(null, "Config setting dataLocation crashed", e);
//        		
//
//        	}
//    }
//
//    // Getter and Setter for tableLocation
//    public static File getTableLocation() {
//        return tableLocation;
//    }
//
//    public static void setTableLocation(File tableLocationPar) {
//        tableLocation = tableLocationPar;
//        try {
//            FileReader reader = new FileReader(Main.CONFIG);
//            Properties props = new Properties();
//            props.load(reader);
//		     
//		     props.setProperty("tableLocation", tableLocation.getAbsolutePath());
//		     FileWriter writer = new FileWriter(Main.CONFIG);
//		     props.store(writer, "DO NOT MODIFY\nConfiguration-File for Homework Manager\n");
//		     writer.close();
//        	}catch (Exception e) {
//        		e.printStackTrace();
//        		ErrView.showStackTraceErrorDialog(null, "Config setting tableLocation crashed", e);
//        		
//
//        	}
//    }
//
//}




package de.grnx.homeworkmgr.main;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.*;

public class Settings {
    private static long delay = 5;
    private static File dataLocation = new File(Main.FOLDER.toString() + "\\data.dat");
    private static File tableLocation = new File(Main.FOLDER + "\\table.ser");
    private static Map<String, String> AdditionalSettings = new HashMap<>();

    
    public Settings() {
        if (!(Main.CONFIG).exists()) {
            try {
                Properties props = new Properties();
                props.setProperty("delay", "5");
                props.setProperty("dataLocation", dataLocation.getAbsolutePath());
                props.setProperty("tableLocation", tableLocation.getAbsolutePath());
                saveAdditionalSettings(props);
                FileWriter writer = new FileWriter(Main.CONFIG);
                props.store(writer, "DO NOT MODIFY\nConfiguration-File for Homework Manager\n");
                writer.close();
                if (!tableLocation.exists()) {
                    Serializer.serializeObject(tableLocation.getAbsolutePath(), Timetable.timetableARR);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrView.showStackTraceErrorDialog(null, "Config Setup FileWriting crashed, exiting", e);
                System.exit(-1);
            }
        } else {
            try {
                FileReader reader = new FileReader(Main.CONFIG);
                Properties props = new Properties();
                props.load(reader);
                try {
                    delay = Integer.valueOf(props.getProperty("delay"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                    delay = 5;
                    ErrView.showStackTraceErrorDialog(null, "Number format Exception when parsing Delay Value from config file. Delay will be set to 5. " + Main.CONFIG, e1);
                }
                dataLocation = new File(props.getProperty("dataLocation"));
                tableLocation = new File(props.getProperty("tableLocation"));
                loadAdditionalSettings(props);
                System.out.println(delay);
                System.out.println(dataLocation);
                System.out.println(tableLocation);
                System.out.println(AdditionalSettings);
            } catch (Exception e) {
                e.printStackTrace();
                ErrView.showStackTraceErrorDialog(null, "Error when reading Config File. " + Main.CONFIG, e);
            }
        }
    }

    private static void saveAdditionalSettings(Properties props) {
        for (Map.Entry<String, String> entry : AdditionalSettings.entrySet()) {
            props.setProperty(entry.getKey(), entry.getValue());
        }
    }

    private void loadAdditionalSettings(Properties props) {
        for (String key : props.stringPropertyNames()) {
            if (!key.equals("delay") && !key.equals("dataLocation") && !key.equals("tableLocation")) {
                AdditionalSettings.put(key, props.getProperty(key));
            }
        }
    }

    public static long getDelay() {
        return delay;
    }

    public static void setDelay(long delayPar) {
        delay = delayPar;
        try {
            FileReader reader = new FileReader(Main.CONFIG);
            Properties props = new Properties();
            props.load(reader);
            props.setProperty("delay", String.valueOf(delay));
            saveAdditionalSettings(props);
            FileWriter writer = new FileWriter(Main.CONFIG);
            props.store(writer, "DO NOT MODIFY\nConfiguration-File for Homework Manager\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            ErrView.showStackTraceErrorDialog(null, "Config setting delay crashed", e);
        }
    }

    public static File getDataLocation() {
        return dataLocation;
    }

    public static void setDataLocation(File dataLocationPar) {
        dataLocation = dataLocationPar;
        try {
            FileReader reader = new FileReader(Main.CONFIG);
            Properties props = new Properties();
            props.load(reader);
            props.setProperty("dataLocation", dataLocation.getAbsolutePath());
            saveAdditionalSettings(props);
            FileWriter writer = new FileWriter(Main.CONFIG);
            props.store(writer, "DO NOT MODIFY\nConfiguration-File for Homework Manager\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            ErrView.showStackTraceErrorDialog(null, "Config setting dataLocation crashed", e);
        }
    }

    public static File getTableLocation() {
        return tableLocation;
    }

    public static void setTableLocation(File tableLocationPar) {
        tableLocation = tableLocationPar;
        try {
            FileReader reader = new FileReader(Main.CONFIG);
            Properties props = new Properties();
            props.load(reader);
            props.setProperty("tableLocation", tableLocation.getAbsolutePath());
            saveAdditionalSettings(props);
            FileWriter writer = new FileWriter(Main.CONFIG);
            props.store(writer, "DO NOT MODIFY\nConfiguration-File for Homework Manager\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            ErrView.showStackTraceErrorDialog(null, "Config setting tableLocation crashed", e);
        }
    }

    public Map<String, String> getAdditionalSettings() {
        return AdditionalSettings;
    }

    public static void setAdditionalSettings(Map<String, String> settings) {
        AdditionalSettings = settings;
        try {
            FileReader reader = new FileReader(Main.CONFIG);
            Properties props = new Properties();
            props.load(reader);
            saveAdditionalSettings(props);
            FileWriter writer = new FileWriter(Main.CONFIG);
            props.store(writer, "DO NOT MODIFY\nConfiguration-File for Homework Manager\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            ErrView.showStackTraceErrorDialog(null, "Config setting AdditionalSettings crashed", e);
        }
    }
    
    public static void addAdditionalSettings(String key, String value) {
        AdditionalSettings.put(key, value);
        try {
            FileReader reader = new FileReader(Main.CONFIG);
            Properties props = new Properties();
            props.load(reader);
            saveAdditionalSettings(props);
            FileWriter writer = new FileWriter(Main.CONFIG);
            props.store(writer, "DO NOT MODIFY\nConfiguration-File for Homework Manager\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            ErrView.showStackTraceErrorDialog(null, "Config setting AdditionalSettings crashed", e);
        }
    
    }

    // Method to export a JComponent for the UI
    public static JComponent getSettingsComponent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Delay:"));
        JTextField delayField = new JTextField(String.valueOf(getDelay()));
        delayField.addActionListener(e -> {
            setDelay(Long.parseLong(delayField.getText()));
            Popup.displayNotification(panel, "Saved", 500);
        });
        panel.add(delayField);

        panel.add(new JLabel("Data Location:"));
        JTextField dataLocationField = new JTextField(getDataLocation().getAbsolutePath());
        dataLocationField.addActionListener(e -> {
            setDataLocation(new File(dataLocationField.getText()));
            Popup.displayNotification(panel, "Saved", 500);
        });
        panel.add(dataLocationField);

        panel.add(new JLabel("Table Location:"));
        JTextField tableLocationField = new JTextField(getTableLocation().getAbsolutePath());
        tableLocationField.addActionListener(e -> {
            setTableLocation(new File(tableLocationField.getText()));
            Popup.displayNotification(panel, "Saved", 500);
        });
        panel.add(tableLocationField);

        for (Map.Entry<String, String> entry : AdditionalSettings.entrySet()) {
            panel.add(new JLabel(entry.getKey() + ":"));
            JTextField extraField = new JTextField(entry.getValue());
            extraField.addActionListener(e -> {
                Map<String, String> newAdditionalSettings = new HashMap<>(AdditionalSettings);
                newAdditionalSettings.put(entry.getKey(), extraField.getText());
                setAdditionalSettings(newAdditionalSettings);
                Popup.displayNotification(panel, "Saved", 500);
            });
            panel.add(extraField);
        }
        return panel;
    }

}


package de.grnx.homeworkmgr.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Main {
	public static File FOLDER;
	public static File CONFIG;
	public static String propertiesPath;
	public static volatile Settings SETTINGS;

	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	//public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


	public static void main(String[] args) {

		String dataFolder = System.getenv("APPDATA");
		File folder = new File(dataFolder + "\\.homeworkMGR");
		File config = Paths.get(folder + "\\config.properties").toFile();

		// System.out.println(folder);
		// System.out.println(config);

		if (!folder.exists()) {
			folder.mkdir();
		}

		FOLDER = folder;
		CONFIG = config;

		SETTINGS = new Settings();

		/*
		 * Serializer.serializeData(new EnSt(),
		 * "C:\\Users\\bened\\AppData\\Roaming\\.newFolder\\Entries.ser");
		 * System.out.println(Serializer.deserializeData(
		 * "C:\\Users\\bened\\AppData\\Roaming\\.newFolder\\Entries.ser").toString());
		 */

//		   ArrayList<EnSt> arrList = new ArrayList<EnSt>();
//		   arrList.add(new EnSt());
//		   arrList.add(new EnSt("lol", LocalDateTime.now(),"lmao"));

		// SETTINGS.setDataLocation(new File(System.getenv("APPDATA")));
//SETTINGS.setDelay(-45);

		// SETTINGS.setTableLocation(new File(System.getenv("APPDATA")));

//	        int year = 2023;
//	        Month month = Month.OCTOBER; // You can use Month enum or an integer (1 for January, 2 for February, etc.)
//	        int dayOfMonth = 8;
//	        int hour = 14;
//	        int minute = 30;
		//
//	        LocalDateTime customLocalDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);

		int year = 1989;
		Month month = Month.APRIL;
		int weekOfMonth = 1; // Week of the month (e.g., 1st, 2nd, 3rd, etc.)
		DayOfWeek dayOfWeek = DayOfWeek.SUNDAY; // Day of the week
		int hour = 19; // Hour of the day
		int minute = 33; // Minute of the hour

		// Calculate the date based on week and day of the week
		LocalDateTime firstDayOfMonth = LocalDateTime.of(year, month, 1, hour, minute);
		LocalDateTime customLocalDateTime = firstDayOfMonth.with(TemporalAdjusters.firstInMonth(dayOfWeek));
		customLocalDateTime = customLocalDateTime.plusWeeks(weekOfMonth - 1);

		System.out.println("Custom LocalDateTime: " + customLocalDateTime);

		LocalDateTime today = LocalDateTime.now();

		System.out.println("Today LocalDateTime: " + today);

		LocalDateTime randomDateTime = LocalDateTime.ofEpochSecond(
				ThreadLocalRandom.current().nextLong(0, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 0,
				ZoneOffset.UTC);

		System.out.println("Random LocalDateTime: " + randomDateTime);//1989-04-08T19:33:34

		// DayOfWeek dayOfWeek = today.getDayOfWeek();
		// dayOfWeek.getValue();

		System.out.println("now notes");
		//System.out.println(NoteHandler.getNotesString());
		System.out.println("notes done");
		
		Settings.addAdditionalSettings("lol", "value");
		
        JFrame frame = new JFrame("Settings Frame");
        JComponent com = Settings.getSettingsComponent();
        frame.add(com);
        frame.pack(); // Packs the components in the frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Closes the application when the frame is closed
        frame.setVisible(true); // Makes the frame visible
   
		//new Popup(NoteHandler.getTable(),randomDateTime);

		//HomeworkViewer v = new HomeworkViewer(NoteHandler.getNotesStringArray());

		// Popup popup = new Popup();

		// new TimetableViewer(Timetable.timetableARR);

//		File openFile = FileHandler.openFile(null, null);
//        if (openFile != null) {
//            // Process the opened file
//            System.out.println("Opened file: " + openFile.getAbsolutePath());
//        }
	}

//	public boolean isDateParsable(String s) {
//		    SimpleDateFormat sdf = Main.formatter;
//		    sdf.setLenient(false);
//		    return sdf.parse(s, new ParsePosition(0)) != null;
//		
//		 }#

	public static boolean isDateParsable(String s) {
		System.out.println("\t\t" + s);

		try {
			//LocalDateTime ldt = s.format(Main.formatter).toString();
			LocalDateTime ldt = java.time.LocalDateTime.parse(s, Main.formatter);

			System.out.println(s +"true");
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		try {
			formatter.parse(s);
			System.out.println(true);
			return true;
		} catch (DateTimeParseException e1) {
			System.out.println(false);
			return false;
		}
		}
		
		
	}
	
	

}
/*
 * LocalDate today = LocalDate.now();
 * 
 * DayOfWeek dayOfWeek = today.getDayOfWeek();
 * 
 * System.out.println("Day of the Week :: " + dayOfWeek);
 * System.out.println("Day of the Week - in Number :: " + dayOfWeek.getValue());
 * System.out.println("Day of the Week - Formatted FULL :: " +
 * dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()));
 * System.out.println("Day of the Week - Formatted SHORT :: " +
 * dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
 */
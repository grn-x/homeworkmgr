package de.grnx.homeworkmgr.main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class NoteHandler {
	private String fileLocation;
	private LocalDateTime time;
	private String subject;
	private String note;
	private static ArrayList<EnSt> data;
	
	public static void saveNote(String subject, LocalDateTime time, String note) {
		data= new ArrayList<EnSt>();
		
		if(Settings.getDataLocation().exists()) {
		//data=Serializer.deserializeDataArray(Settings.getDataLocation().getAbsolutePath());
		data=(ArrayList<EnSt>) Serializer.deserializeObject(Settings.getDataLocation().getAbsolutePath());

		}
		
		EnSt entry = new EnSt(subject,time,note);
		data.add(entry);
		Serializer.serializeObject(Settings.getDataLocation().toString(),data);
		//Serializer.serializeDataArray(data,Settings.getDataLocation().toString());

		
	}
	
	public static ArrayList<EnSt> getNotes(){
		return (ArrayList<EnSt>) Serializer.deserializeObject(Settings.getDataLocation().getAbsolutePath());
	}
	
	public static ArrayList<Object[]> getNotesArray(){
		//return ((ArrayList<EnSt>) Serializer.deserializeObject(Settings.getDataLocation().getAbsolutePath())).stream().map(EnSt::toArray).collect(ArrayList<Object[]>::new, ArrayList::add, ArrayList::addAll)
		return ((ArrayList<EnSt>) Serializer.deserializeObject(Settings.getDataLocation().getAbsolutePath()))
			    .stream()
			    .map(EnSt::toArray)
			    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	public static ArrayList<Object[]> getNotesStringArray(){
		//return ((ArrayList<EnSt>) Serializer.deserializeObject(Settings.getDataLocation().getAbsolutePath())).stream().map(EnSt::toArray).collect(ArrayList<Object[]>::new, ArrayList::add, ArrayList::addAll)
		return ((ArrayList<EnSt>) Serializer.deserializeObject(Settings.getDataLocation().getAbsolutePath()))
			    .stream()
			    .map(EnSt::getStringArray)
			    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	public static String getNotesString(){
		return ((ArrayList<EnSt>) Serializer.deserializeObject(Settings.getDataLocation().getAbsolutePath()))
			    .stream()
			    .map(EnSt::toArray)
			    .map(Arrays::toString) // Convert arrays to string
			    .collect(Collectors.joining("\n"));
	}
	
	public static String[][] getTable() {
		String[][] t;
		if(Settings.getTableLocation().exists()) {
			try {
				t=(String[][])Serializer.deserializeObject(Settings.getTableLocation().getAbsolutePath());
				return t;

			}catch (Exception e) {
				e.printStackTrace();
				ErrView.showStackTraceErrorDialog(null, "Error when Deserializing and Parsing schedule table to 2d StringArray\nReturning default Timetable", e);
				return Timetable.timetableARR;
			}
		}else{
			
			Exception e1 = new Exception(Constants.tablePath,
					new Throwable("Table not found. Path or File invalid: " + Settings.getTableLocation().getAbsolutePath() +"Default table is being used: \n\n" + Arrays.deepToString(Timetable.timetableARR)));
			ErrView.showStackTraceErrorDialog(null, "Table Path invalid, using default table", e1);
			return Timetable.timetableARR;

		}
	}
}
//Serializer.serializeDataArray(arrList, "C:\\Users\\user\\AppData\\Roaming\\.newFolder\\Entry.dat");
//Serializer.deserializeDataArray("C:\\Users\\user\\AppData\\Roaming\\.newFolder\\Entry.dat").stream().map(EnSt::toString).forEach(System.out::println);
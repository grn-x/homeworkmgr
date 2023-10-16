package de.grnx.homeworkmgr.main;
//data class
public class Timetable {
	/*public static String timetableCSV = "Hours,Monday,Tuesday,Wednesday,Thursday,Friday\r\n"
			+ "08:45,Physik,Englisch,Englisch,Informatik,Politik und Gesellschaft\r\n"
			+ "09:45,Physik,Englisch,Latein,Wirtschaft und Recht,Religion\r\n"
			+ "10:30,Geographie,Informatik,Geographie,Wirtschaft und Recht,Religion\r\n"
			+ "11:30,Mathe,Deutsch,Mathe,Latein,Geschichte\r\n"
			+ "12:15,Sport,Deutsch,Mathe,Latein,Chemie\r\n"
			+ "13:15,Sport,,Deutsch,Politik und Gesellschaft,Chemie\r\n"
			+ "14:00,,Kunst,,,\r\n"
			+ "14:45,,Kunst,,P-Seminar,\r\n"
			+ "15:30,,Physik,,P-Seminar,\r\n"
			+ "16:15,,Physik,,,";*/
	
	static String[][] timetableARR = {
		    {"Hours", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" },
		    {"08:45", "Physik", "Englisch", "Englisch", "Informatik", "Politik und Gesellschaft"},
		    {"09:45", "Physik", "Englisch", "Latein", "Wirtschaft und Recht", "Religion"},
		    {"10:30", "Geographie", "Informatik", "Geographie", "Wirtschaft und Recht", "Religion"},
		    {"11:30", "Mathe", "Deutsch", "Mathe", "Latein", "Geschichte"},
		    {"12:15", "Sport", "Deutsch", "Mathe", "Latein", "Chemie"},
		    {"13:15", "Sport", "", "Deutsch", "Politik und Gesellschaft", "Chemie"},
		    {"14:00", "", "Kunst", "", "", ""},
		    {"14:45", "", "Kunst", "", "P-Seminar", ""},
		    {"15:30", "", "Physik", "", "P-Seminar", ""},
		    {"16:15", "", "Physik", "", "", ""}
		    };

}

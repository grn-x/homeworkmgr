package de.grnx.homeworkmgr.main;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.stream.IntStream;

public class TimetableSubjectMapper {// potentially unsafe method

	/*public static void main(String[] args) {

//        int year = 2023;
//        Month month = Month.OCTOBER; // You can use Month enum or an integer (1 for January, 2 for February, etc.)
//        int dayOfMonth = 8;
//        int hour = 14;
//        int minute = 30;
//
//        LocalDateTime customLocalDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);

		int year = 2023;
		Month month = Month.OCTOBER;
		int weekOfMonth = 2; // Week of the month (e.g., 1st, 2nd, 3rd, etc.)
		DayOfWeek dayOfWeek = DayOfWeek.WEDNESDAY; // Day of the week
		int hour = 0; // Hour of the day
		int minute = 49; // Minute of the hour

		// Calculate the date based on week and day of the week
		LocalDateTime firstDayOfMonth = LocalDateTime.of(year, month, 1, hour, minute);
		LocalDateTime customLocalDateTime = firstDayOfMonth.with(TemporalAdjusters.firstInMonth(dayOfWeek));
		customLocalDateTime = customLocalDateTime.plusWeeks(weekOfMonth - 1);

		System.out.println("Custom LocalDateTime: " + customLocalDateTime);

//	    
//	    System.out.println("HEader: " +Arrays.toString(header));
//	    System.out.println("table0: " +Arrays.toString(table[0]));
//
//	    System.out.println(Arrays.toString(Arrays.copyOfRange(header, 0, table[0].length)));

		System.out.println(Arrays.deepToString(Mapper(Timetable.timetableARR, customLocalDateTime)));
//		System.out.println(Mapper(Timetable.timetableARR,LocalDateTime.now()));
//    	System.out.println(		    dayOfWeek.getValue());
//	    System.out.println(index);
//	    System.out.println(table[index][0]);
//	    System.out.println(table[index][dayOfWeek.getValue()-3]);
//    	return table[index][table[index].length-1];

	}*/

	public static Object[] Mapper(String[][] table, LocalDateTime timePar) {
		Object[] returnTypes = new Object[5];
		
		LocalDateTime time = timePar.minusMinutes(Settings.getDelay());
//		System.out.println(time);
//		System.out.println(timePar);
		DayOfWeek dayOfWeek = time.getDayOfWeek();
		dayOfWeek.getValue();

		String[] header = new String[] { "Hours", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
				"Sunday" };

		if (Arrays.equals(Arrays.copyOfRange(header, 0, table[0].length), table[0])) {

		} else {
			Exception e = new Exception(Constants.exceptionMessage, new Throwable("Current table header: \t"
					+ Arrays.toString(table[0]) + "\n\nExpected table Header: \t" + Arrays.toString(header)));

			ErrView.showStackTraceErrorDialog(null, "TimetableSubjectMapping failed, Timetable Headers unparsable", e);
		}

		String[] hours = new String[table.length - 1];

		// assuming the header is correctly setup
		for (int i = 0; i < table.length - 1; i++) {
			hours[i] = table[i+1][0];
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		int index = 0;

		for (String str : hours) {
			try {
				index++;
				LocalTime tableTime = LocalTime.parse(str, formatter);
				if (time.toLocalTime().isBefore(tableTime)) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ErrView.showStackTraceErrorDialog(null,
						"Error when parsing Timetable time String to LocalTime Object: " + str, e);
			}

		}

		try {
			boolean invalidHour = false;
			int dIndex = index;
			String returnValue = table[index][dayOfWeek.getValue()];

			if (returnValue == null||returnValue.isBlank()) {
				invalidHour=true;
				for (int i = index; i != 0; i--) {
					returnValue = table[i][dayOfWeek.getValue()];
					dIndex =i;
					if (!(returnValue.isBlank() || returnValue == null)) {
//						System.out.println(returnValue);
						break;
					}

				}
			}
			for(String[] arr:table) {
//				System.out.println("\""+arr[dayOfWeek.getValue()]+"\"");
			}
			
	        returnTypes[2]=IntStream.range(0, table.length)//really length-1? TODO
	                .mapToObj(i -> table[i][dayOfWeek.getValue()])
	                .toArray(String[]::new);
	        
	        
			returnTypes[3]=invalidHour;
	        returnTypes[4]=index;

	        returnTypes[0]=returnValue;
			returnTypes[1]=dIndex;
			
			//return table[index][dayOfWeek.getValue()];
	        return returnTypes;

	        
	        
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			new Thread(()->{
			Thread t1 = new Thread(()->{
				//e.printStackTrace();
				//ErrView.showStackTraceErrorDialog(null, "Probably Array out of bounds exception", e);
				
			});
			Thread t2 = new Thread(()->{
				Exception e1 = new Exception(Constants.dayOfWeekException,
						new Throwable("Current day: " + dayOfWeek.name() + "\t Index:\t" + dayOfWeek.getValue()
								+ "\n\nLast defined day in table: "
								+ /* table[0][table[index].length-1] */table[0][table[0].length - 1] + "\t Index:\t"
								+ (table[0].length - 1)));
				e1.printStackTrace();
				ErrView.showStackTraceErrorDialog(null, "Exception", e1);
			});
			t1.run();
			t2.run();
			}).run();

			
			boolean invalidHour = false;
			int dIndex = index;
			String returnValue = table[index][table[index].length - 1];

			if (returnValue == null||returnValue.isBlank() ){
				invalidHour=true;
				for (int i = index; i != 0; i--) {
					returnValue = table[i][table[i].length - 1];
					dIndex=i;
					if (!(returnValue.isBlank() || returnValue == null)) {
						//System.out.println(returnValue);
						break;
					}

				}
			}
			
			
			
			
//			System.out.println(returnValue);
//
//			System.out.println(table[index][table[index].length - 1]);
//			System.out.println(Arrays.toString(table[index]));
//			System.out.println("\"" + table[index][table[index].length - 1] + "\"");
			final int indexCopy = index;//Local variable statement defined in an enclosing scope must be final or effectively final. D-:<
			returnTypes[0]=returnValue;
			returnTypes[1]=dIndex;
			for(String[] arr:table) {
			//System.out.println("2nd\""+arr[indexCopy]+"\"");//dayOfWeekException
			}
	        returnTypes[2]=IntStream.range(0, table.length)//really length-1? TODO
	                .mapToObj(i -> table[i][table[indexCopy].length - 1])
	                .toArray(String[]::new);
			returnTypes[3]=invalidHour;
	        returnTypes[4]=index;
	        
	        
	        return returnTypes;
			//return returnValue;

	        
	        
		}catch(Exception e3) {
			//every non arrayindex out of bounds

			new Thread(()->{
			Thread t1 = new Thread(()->{
				//e.printStackTrace();
				ErrView.showStackTraceErrorDialog(null, "Non Array Index out of Bounds Exceptiom: ", e3);
				
			});
			/*Thread t2 = new Thread(()->{
				Exception e2 = new Exception(Constants.dayOfWeekException,
						new Throwable("Current day: " + dayOfWeek.name() + "\t Index:\t" + dayOfWeek.getValue()
								+ "\n\nLast defined day in table: "
								//+  table[0][table[index].length-1] 
								+table[0][table[0].length - 1] + "\t Index:\t"
								+ (table[0].length - 1)));
				e2.printStackTrace();
				ErrView.showStackTraceErrorDialog(null, "Exception", e2);
			});
						t2.run();*/
			t1.run();
			}).run();

			
			boolean invalidHour = false;
			int dIndex = index;
			String returnValue = table[index][table[index].length - 1];

			if (returnValue == null||returnValue.isBlank() ){
				invalidHour=true;
				for (int i = index; i != 0; i--) {
					returnValue = table[i][table[i].length - 1];
					dIndex=i;
					if (!(returnValue.isBlank() || returnValue == null)) {
						//System.out.println(returnValue);
						break;
					}

				}
			}
			
			
			
			
//			System.out.println(returnValue);
//
//			System.out.println(table[index][table[index].length - 1]);
//			System.out.println(Arrays.toString(table[index]));
//			System.out.println("\"" + table[index][table[index].length - 1] + "\"");
			final int indexCopy = index;//Local variable statement defined in an enclosing scope must be final or effectively final. D-:<
			returnTypes[0]=returnValue;
			returnTypes[1]=dIndex;
			for(String[] arr:table) {
			//System.out.println("2nd\""+arr[indexCopy]+"\"");//dayOfWeekException
			}
	        returnTypes[2]=IntStream.range(0, table.length)//really length-1? TODO
	                .mapToObj(i -> table[i][table[indexCopy].length - 1])
	                .toArray(String[]::new);
			returnTypes[3]=invalidHour;
	        returnTypes[4]=index;
	        
	        
	        return returnTypes;
			//return returnValue;
			
		}

	}
}

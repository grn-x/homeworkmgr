package de.grnx.homeworkmgr.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serializer {
    public static void serializeObject(String fileName, Object obj) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(obj);
            System.out.println("Serialization complete. Data saved to " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            ErrView.showStackTraceErrorDialog(null, "SerializingTable: "+e.getLocalizedMessage(), e);
        }
    }

    // Deserialize the 2D array from a file
    public static Object deserializeObject(String fileName) {
        Object deserializedTable = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            deserializedTable = inputStream.readObject();
            System.out.println("Deserialization complete. Data loaded from " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            ErrView.showStackTraceErrorDialog(null, "DeserializingObject: "+e.getLocalizedMessage(), e);

        }
        return deserializedTable;
    }
    
    public static void serializeData(EnSt entry, String path) {
    	try
		{
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(entry);
			out.close();
			fileOut.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			ErrView.showStackTraceErrorDialog(null, "Error when saving Homework data", e);
		}
    }
    
    public static EnSt deserializeData(String path) {
    	try {
		FileInputStream fileIn = new FileInputStream(path);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		EnSt entry = (EnSt) in.readObject();
		in.close();
		fileIn.close();
		return entry;
    	}catch (Exception e) {
    			e.printStackTrace();
    			ErrView.showStackTraceErrorDialog(null, "Error when loading Homework data", e);
    		return null;
    	}
    }
    
    
    public static void serializeDataArray(ArrayList<EnSt> entry, String path) {
    	try
		{
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(entry);
			out.close();
			fileOut.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			ErrView.showStackTraceErrorDialog(null, "Error when saving Homework data", e);
		}
    }
    
    public static ArrayList<EnSt> deserializeDataArray(String path) {
    	try {
		FileInputStream fileIn = new FileInputStream(path);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ArrayList<EnSt> entry = (ArrayList<EnSt>) in.readObject();
		in.close();
		fileIn.close();
		return entry;
    	}catch (Exception e) {
    			e.printStackTrace();
    			ErrView.showStackTraceErrorDialog(null, "Error when loading Homework data", e);
    		return null;
    	}
    }
    
    
}

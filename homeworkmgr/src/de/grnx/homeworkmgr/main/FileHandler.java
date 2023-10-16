package de.grnx.homeworkmgr.main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileHandler {

			/*usage example because i dont need the features that i wanted so bad 
			 *   

        String[] allowedExtensions = {"txt", "csv"};
        File openFile = FileHandler.openFile("/path/to/initial/directory", allowedExtensions);
        if (openFile != null) {
            // Process the opened file
            System.out.println("Opened file: " + openFile.getAbsolutePath());
        }

        File saveFile = FileHandler.saveFile("/path/to/initial/directory", allowedExtensions);
        if (saveFile != null) {
            // Process the saved file
            System.out.println("Saved file: " + saveFile.getAbsolutePath());
        }
			 */


	public static File openFile(String initialPath, String[] allowedExtensions) {
		if(!(initialPath==null)) {
			initialPath=Main.FOLDER.toString();
		}
		
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            ErrView.showStackTraceErrorDialog(null, "UI Look Error " + e.getLocalizedMessage(), e);
        }

        JFileChooser fileChooser = new JFileChooser(initialPath);
        fileChooser.setDialogTitle("Open File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Set file filters for allowed extensions
        if (allowedExtensions != null && allowedExtensions.length > 0) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Allowed Files", allowedExtensions);
            fileChooser.setFileFilter(filter);
        }

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION/*&&fileChooser.getSelectedFile().exists()*/) {
        	
            return fileChooser.getSelectedFile();
        
        }else {
        	//return openFile(initialPath, allowedExtensions);
            //remove comments for null check 
        	return null;
        }
            
    }

    // Method for saving files
    public static File saveFile(String initialPath, String[] allowedExtensions) {
    	if(!(initialPath==null)) {
			initialPath=Main.FOLDER.toString();
		}
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            ErrView.showStackTraceErrorDialog(null, "UI Look Error " + e.getLocalizedMessage(), e);

        }

        JFileChooser fileChooser = new JFileChooser(initialPath);
        fileChooser.setDialogTitle("Save File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Set file filters for allowed extensions
        if (allowedExtensions != null && allowedExtensions.length > 0) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Allowed Files", allowedExtensions);
            fileChooser.setFileFilter(filter);
        }

        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Ensure the file has the correct extension
            if (allowedExtensions != null && allowedExtensions.length > 0) {
                String selectedFileName = selectedFile.getName();
                boolean hasValidExtension = false;
                for (String extension : allowedExtensions) {
                    if (selectedFileName.endsWith("." + extension)) {
                        hasValidExtension = true;
                        break;
                    }
                }
                if (!hasValidExtension) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + "." + allowedExtensions[0]);
                }
            }

            return selectedFile;
        } else {
            //return saveFile(initialPath, allowedExtensions);
        	return null;
        	//uncomment for null check / cancel button loop
        }
    }

  
}

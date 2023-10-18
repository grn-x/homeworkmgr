package de.grnx.homeworkmgr.main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileHandler {

	public static File openFile(String initialPath, String[] allowedExtensions) {
		
		if (!(initialPath == null)) {
			initialPath = Main.FOLDER.toString();
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

		if (allowedExtensions != null && allowedExtensions.length > 0) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Allowed Files", allowedExtensions);
			fileChooser.setFileFilter(filter);
		}

		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION/* &&fileChooser.getSelectedFile().exists() */) {

			return fileChooser.getSelectedFile();

		} else {

			return null;
		}

	}

	public static File saveFile(String initialPath, String[] allowedExtensions) {
		if (!(initialPath == null)) {
			initialPath = Main.FOLDER.toString();
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

		if (allowedExtensions != null && allowedExtensions.length > 0) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Allowed Files", allowedExtensions);
			fileChooser.setFileFilter(filter);
		}

		int result = fileChooser.showSaveDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();

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
			return null;
		}
	}

}

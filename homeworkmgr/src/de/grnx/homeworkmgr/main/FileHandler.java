package de.grnx.homeworkmgr.main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.formdev.flatlaf.FlatLaf;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FileHandler {


	
	
	
public static File openFile(String initialPath, FileNameExtensionFilter[] FileTypes, boolean allowAnyFile, boolean allowFolder, boolean allowMultiSelection, String title) {
//	fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
//	usage : new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp")
//			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
//    		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MS Office Documents", "docx", "xlsx", "pptx"));
//    		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
	
	//       File file = FileHandler.openFile(Settings.getTableLocation().getAbsolutePath(), new FileNameExtensionFilter[]{new FileNameExtensionFilter("Serialized File: *.ser","ser"), new FileNameExtensionFilter("Data File: *.dat", "dat")},true, false, false, null);
//    File file = FileHandler.saveFile(Settings.getTableLocation().getAbsolutePath(), new FileNameExtensionFilter[]{new FileNameExtensionFilter("Serialized File","ser"), new FileNameExtensionFilter("Data File", "dat")},false, false, false, null);
//maybe later but lots of bugs :(

		if(title==null||title.isBlank())title="Open File";
		
		if (!(initialPath == null)) {
			initialPath = Main.FOLDER.toString();
		}

		try {
			switch (Main.FlatLafConfig) {
			case 0: {
//				FlatLaf.repaintAllFramesAndDialogs();
//				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				
				FlatLaf.repaintAllFramesAndDialogs();
				break;
			}case 1: {
				com.formdev.flatlaf.FlatDarkLaf.setup();
				break;
			}case 2: {
				com.formdev.flatlaf.FlatLightLaf.setup();
				break;
			}case 3: {
				com.formdev.flatlaf.FlatDarculaLaf.setup();
				break;
			}case 4: {
				com.formdev.flatlaf.FlatIntelliJLaf.setup();
				break;
			}
			default:
//				com.formdev.flatlaf.FlatDarkLaf.setup();
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrView.showStackTraceErrorDialog(null, "UI Look Error " + e.getLocalizedMessage(), e);
		}

		JFileChooser fileChooser = new JFileChooser(initialPath);
		fileChooser.setDialogTitle(title);
		
		for(FileNameExtensionFilter f:FileTypes) {
			fileChooser.addChoosableFileFilter(f);
		}
		
		
		if(allowFolder) {
					fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		}else {
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		}
		
			fileChooser.setMultiSelectionEnabled(allowMultiSelection);
			fileChooser.setAcceptAllFileFilterUsed(allowAnyFile);			

			

		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION/* &&fileChooser.getSelectedFile().exists() */) {

			System.out.println(fileChooser.getSelectedFile());
			System.out.println(fileChooser.getFileFilter());
			
			if(fileChooser.getSelectedFile().getAbsolutePath().endsWith(fileChooser.getFileFilter().toString())) {
				return fileChooser.getSelectedFile();
			}else {
				return new File(fileChooser.getSelectedFile().getAbsolutePath()+fileChooser.getFileFilter().toString());

			}

		} else {

			return null;
		}

	}

public static File saveFile(String initialPath, FileNameExtensionFilter[] FileTypes, boolean allowAnyFile, boolean allowFolder, boolean allowMultiSelection, String title) {
//	fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
//	usage : new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp")
//			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
//    		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MS Office Documents", "docx", "xlsx", "pptx"));
//    		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));

		if(title==null||title.isBlank())title="Save File";
		
		if (!(initialPath == null)) {
			initialPath = Main.FOLDER.toString();
		}

		try {
			switch (Main.FlatLafConfig) {
			case 0: {
//				FlatLaf.repaintAllFramesAndDialogs();
//				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				
				FlatLaf.repaintAllFramesAndDialogs();
				break;
			}case 1: {
				com.formdev.flatlaf.FlatDarkLaf.setup();
				break;
			}case 2: {
				com.formdev.flatlaf.FlatLightLaf.setup();
				break;
			}case 3: {
				com.formdev.flatlaf.FlatDarculaLaf.setup();
				break;
			}case 4: {
				com.formdev.flatlaf.FlatIntelliJLaf.setup();
				break;
			}
			default:
//				com.formdev.flatlaf.FlatDarkLaf.setup();
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrView.showStackTraceErrorDialog(null, "UI Look Error " + e.getLocalizedMessage(), e);
		}

		JFileChooser fileChooser = new JFileChooser(initialPath);
		fileChooser.setDialogTitle(title);
		
		if(allowFolder) {
					fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		}else {
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		}
		
			fileChooser.setMultiSelectionEnabled(allowMultiSelection);
			fileChooser.setAcceptAllFileFilterUsed(allowAnyFile);			

			for(FileNameExtensionFilter f:FileTypes) {
				fileChooser.addChoosableFileFilter(f);
			}
			

		int result = fileChooser.showSaveDialog(null);

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
			switch (Main.FlatLafConfig) {
			case 0: {
//				FlatLaf.repaintAllFramesAndDialogs();
//				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				
				FlatLaf.repaintAllFramesAndDialogs();
				break;
			}case 1: {
				com.formdev.flatlaf.FlatDarkLaf.setup();
				break;
			}case 2: {
				com.formdev.flatlaf.FlatLightLaf.setup();
				break;
			}case 3: {
				com.formdev.flatlaf.FlatDarculaLaf.setup();
				break;
			}case 4: {
				com.formdev.flatlaf.FlatIntelliJLaf.setup();
				break;
			}
			default:
//				com.formdev.flatlaf.FlatDarkLaf.setup();
			}
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
	
	
	public static File openFile(String initialPath, String[] allowedExtensions) {
		
		if (!(initialPath == null)) {
			initialPath = Main.FOLDER.toString();
		}

		try {
			switch (Main.FlatLafConfig) {
			case 0: {
//				FlatLaf.repaintAllFramesAndDialogs();
//				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				
				FlatLaf.repaintAllFramesAndDialogs();
				break;
			}case 1: {
				com.formdev.flatlaf.FlatDarkLaf.setup();
				break;
			}case 2: {
				com.formdev.flatlaf.FlatLightLaf.setup();
				break;
			}case 3: {
				com.formdev.flatlaf.FlatDarculaLaf.setup();
				break;
			}case 4: {
				com.formdev.flatlaf.FlatIntelliJLaf.setup();
				break;
			}
			default:
//				com.formdev.flatlaf.FlatDarkLaf.setup();
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrView.showStackTraceErrorDialog(null, "UI Look Error " + e.getLocalizedMessage(), e);
		}

		JFileChooser fileChooser = new JFileChooser(initialPath);
		fileChooser.setDialogTitle("Open File");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);

		if (allowedExtensions != null && allowedExtensions.length > 0) {
			;
			FileNameExtensionFilter filter = new FileNameExtensionFilter(Arrays.stream(allowedExtensions).map(s -> "*." + s).collect(Collectors.joining("; ")), allowedExtensions);
			fileChooser.setFileFilter(filter);
		}

		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION/* &&fileChooser.getSelectedFile().exists() */) {

			return fileChooser.getSelectedFile();

		} else {

			return null;
		}

	}

}

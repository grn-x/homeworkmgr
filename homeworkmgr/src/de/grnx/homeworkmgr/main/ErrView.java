package de.grnx.homeworkmgr.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class ErrView {
    public static void showStackTraceErrorDialog(Component parentComponent, String title, Exception e) {
    	switch (Main.FlatLafConfig) {
		case 0: {
			//skip for swing gui
		}case 1: {
			com.formdev.flatlaf.FlatDarkLaf.setup();
			break;
		}case 2: {
			com.formdev.flatlaf.FlatIntelliJLaf.setup();
			break;
		}case 3: {
			com.formdev.flatlaf.FlatDarculaLaf.setup();
			break;
		}case 4: {
			com.formdev.flatlaf.FlatLightLaf.setup();
			break;
		}
		default:
//			com.formdev.flatlaf.FlatDarkLaf.setup();
		}
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String message = sw.toString();

        
        Throwable rootCause = e;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }

        message = message.concat("\n\nCause: \n"+rootCause.getMessage());
        
        
        JTextArea messageArea = new JTextArea(message);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setResizable(true);

//ImageIcon errorIcon = (ImageIcon) UIManager.getIcon("OptionPane.errorIcon");flatlfaferror
        JLabel titleLabel = new JLabel("Error : " + e.getMessage());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
//titlePanel.add(new JLabel(errorIcon));flatlfaferror
        titlePanel.add(Box.createHorizontalStrut(10));
        titlePanel.add(titleLabel);

        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(titlePanel, BorderLayout.NORTH);
        dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);

        dialog.pack();
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
    }

}

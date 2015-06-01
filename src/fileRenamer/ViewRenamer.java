package fileRenamer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ViewRenamer extends JFrame {

	private JButton btnRename;
	private JTextField txtPath;
	private JTextField txtRegex;
	private JTextField txtReplacement;
	private JTextField txtFrom;
	private JTextField txtTo;
	private JTextArea txtListed;
	private JCheckBox btnFile;
	private JCheckBox btnDirectory;
	private JCheckBox btnRecursive;

	private boolean files;
	private boolean directorys;
	private boolean recursive;

	private JButton btnCreateDirectoryToFiles;
	private JTextField txtForExtraFunctions;
	private JButton btnAddToFileNameFront;

	public ViewRenamer() {
		super("File renamer");
		erstelleGUI();
	}

	private void erstelleGUI() {
		this.getContentPane().setLayout(null);
		this.setSize(880, 510);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		{
			txtPath = createTextWithLabel("Path", 0);
			txtRegex = createTextWithLabel("Find", 1);
			txtReplacement = createTextWithLabel("Replace with", 2);
			txtFrom = createTextWithLabel("Delete from", 3);
			txtTo = createTextWithLabel("to", 4);
			txtListed = createTextAreaListed();

			btnFile = createCheckBox("Files", 0, btnFile);
			btnDirectory = createCheckBox("Directorys", 1, btnDirectory);
			btnRecursive = createCheckBox("Recursive", 2, btnRecursive);

			btnRename = new JButton("Rename");
			btnRename.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					String showText = "";
					try {
						showText = RenameTools.rename(files, directorys, recursive, txtPath.getText(), txtRegex.getText(), txtReplacement.getText(), txtFrom.getText(),
								txtTo.getText());
					} catch (RuntimeException e) {
						JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + e.getStackTrace(), "Ecxeption", JOptionPane.OK_OPTION);
					}
					JOptionPane.showMessageDialog(null, showText);
				}
			});
			btnRename.setBounds(250, 275, 150, 50);
			this.getContentPane().add(btnRename);
		}

		{
			txtForExtraFunctions = createTextWithLabel("", 6.9);

			btnCreateDirectoryToFiles = new JButton("CreateDirectoryToFiles and order in Dirs!!!");
			btnCreateDirectoryToFiles.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent ae) {
					String showText = "";
					try {
						showText = RenameTools.createDirectoryUpToSplitSequence(txtPath.getText(), txtForExtraFunctions.getText());
					} catch (RuntimeException e) {
						JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + e.getStackTrace(), "Ecxeption", JOptionPane.OK_OPTION);
					}
					JOptionPane.showMessageDialog(null, showText);
				}
			});
			btnCreateDirectoryToFiles.setBounds(250, 400, 150, 50);
			this.getContentPane().add(btnCreateDirectoryToFiles);

			btnAddToFileNameFront = new JButton("AddToFileNameFront");
			btnAddToFileNameFront.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent ae) {
					String showText = "";
					try {
						showText = RenameTools.addTextToFilenameFront(files, directorys, recursive, txtPath.getText(), txtForExtraFunctions.getText());
					} catch (RuntimeException e) {
						JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + e.getStackTrace(), "Ecxeption", JOptionPane.OK_OPTION);
					}
					JOptionPane.showMessageDialog(null, showText);
				}
			});
			btnAddToFileNameFront.setBounds(50, 400, 160, 50);
			this.getContentPane().add(btnAddToFileNameFront);

		}

		this.setVisible(true);
	}

	private JTextArea createTextAreaListed() {

		txtListed = new JTextArea("Listed Files and Dirs");
		JScrollPane scrollPane = new JScrollPane(txtListed);
		JPanel panel = new JPanel(new BorderLayout());

		panel.setBounds(420, 20, 420, 430);

		panel.add(scrollPane);
		this.getContentPane().add(panel);

		return txtListed;
	}

	private JTextField createTextWithLabel(String infoText, double countwidgetPairs) {
		JLabel lblInfo = new JLabel(infoText);
		int height = (int) (20 + (countwidgetPairs * 50.0));
		lblInfo.setBounds(20, height, 80, 30);
		this.getContentPane().add(lblInfo);

		JTextField txtField = new JTextField();
		txtField.setBounds(100, height, 300, 30);
		this.getContentPane().add(txtField);
		return txtField;
	}

	private JCheckBox createCheckBox(String infoText, final int counter, JCheckBox btnCheckBox) {
		btnCheckBox = new JCheckBox(infoText);
		int height = 270 + 30 * counter;

		btnCheckBox.setBounds(10, height, 200, 30);
		this.getContentPane().add(btnCheckBox);

		btnCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (counter == 0) {
					if (files) {
						files = false;
					} else {
						files = true;
					}
				}
				if (counter == 1) {
					if (directorys) {
						directorys = false;
					} else {
						directorys = true;
					}
				}
				if (counter == 2) {
					if (recursive) {
						recursive = false;
					} else {
						recursive = true;
					}
				}
			}
		});

		return btnCheckBox;
	}
}
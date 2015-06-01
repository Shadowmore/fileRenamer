package fileRenamer;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

public class ViewRenamer_SWT extends Composite {

	private boolean files;
	private boolean directorys;
	private boolean recursive;

	private Text txtPath;
	private Text txtSearch;
	private Text txtReplaceWith;
	private Text txtDeleteFrom;
	private Text txtDeleteTo;
	private Text txtAddToFront;
	private Text txtCreateDirectoryUpToSplitSequence;
	private Text txtList;

	private Button btnCheckFiles;
	private Button btnCheckDirectorys;
	private Button btnCheckRecursive;

	public ViewRenamer_SWT(Composite parent) {
		super(parent, SWT.NONE);

		parent.setLayout(new GridLayout(1, false));
		setLayout(new GridLayout(1, false));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createContent(this);

		files = btnCheckFiles.getSelection();
		directorys = btnCheckDirectorys.getSelection();
		recursive = btnCheckRecursive.getSelection();
	}

	private void createContent(Composite parent) {

		Composite compMain = generateCompositeWithGridLayout(parent, 1, false);
		compMain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		{
			Composite compPath = generateCompositeWithGridLayout(compMain, 3, false);
			txtPath = generateLabelWithText_WithoutExtraContainer(compPath, "Pfad:");
			Button btnOpenDirectoryDialog = new Button(compPath, SWT.NONE);
			btnOpenDirectoryDialog.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

			URL resource = ViewRenamer_SWT.class.getClassLoader().getResource("fileRenamer/pics/directory.png");
			try {
				btnOpenDirectoryDialog.setImage(new Image(btnOpenDirectoryDialog.getDisplay(), resource.openStream()));
			} catch (IOException e1) {
				throw new RuntimeException(e1);
			}
			btnOpenDirectoryDialog.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					btnOpenDirectoryDialog_WidgetSelected(e);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}

		{
			Composite compCheckboxes = generateCompositeWithGridLayout(compMain, 3, false);
			btnCheckFiles = generateCheckBoxButton(compCheckboxes, "Dateien");
			btnCheckFiles.setSelection(true);
			btnCheckFiles.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					files = btnCheckFiles.getSelection();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			btnCheckDirectorys = generateCheckBoxButton(compCheckboxes, "Ordner");
			btnCheckDirectorys.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					directorys = btnCheckDirectorys.getSelection();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			btnCheckRecursive = generateCheckBoxButton(compCheckboxes, "Rekursiv");
			btnCheckRecursive.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					recursive = btnCheckRecursive.getSelection();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}

		//		Composite compLabelTextButton = generateCompositeWithGridLayout(compMain, 3, false);

		{
			Composite compReplaceWith = generateCompositeWithGridLayoutAndBorder(compMain, 3, false);
			//			Composite compReplaceWith = compLabelTextButton;
			txtSearch = generateLabelWithText_WithoutExtraContainer(compReplaceWith, "Suche nach");
			Button btnReplaceWith = new Button(compReplaceWith, SWT.NONE);
			btnReplaceWith.setText("Umbenennen");
			btnReplaceWith.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));
			btnReplaceWith.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					btnReplaceWith_WidgetSelected(e);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			txtReplaceWith = generateLabelWithText_WithoutExtraContainer(compReplaceWith, "Ersetze mit");
		}

		{
			Composite compDeleteFromTo = generateCompositeWithGridLayoutAndBorder(compMain, 3, false);
			//			Composite compDeleteFromTo = compLabelTextButton;
			txtDeleteFrom = generateLabelWithText_WithoutExtraContainer(compDeleteFromTo, "Lösche von");
			Button btnDeleteFromTo = new Button(compDeleteFromTo, SWT.NONE);
			btnDeleteFromTo.setText("Umbenennen");
			btnDeleteFromTo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));
			btnDeleteFromTo.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					btnDeleteFromTo_WidgetSelected(e);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			txtDeleteTo = generateLabelWithText_WithoutExtraContainer(compDeleteFromTo, "bis");
		}

		{
			Composite compAddToFront = generateCompositeWithGridLayoutAndBorder(compMain, 3, false);
			//			Composite compAddToFront = compLabelTextButton;
			txtAddToFront = generateLabelWithText_WithoutExtraContainer(compAddToFront, "Füge am Anfang an");
			Button btnAddToFront = new Button(compAddToFront, SWT.NONE);
			btnAddToFront.setText("Umbenennen");
			btnAddToFront.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
			btnAddToFront.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					btnAddToFront_WidgetSelected(e);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}

		{
			Composite compCreateDirectoryUpToSplitSequence = generateCompositeWithGridLayoutAndBorder(compMain, 3, false);
			//			Composite compCreateDirectoryUpToSplitSequence = compLabelTextButton;
			txtCreateDirectoryUpToSplitSequence = generateLabelWithText_WithoutExtraContainer(compCreateDirectoryUpToSplitSequence, "Erstelle einen Ordner aus dem\nDateinamen von Anfang bis");
			Button btnCreateDirectoryUpToSplitSequence = new Button(compCreateDirectoryUpToSplitSequence, SWT.NONE);
			btnCreateDirectoryUpToSplitSequence.setText("Erstelle Ordner");
			btnCreateDirectoryUpToSplitSequence.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
			btnCreateDirectoryUpToSplitSequence.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					btnCreateDirectoryUpToSplitSequence_WidgetSelected(e);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}

		{
			Composite compListFiles = generateCompositeWithGridLayoutAndBorder(compMain, 1, false);
			compListFiles.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			Button btnListFiles = new Button(compListFiles, SWT.NONE);
			btnListFiles.setText("Liste Dateien und/oder Ordner auf");
			btnListFiles.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
			btnListFiles.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					btnListFiles_WidgetSelected(e);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			txtList = new Text(compListFiles, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
			txtList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}
	}

	private Button generateCheckBoxButton(Composite compParent, String btnText) {
		Button btnCheckFiles = new Button(compParent, SWT.CHECK);
		btnCheckFiles.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnCheckFiles.setText(btnText);
		return btnCheckFiles;
	}

	//	private Text generateLabelWithText_WithExtraContainer(Composite parent, String lblText) {
	//		Composite comp = generateCompositeWithGridLayout(parent, 2, false);
	//		Text text = generateLabelWithText_WithoutExtraContainer(comp, lblText);
	//		return text;
	//	}

	private Text generateLabelWithText_WithoutExtraContainer(Composite parent, String lblText) {
		Label lbl = new Label(parent, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		lbl.setText(lblText);

		Text text = new Text(parent, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		return text;
	}

	private Composite generateCompositeWithGridLayoutAndBorder(Composite parent, int rows, boolean equals) {
		Composite comp = new Composite(parent, SWT.BORDER);
		comp.setLayout(new GridLayout(rows, equals));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		return comp;
	}

	private Composite generateCompositeWithGridLayout(Composite parent, int rows, boolean equals) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(rows, equals));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		return comp;
	}

	private void btnOpenDirectoryDialog_WidgetSelected(SelectionEvent e) {

		DirectoryDialog directoryDialog = new DirectoryDialog(this.getShell(), SWT.NONE);

		String actPath = txtPath.getText();
		if (actPath != null && !actPath.equals("")) {
			File file = new File(actPath);

			if (file.exists()) {
				directoryDialog.setFilterPath(actPath);
			}
		}

		String path = directoryDialog.open();
		if (path != null) {
			txtPath.setText(path);
		}
	}

	private void btnReplaceWith_WidgetSelected(SelectionEvent e) {
		String showText = "";
		try {
			showText = RenameTools.rename(files, directorys, recursive, txtPath.getText(), txtSearch.getText(), txtReplaceWith.getText(), null, null);
		} catch (RuntimeException ex) {
			MessageBox mb = new MessageBox(getShell(), SWT.ERROR);
			mb.setMessage(ex.getMessage());
			mb.open();
			return;
		}
		MessageBox mb = new MessageBox(getShell(), SWT.DEFAULT);
		mb.setMessage(showText);
		mb.open();
	}

	private void btnDeleteFromTo_WidgetSelected(SelectionEvent e) {
		String showText = "";
		try {
			showText = RenameTools.rename(files, directorys, recursive, txtPath.getText(), null, null, txtDeleteFrom.getText(), txtDeleteTo.getText());
		} catch (RuntimeException ex) {
			MessageBox mb = new MessageBox(getShell(), SWT.ERROR);
			mb.setMessage(ex.getMessage());
			mb.open();
			return;
		}
		MessageBox mb = new MessageBox(getShell(), SWT.DEFAULT);
		mb.setMessage(showText);
		mb.open();
		return;
	}

	private void btnAddToFront_WidgetSelected(SelectionEvent e) {
		String showText = "";
		try {
			showText = RenameTools.addTextToFilenameFront(files, directorys, recursive, txtPath.getText(), txtAddToFront.getText());
		} catch (RuntimeException ex) {
			MessageBox mb = new MessageBox(getShell(), SWT.ERROR);
			mb.setMessage(ex.getMessage());
			mb.open();
			return;
		}
		MessageBox mb = new MessageBox(getShell(), SWT.DEFAULT);
		mb.setMessage(showText);
		mb.open();
	}

	private void btnCreateDirectoryUpToSplitSequence_WidgetSelected(SelectionEvent e) {
		String showText = "";
		try {
			String path = txtPath.getText();
			if (path != null && !path.equals("")) {
				showText = RenameTools.createDirectoryUpToSplitSequence(txtPath.getText(), txtCreateDirectoryUpToSplitSequence.getText());
			}
		} catch (Exception ex) {
			MessageBox mb = new MessageBox(getShell(), SWT.ERROR);
			mb.setMessage(ex.getMessage());
			mb.open();
			return;
		}
		MessageBox mb = new MessageBox(getShell(), SWT.DEFAULT);
		mb.setMessage(showText);
		mb.open();
	}

	private void btnListFiles_WidgetSelected(SelectionEvent e) {
		String path = txtPath.getText();
		if (path == null || path.equals("")) {
			MessageBox mb = new MessageBox(getShell(), SWT.DEFAULT);
			mb.setMessage("Es wurde kein Pfad angegeben");
			mb.open();
		} else {
			txtList.setText("");

			String listedFiles = "";
			try {
				listedFiles = RenameTools.listFilesInPath(files, directorys, recursive, path);
			} catch (Exception ex) {
				MessageBox mb = new MessageBox(getShell(), SWT.ERROR);
				mb.setMessage(ex.getMessage());
				mb.open();
				return;
			}

			if (listedFiles != null && !listedFiles.equals("")) {
				txtList.setText(listedFiles);
			}

			int amountItems = listedFiles.split("\\n").length;

			MessageBox mb = new MessageBox(getShell(), SWT.DEFAULT);
			mb.setMessage("Es wurden " + amountItems + " Dateien und/oder Ordner gefunden.");
			mb.open();
		}
	}
}
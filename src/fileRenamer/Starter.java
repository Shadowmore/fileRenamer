package fileRenamer;

import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Starter {

	public static void main(String[] args) {

		//		boolean doAWT = true;
		boolean doAWT = false;
		if (doAWT) {
			new ViewRenamer();
		} else {
			try {
				Display display = new Display();
				Shell shell = new Shell(display);
				shell.setSize(800, 600);
				shell.setText("Renamer");
				new ViewRenamer_SWT(shell);
				shell.open();

				while (!shell.isDisposed()) {
					if (!display.readAndDispatch())
					{
						display.sleep();
					}
				}
				display.dispose();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
}
package fileRenamer;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class RenameTools {

	public static String rename(boolean files, boolean directorys, boolean recursive, String path, String regex, String replacement, String from, String to) {
		try {
			if (path != null && !path.equals("")) {
				String errorMessage = Renamer.rename(files, directorys, recursive, path, regex, replacement, from, to);
				if (errorMessage != null) {
					return errorMessage;
				}
			} else {
				return "Es wurde kein Pfad angegeben";
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return "Es wurde alles umbenannt";
	}

	public static String listFilesInPath(boolean files, boolean directorys, boolean recursive, String path) {
		String listedFiles = "";
		if (path != null && !path.equals("")) {
			List<File> listFromPath = DirectoryReadTool.getListFromPath(path, files, directorys, recursive);
			for (File file : listFromPath) {
				String fileName = file.getName();
				listedFiles += fileName + "\n";
			}
		}
		return listedFiles;
	}

	public static String addTextToFilenameFront(boolean files, boolean directorys, boolean recursive, String path, String textToAdd) {
		try {
			if (path != null && !path.equals("")) {
				if (textToAdd != null && !textToAdd.equals("")) {
					List<File> listFromPathSimpleDeepth = DirectoryReadTool.getListFromPath(path, files, directorys, recursive);
					for (File file : listFromPathSimpleDeepth) {
						if ((file.isFile() && files) || (file.isDirectory() && directorys)) {
							file.renameTo(new File(path + "\\" + textToAdd + file.getName()));
						}
					}
				}
			} else {
				return "Es wurde kein Pfad angegeben";
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + e.getStackTrace(), "Ecxeption", JOptionPane.OK_OPTION);
		}
		return "Es wurde alles umbenannt";
	}

	public static String createDirectoryUpToSplitSequence(String path, String partTo) {
		String nameTo = "";
		if (partTo != null) {
			nameTo = partTo;
		}

		List<File> listFiles = DirectoryReadTool.getListFromPath(path, true, false, false);
		Map<String, String> mapFiles = new LinkedHashMap<>();

		List<String> listArtistsForDirectorys = new ArrayList<>();
		for (File file : listFiles) {
			String name = file.getName();
			String directoryName = name.split(nameTo)[0];
			mapFiles.put(name, directoryName);
			boolean contains = listArtistsForDirectorys.contains(directoryName);
			if (!contains) {
				listArtistsForDirectorys.add(directoryName);
			}
		}

		for (String directoryName : listArtistsForDirectorys) {
			File directory = new File(path + "/" + directoryName);
			boolean mkdir = directory.mkdir();
			if (!mkdir) {
				throw new RuntimeException("Fehler beim Erstellen des Ordners:" + directoryName);
			}
		}

		for (File file : listFiles) {
			String fileName = file.getName();
			String directoryName = mapFiles.get(fileName);

			file.renameTo(new File(path + "/" + directoryName + "/" + fileName));
		}

		return "Alle Ordner wurden erzeugt, und die Dateien hineinverschoben";
	}
}
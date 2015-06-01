package fileRenamer;

import java.io.File;
import java.util.List;

public class Renamer {

	public static String rename(boolean files, boolean directorys, boolean recursive, String path, String regex, String replacement, String from, String to) {

		if (!new File(path).exists()) {
			throw new RuntimeException("Datei/Ordner nicht gefunden.");
		}

		boolean changed = false;
		List<File> listFiles = DirectoryReadTool.getListFromPath(path, files, directorys, recursive);
		listFiles(listFiles);

		if (regex != null) {
			if (!regex.isEmpty()) {
				for (File file : listFiles) {
					String filename = file.getName();
					while (true) {
						if (regex.equals(".")) {
							int first = filename.indexOf(".");
							int last = filename.lastIndexOf(".");
							if (first == last) {
								break;
							} else {
								filename = filename.replaceFirst("[" + regex + "]", replacement);
							}
						} else {
							if (filename.contains(regex)) {
								filename = filename.replace(regex, replacement);
								break;
							} else {
								break;
							}
						}
					}
					if (!filename.equals(file.getName())) {
						boolean renameTo = file.renameTo(new File(path + "\\" + filename));
						if (!renameTo) {
							String errorMessage = "Error: " + path + "\\" + filename + " ---> File already exists???";
							System.out.println(errorMessage);
							return errorMessage;
						}
						changed = true;
					}
				}
				if (changed) {
					listFiles = DirectoryReadTool.getListFromPath(path, files, directorys, recursive);
				}
				listFiles(listFiles);
			}
		}

		if (from != null) {
			if (!from.isEmpty()) {
				for (File file : listFiles) {
					String filename = file.getName();

					filename = delFromTo(from, to, filename);

					if (!filename.equals(file.getName())) {
						boolean renameTo = file.renameTo(new File(path + "\\" + filename));
						if (!renameTo) {
							String errorMessage = "Error: " + path + "\\" + filename + " ---> File already exists???";
							System.out.println(errorMessage);
							return errorMessage;
						}
						changed = true;
					}
				}
				if (changed) {
					listFiles = DirectoryReadTool.getListFromPath(path, files, directorys, recursive);
				}
			}
		}
		listFiles(listFiles);
		System.out.println("Done");
		return null;

	}

	private static String delFromTo(String from, String to, String filename) {
		if (filename.contains(from) && filename.contains(to)) {
			int intFrom = filename.indexOf(from);
			int intTo = filename.indexOf(to);
			String toDel = filename.substring(intFrom, intTo + 1);
			filename = filename.replace(toDel, "");
		} else {
			return filename;
		}
		filename = delFromTo(from, to, filename);
		return filename;
	}

	private static void listFiles(List<File> listFiles) {
		for (File file : listFiles) {
			System.out.println(file.getName());
		}
		System.out.println("");
		System.out.println("");
		System.out.println("");
	}
}
package fileRenamer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryReadTool {

	public static List<File> getListFromPath(String path, boolean withFiles, boolean withDirectorys, boolean recursive) {
		List<File> listFiles = new ArrayList<>();
		File file = new File(path);

		if (!file.exists()) {
			throw new RuntimeException("Datei/Ordner nicht gefunden.");
		}

		listFilesAndDirs(file, listFiles, withFiles, withDirectorys, recursive);
		return listFiles;
	}

	private static void listFilesAndDirs(File file, List<File> listFiles, boolean withFiles, boolean withDirectorys, boolean recursive) {
		for (File fileFromList : file.listFiles()) {

			if (fileFromList.isFile()) {
				if (withFiles) {
					listFiles.add(fileFromList);
				}
			}

			if (fileFromList.isDirectory()) {
				if (withDirectorys) {
					listFiles.add(fileFromList);
				}
				if (recursive) {
					listFilesAndDirs(fileFromList, listFiles, withFiles, withDirectorys, recursive);
				}
			}
		}
	}
}

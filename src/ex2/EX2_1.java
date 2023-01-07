package ex2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class EX2_1 {
	public static String[] createTextFiles(int n, int seed, int bound) {
		int x;
		String[] filesNames = new String[n]; //the names of the files
		String nameFile;
		Random rand = new Random(seed);
		for (int i = 0; i < n; i++) {
			x = rand.nextInt(bound);// the number of lines
			nameFile = "File" + (i + 1) + ".txt";
			filesNames[i] = nameFile;
			try {
				File randomFile = new File(nameFile);
				if (randomFile.createNewFile()) {

					FileWriter myWriter = new FileWriter(randomFile);
					for (int j = 1; j <= x; j++) {
						myWriter.write("This is line: " + j + "\n");
					}
					myWriter.close();
				} else {
					System.out.println("File already exists.");
				}
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
		return filesNames;
	}

	public static int getNumOfLines(String[] fileNames) throws FileNotFoundException {
		int counterForLines = 0;
		Scanner reader;
		File fileTemp;
		for (String fileName : fileNames) {
			fileTemp = new File(fileName);
			reader = new Scanner(fileTemp);
			while (reader.hasNextLine()) { // if we got \n
				reader.nextLine();
				counterForLines++;
			}
			reader.close();
		}
		return counterForLines;
	}

	public int getNumOfLinesThreads(String[] fileNames) throws FileNotFoundException {
		int counterLine = 0;
		helperForThread[] arrThread = new helperForThread[fileNames.length];
		for (int i = 0; i < fileNames.length; i++) {
			arrThread[i] = new helperForThread(fileNames[i]);
			counterLine += arrThread[i].countLine;
		}
		return counterLine;
	}

	public int getNumOfLinesThreadPool(String[] fileNames) throws FileNotFoundException {
		int counterline = 0;
		helperForThreadpool[] threadsArr = new helperForThreadpool[fileNames.length];
		for (int i = 0; i < fileNames.length; i++) {
			threadsArr[i] = new helperForThreadpool(fileNames[i]);
			counterline += threadsArr[i].countLine;
		}

		return counterline;
	}

	public static void deleteFiles(String[] filesNames) {
		for (String fileName : filesNames) {
			File file = new File(fileName);
			file.delete();
		}
	}
}

package ex2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class helperForThread extends Thread{
    File fileTemp = null;
    int countLine =0;
    Scanner reader;
    public helperForThread(String nameFile) throws FileNotFoundException {
        this.fileTemp = new File(nameFile);
        reader = new Scanner(fileTemp);
    }

    public synchronized void run(){
        while (reader.hasNextLine()) { // if we got \n
            reader.nextLine();
            countLine++;
        }
        reader.close();
    }
}

package main.Constants;

import java.util.Arrays;
/**
 * Clasa pentru definirea constantelor folosite de-a lungul aplicatiei
 * */
public class Constants {
    /**
     * Calea fisierului cu datele de intrare
     * */
    private static final String filePath = "D:\\PPOO\\PROIECT_NOU\\proiect\\src\\main\\Data\\Directories.txt";

    /**
     * Calea fisierului cu date de iesire pentru statisitci*/
    private static final String fileOutPath = "D:\\PPOO\\PROIECT_NOU\\proiect\\src\\main\\Data\\Statistics.txt";

    /**
     * Vector de Stringuri care reprezinta extensiile posibile ale fisierelor multimedia*/
    private static final String[] multimediaFileExtensions = {"jpeg", "png", "svg", "mp4", "mp3", "wmv"};

    /**
     * Metoda care returneaza calea fisierului cu date de intrare
     * @return calea fisierului cu date de intrare*/
    public static String getFilePath() {
        return filePath;
    }

    /**
     * Metoda care returneaza calea fisierului cu statistici
     * @return calea fisierului cu date de iesire*/
    public static String getFileOutPath() {
        return fileOutPath;
    }

    /**
     * Metoda care returneaza vectorul cu extensii pentru fisierele multimedia
     * @return vectorul de extensii*/
    public static String[] getMultimediaFileExtensions() {
        return multimediaFileExtensions;
    }
}

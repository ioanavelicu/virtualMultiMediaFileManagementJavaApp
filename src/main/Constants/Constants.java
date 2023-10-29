package main.Constants;

import java.util.Arrays;

public class Constants {
    private static final String filePath = "D:\\PPOO\\PROIECT_NOU\\proiect\\src\\main\\Data\\Directories.txt";

    private static final String[] multimediaFileExtensions = {"img", "jpeg", "png", "svg", "mp4", "mp3", "wmv"};

    public static String getFilePath() {
        return filePath;
    }

    public static boolean fileHasCorrectExtension(String extension) {
        for(String e: multimediaFileExtensions) {
            if(e.equals(extension)) {
                return true;
            }
        }
        return false;
    }
}

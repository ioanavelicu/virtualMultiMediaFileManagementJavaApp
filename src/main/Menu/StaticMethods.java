package main.Menu;

import main.Constants.Constants;
import main.Directory.Directory;
import main.Exceptions.ExceptionDirectoryDoesNotExist;
import main.Exceptions.ExceptionIncorrectPath;
import main.File.File;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class StaticMethods {
    public static void getStatisticsFromApp() throws IOException {
        int numberOfDirectories = (int) MenuDirectories.getListOfDirectories().stream().count();

        int[] numberOfFilesPerDirectory = new int[numberOfDirectories];
        int[] numberOfVideosPerDirectory = new int[numberOfDirectories];
        int[] numberOfImagesPerDirectory = new int[numberOfDirectories];
        int[] numberOfAudiosPerDirectory = new int[numberOfDirectories];
        int i = 0;

        List<Directory> listOdDirectories = MenuDirectories.getListOfDirectories();
        for(Directory directory : listOdDirectories) {
            List<File> listOfFiles = directory.getListOfFiles();
            for(File file : listOfFiles) {
                numberOfFilesPerDirectory[i] ++;
                if(file.getExtension().equals("jpeg") || file.getExtension().equals("png")
                       || file.getExtension().equals("img") || file.getExtension().equals("svg")) {
                    numberOfImagesPerDirectory[i] ++;
                }
                if(file.getExtension().equals("mp3")) {
                    numberOfAudiosPerDirectory[i] ++;
                }
                if(file.getExtension().equals("mp4") || file.getExtension().equals("wmv")) {
                    numberOfVideosPerDirectory[i] ++;
                }
            }
            i++;
        }

        FileWriter fileWriter = new FileWriter(Constants.getFileOutPath());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        printWriter.println("The number of files in each directory:");
        for(int j = 0; j < numberOfDirectories; j++) {
            printWriter.println("\t" + listOdDirectories.get(j).getPath() + ": " + numberOfFilesPerDirectory[j]);
        }
        printWriter.println("The number of image files in each directory:");
        for(int j = 0; j < numberOfDirectories; j++) {
            printWriter.println("\t" + listOdDirectories.get(j).getPath() + ": " + numberOfImagesPerDirectory[j]);
        }
        printWriter.println("The number of video files in each directory:");
        for(int j = 0; j < numberOfDirectories; j++) {
            printWriter.println("\t" + listOdDirectories.get(j).getPath() + ": " + numberOfVideosPerDirectory[j]);
        }
        printWriter.println("The number of audio files in each directory:");
        for(int j = 0; j < numberOfDirectories; j++) {
            printWriter.println("\t" + listOdDirectories.get(j).getPath() + ": " + numberOfAudiosPerDirectory[j]);
        }

        printWriter.close();
        bufferedWriter.close();
        fileWriter.close();
    }
    public static void savingDataInFile(String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        MenuDirectories.getListOfDirectories().forEach(directory -> {
            printWriter.println(directory.getPath());
            directory.getListOfFiles()
                    .forEach(file -> printWriter.println(file.getName() + "." + file.getExtension()));
        });
        printWriter.close();
        bufferedWriter.close();
        fileWriter.close();
    }
    public static String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        String extension = fileName.substring(index + 1);
        return extension;
    }
    public static void populateListOfDirectories(String filePath) throws IOException {
        String line;
        Directory directory = null;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        while ((line = bufferedReader.readLine()) != null) {
            if(line.charAt(1) == ':') {
                char symobl = '\\';
                directory = new Directory(line.substring(line.lastIndexOf(symobl) + 1), line);
                MenuDirectories.listOfDirectories.add(directory);
            }
            if(line.charAt(1) != ':') {
                String extension = line.substring(line.lastIndexOf('.') + 1);
                String fileName = line.substring(0, line.lastIndexOf('.'));
                File file = new File(directory.getPath(), extension, fileName);
                directory.getListOfFiles().add(file);
            }
        }
    }

    public static void populateListOfFiles(String filePath) throws IOException {
        String line;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String directoryName = "";
        String directoryPath = "";
        while ((line = bufferedReader.readLine()) != null) {
            int index = -1;
            if(line.charAt(1) == ':') {
                char symobl = '\\';
                index = line.lastIndexOf(symobl);
                directoryPath = line;
                directoryName = line.substring(index + 1);
            }
            if(line.charAt(1) != ':') {
                String extension = line.substring(line.lastIndexOf('.') + 1);
                String fileName = line.substring(0, line.lastIndexOf('.'));
                Directory directory = new Directory(directoryName,directoryPath);
                File file = new File(directory.getPath(),extension,fileName);
                directory.getListOfFiles().add(file);
                MenuFiles.listOfFiles.add(file);
            }
        }
    }

    public static boolean checkFileAlreadyExists(String path, String fileName) {
        int index = fileName.lastIndexOf('.');
        String extension = fileName.substring(index + 1);
        String name = fileName.substring(0, index);
        Directory dir = MenuDirectories.getListOfDirectories().stream()
                .filter(directory -> directory.getPath().equals(path))
                .findFirst().orElse(null);
        assert dir != null;
//        File f = dir.getListOfFiles().stream()
//                .filter(file -> file.getName().equals(name) && file.getExtension().equals(extension))
//                .findAny().orElse(null);
        return dir.getListOfFiles().stream()
                .anyMatch(file -> file.getName().equals(name) && file.getExtension().equals(extension));
    }

    public static boolean checkPathIsCorrect(String path) throws ExceptionIncorrectPath {
        if(path.startsWith(":\\", 1) && path.charAt(path.length() - 1) != '\\') {
            return true;
        } else if (path.startsWith(":\\", 1) && path.length() == 3){
            return true;
        } else if(MenuDirectories.listOfDirectories.stream().anyMatch(directory -> directory.getPath().equals(path))) {
            return true;
        } else {
            throw new ExceptionIncorrectPath("The path you typed dose not have the correct format.\nIt should be"
                    + " partition:\\existingDirectory\\existingDirectory...");
        }
    }

    public static boolean checkDirectoryAlreadyExists(String pathAdd, String name){
        List<Directory> directories = MenuDirectories.getListOfDirectories();

        long numberOfDirectoriesWithTheSameName = directories.stream()
                .filter(directory -> (directory.getName().equals(name) &&
                        directory.getPath().startsWith(pathAdd)))
                .count();
        System.out.println(numberOfDirectoriesWithTheSameName);
        return numberOfDirectoriesWithTheSameName != 0;
    }

    public static boolean checkPathAlreadyExists(String path) {
        List<Directory> directories = MenuDirectories.getListOfDirectories();
        long numberOfDirectoriesWithTheSamePath = directories.stream()
                .filter(dr -> dr.getPath().equals(path))
                .count();
        return numberOfDirectoriesWithTheSamePath != 0;
    }

    public static boolean checkPathAddExists(String path) {
        List<Directory> directories = MenuDirectories.getListOfDirectories();
        long numberOfDirectoriesWithTheSamePath = directories.stream()
                .filter(dr -> dr.getPath().startsWith(path))
                .count();
        return numberOfDirectoriesWithTheSamePath != 0;
    }
}

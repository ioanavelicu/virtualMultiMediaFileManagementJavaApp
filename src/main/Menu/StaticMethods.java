package main.Menu;

import main.Directory.Directory;
import main.Exceptions.ExceptionDirectoryDoesNotExist;
import main.Exceptions.ExceptionIncorrectPath;
import main.File.File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class StaticMethods {
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

package main.Menu;

import main.Constants.Constants;
import main.Directory.Directory;
import main.Exceptions.*;
import main.File.File;
import main.Interfaces.IAdder;
import main.Interfaces.IMover;
import main.Interfaces.IRemover;
import main.Interfaces.IRenamer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuFiles extends AMenu implements IAdder, IRemover, IRenamer, IMover {
    protected static List<File> listOfFiles = new ArrayList<>();
    public MenuFiles() {
        this.options.put(Options.ADD, "1. Add a new file");
        this.options.put(Options.RENAME, "2. Rename file");
        this.options.put(Options.REMOVE, "3. Remove file");
        this.options.put(Options.MOVE, "4. Move file");
        this.options.put(Options.LIST, "5. List files from a directory by path");
        this.options.put(Options.BACK, "6. Back");
    }

    private List<File> getListOfFiles() {
        return listOfFiles;
    }

    @Override
    public void add(String path, String fileName) {
        List<Directory> directories = MenuDirectories.getListOfDirectories();
        int index = fileName.lastIndexOf('.');
        String extension = fileName.substring(index + 1);
        String name = fileName.substring(0, index);
        String directoryName = path.substring(path.lastIndexOf('\\'));
        Directory directory = directories.stream()
                .filter(dr -> dr.getPath().equals(path))
                .findAny().get();
        File file = new File(directory.getPath(), extension, name);
        directory.getListOfFiles().add(file);
        listOfFiles.add(file);
        System.out.println("The new file has been created\n");
    }

    @Override
    public void remove(String path, String fileName) {
        List<Directory> directories = MenuDirectories.getListOfDirectories();
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        String name = fileName.substring(0, fileName.lastIndexOf('.'));
        Directory directory = directories.stream()
                .filter(dr -> dr.getPath().equals(path))
                .findAny().get();
        listOfFiles.removeIf(file -> file.getExtension().equals(extension)
                    && file.getName().equals(name) && file.getRootDirectoryPath().equals(directory.getPath()));
        directory.getListOfFiles().removeIf(file -> file.getName().equals(name)
                && file.getExtension().equals(extension));
        System.out.println("The file has been deleted\n");
    }

    @Override
    public void rename(String path, String newName, String fileRename){
        List<Directory> directories = MenuDirectories.getListOfDirectories();

        String extension = newName.substring(newName.lastIndexOf('.') + 1);
        String name = newName.substring(0, newName.lastIndexOf('.'));

        Directory directory = directories.stream()
                .filter(dr -> dr.getPath().equals(path))
                .findAny().get();
        File file = new File(directory.getPath(), extension, name);

        String oldExtension = fileRename.substring(fileRename.lastIndexOf('.') + 1);
        String oldName = fileRename.substring(0, fileRename.lastIndexOf('.'));

        directory.getListOfFiles().removeIf(f -> f.getName().equals(oldName) &&
                        f.getExtension().equals(oldExtension));
        directory.getListOfFiles().add(file);

        listOfFiles.removeIf(f -> f.getExtension().equals(oldExtension)
                && f.getName().equals(oldName) &&
                f.getRootDirectoryPath().equals(directory.getPath()));
        listOfFiles.add(file);

        System.out.println("The file has been renamed\n");
    }

    @Override
    public void move(String oldPath, String newPath, String fileName) {
        List<Directory> directories = MenuDirectories.getListOfDirectories();

        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        String name = fileName.substring(0, fileName.lastIndexOf('.'));

        Directory oldDirectory = directories.stream()
                .filter(dr -> dr.getPath().equals(oldPath))
                .findAny().get();
        oldDirectory.getListOfFiles().removeIf(f -> f.getName().equals(name) &&
                f.getExtension().equals(extension)
                && f.getRootDirectoryPath().equals(oldDirectory.getPath()));
        listOfFiles.removeIf(f -> f.getExtension().equals(extension)
                && f.getName().equals(name)
                && f.getRootDirectoryPath().equals(oldDirectory.getPath()));

        Directory newDirectory = directories.stream()
                .filter(dr -> dr.getPath().equals(newPath))
                .findAny().orElse(null);
        File file = new File(newDirectory.getPath(), extension, name);
        newDirectory.getListOfFiles().add(file);
        listOfFiles.add(file);

        System.out.println("The file has been moved\n");
    }

    @Override
    void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            this.options.values().stream().sorted().forEach(System.out::println);
            int optiune = scanner.nextInt();
            scanner.nextLine();
            switch (optiune) {
                case 1:
                    System.out.println("\nThe paths in witch you can add a new file:");
                    MenuDirectories.getListOfDirectories().forEach(System.out::println);
                    System.out.println("\nType the path you want to put your file in:");
                    String path = scanner.nextLine();
                    try {
                        StaticMethods.checkPathIsCorrect(path);
                        if(StaticMethods.checkPathAlreadyExists(path)) {
                            System.out.println("Type the name of the new file including the extension:");
                            String fileName = scanner.nextLine();
                            if(Constants.fileHasCorrectExtension(StaticMethods.getExtension(fileName))) {
                                if(StaticMethods.checkFileAlreadyExists(path, fileName)) {
                                    throw new ExceptionFileAlreadyExists("This file already exists in this directory.\n");
                                } else {
                                    add(path, fileName);
                                }
                            } else {
                                throw new ExceptionIncorrectFileExtension("""
                                        The file dose not have the right extension.
                                        It has to be one of these: img, jpeg, png, svg, mp4, mp3, wmv
                                        
                                        """);
                            }
                        } else {
                            throw new ExceptionDirectoryDoesNotExist("The directory you want to add your file in does not exist.\n");
                        }
                    } catch (ExceptionIncorrectPath | ExceptionFileAlreadyExists | ExceptionDirectoryDoesNotExist |
                             ExceptionIncorrectFileExtension e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("\nThe list of files that can be renamed:");
                    listOfFiles.forEach(System.out::println);
                    System.out.println("\nType the path of the file you want to rename");
                    String pathRename = scanner.nextLine();
                    try {
                        StaticMethods.checkPathIsCorrect(pathRename);
                        if(StaticMethods.checkPathAlreadyExists(pathRename)) {
                            System.out.println("Type the file you want ro rename:");
                            String fileRename = scanner.nextLine();
                            if(StaticMethods.checkFileAlreadyExists(pathRename, fileRename)) {
                                System.out.println("Type the new name of the file:");
                                String newName = scanner.nextLine();
                                rename(pathRename, newName, fileRename);
                            } else {
                                throw new ExceptionFileDoesNotExist("The file does not exist.\n");
                            }
                        } else {
                            throw new ExceptionDirectoryDoesNotExist("The directory does not exist.\n");
                        }

                    } catch (ExceptionIncorrectPath | ExceptionDirectoryDoesNotExist | ExceptionFileDoesNotExist e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("\nThe list of files you may choose from:");
                    listOfFiles.forEach(System.out::println);
                    System.out.println("\nType the path of the file you want to remove:");
                    String pathRemove = scanner.nextLine();
                    try {
                        StaticMethods.checkPathIsCorrect(pathRemove);
                        if(StaticMethods.checkPathAlreadyExists(pathRemove)) {
                            System.out.println("Type the file you want to remove:");
                            String fileRemove = scanner.nextLine();
                            if(StaticMethods.checkFileAlreadyExists(pathRemove, fileRemove)) {
                                System.out.println("Are you sure you want to remove this file? Y/N");
                                String answer = scanner.nextLine();
                                if(answer.equalsIgnoreCase("y")) {
                                    remove(pathRemove, fileRemove);
                                }
                            } else {
                                throw new ExceptionFileDoesNotExist("The file does not exist.\n");
                            }
                        } else {
                            throw new ExceptionDirectoryDoesNotExist("The directory does not exist.\n");
                        }
                    } catch (ExceptionIncorrectPath | ExceptionDirectoryDoesNotExist | ExceptionFileDoesNotExist e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("\nType the path of the file you want to move:");
                    listOfFiles.forEach(System.out::println);
                    String oldPath = scanner.nextLine();
                    try {
                        StaticMethods.checkPathIsCorrect(oldPath);
                        if(StaticMethods.checkPathAlreadyExists(oldPath)) {
                            System.out.println("Type the name of the file you want to move:");
                            String fileMove = scanner.nextLine();
                            if(StaticMethods.checkFileAlreadyExists(oldPath, fileMove)) {
                                System.out.println("Type the path where you want to move your file:");
                                String newPath = scanner.nextLine();
                                try {
                                    StaticMethods.checkPathIsCorrect(newPath);
                                    if(StaticMethods.checkPathAlreadyExists(newPath)) {
                                        move(oldPath, newPath, fileMove);
                                    } else {
                                        throw new ExceptionDirectoryDoesNotExist("The directory you want to move the file in does not exist.\n");
                                    }
                                } catch (ExceptionIncorrectPath e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                throw new ExceptionFileDoesNotExist("The file does not exist.\n");
                            }
                        } else {
                            throw new ExceptionDirectoryDoesNotExist("The directory does not exist.\n");
                        }
                    } catch (ExceptionIncorrectPath | ExceptionDirectoryDoesNotExist | ExceptionFileDoesNotExist e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("\nThe list of files paths:");
                    listOfFiles.forEach(System.out::println);
                    break;
                case 6:
                    System.out.println("Backing out...");
                    isRunning = false;
                    Menu.getInstance().run();
                    break;
                default:
                    System.out.println("!! Not a valid option !!");
            }
        }
    }
}

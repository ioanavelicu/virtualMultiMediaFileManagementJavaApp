package main.Menu;

import main.Directory.Directory;
import main.Exceptions.ExceptionDirectoryDoesNotExist;
import main.Exceptions.ExceptionIncorrectPath;
import main.Exceptions.ExceptionTheSameDirectory;
import main.Interfaces.IAdder;
import main.Interfaces.IRemover;
import main.Interfaces.IRenamer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuDirectories extends AMenu implements IAdder, IRemover, IRenamer{
    protected static List<Directory> listOfDirectories = new ArrayList<>();
    public MenuDirectories() {
        this.options.put(Options.ADD, "1. Add a new directory");
        this.options.put(Options.REMOVE, "2. Remove directory");
        this.options.put(Options.RENAME, "3. Rename directory");
        this.options.put(Options.LIST, "4. List directories");
        this.options.put(Options.BACK, "5. Back");
    }

    private void writeInFile(String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        listOfDirectories.forEach(directory -> printWriter.println(directory.getPath()));
        printWriter.close();
        bufferedWriter.close();
        fileWriter.close();
    }

    protected static List<Directory> getListOfDirectories() {
        return listOfDirectories;
    }

    @Override
    public void add(String pathAdd, String name) throws ExceptionTheSameDirectory {
        if(pathAdd.length() == 3) {
            listOfDirectories.add(new Directory(name, pathAdd + name));
        } else {
            listOfDirectories.add(new Directory(name, pathAdd + '\\' + name));
        }
        System.out.println("The new directory has been created.\n");
    }

    @Override
    public void remove(String pathRemove, String name) throws ExceptionDirectoryDoesNotExist {
        listOfDirectories.removeIf(directory -> directory.getPath().equals(pathRemove));
        listOfDirectories.removeIf(directory -> directory.getPath().startsWith(pathRemove));
        MenuFiles.listOfFiles.removeIf(file -> file.getRootDirectoryPath().startsWith(pathRemove));
        System.out.println("The directory has been deleted.\n");
    }

    @Override
    public void rename(String pathRename, String newName, String oldName) throws ExceptionDirectoryDoesNotExist {
        List<Directory> directories = getListOfDirectories();
//        long numberOfDirectoriesWithTheSamePath = directories.stream()
//                .filter(dr -> dr.getPath().equals(pathRename))
//                .count();
        listOfDirectories.stream()
                .filter(directory -> directory.getPath().startsWith(pathRename))
                .forEach(directory -> {
                    directory.setName(newName);
                    char symbol = '\\';
                    int index = pathRename.lastIndexOf(symbol);
                    String oldPath = directory.getPath().substring(pathRename.length());
//                    System.out.println(oldPath);
                    String newPath = pathRename.substring(0,index) + "\\" + newName + oldPath;
                    directory.setPath(newPath);
                    directory.getListOfFiles().forEach(file -> file.setRootDirectoryPath(directory.getPath()));
                });
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            this.options.values().stream().sorted().forEach(System.out::println);
            int optiune = scanner.nextInt();
            scanner.nextLine();
            switch (optiune) {
                case 1:
                    System.out.println("Type the path of the new directory: ");
                    String pathAdd = scanner.nextLine();
                    try {
                        StaticMethods.checkPathIsCorrect(pathAdd);
                        if(StaticMethods.checkPathAddExists(pathAdd)) {
                            System.out.println("Type the name of the new directory:");
                            String name = scanner.nextLine();
                            if(StaticMethods.checkDirectoryAlreadyExists(pathAdd, name)) {
                                throw new ExceptionTheSameDirectory("There is already a directory named that way in the path you mentioned.");
                            } else {
                                add(pathAdd, name);
                            }
                        } else {
                            throw new ExceptionIncorrectPath("The path does not exist");
                        }
                    } catch (ExceptionIncorrectPath | ExceptionTheSameDirectory e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("The list of directories you may choose from:");
                    listOfDirectories.forEach(System.out::println);
                    System.out.println("Type the path of the directory you want to remove:");
                    String pathRemove = scanner.nextLine();
                    try {
                        StaticMethods.checkPathIsCorrect(pathRemove);
                        if(StaticMethods.checkPathAlreadyExists(pathRemove)) {
                            System.out.println("Are you sure you want to remove this directory? Y/N");
                            String answer = scanner.nextLine();
                            if(answer.equalsIgnoreCase("y")) {
                                remove(pathRemove, "");
                            }
                        } else {
                            throw new ExceptionDirectoryDoesNotExist("The directory you want to remove does not exist.");
                        }
                    } catch (ExceptionIncorrectPath | ExceptionDirectoryDoesNotExist e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("The list of directories that can be renamed:");
                    listOfDirectories.forEach(System.out::println);
                    System.out.println("Type the path of the directory you want to rename");
                    String pathRename = scanner.nextLine();
                    try {
                        StaticMethods.checkPathIsCorrect(pathRename);
                        System.out.println("Type the new name of the directory:");
                        String newName = scanner.nextLine();
                        if(StaticMethods.checkPathAlreadyExists(pathRename)) {
                            rename(pathRename, newName, "");
                        } else {
                            throw new ExceptionDirectoryDoesNotExist("The directory you want to rename does not exist.");
                        }
                    } catch (ExceptionIncorrectPath | ExceptionDirectoryDoesNotExist e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("The list of directories paths:");
                    listOfDirectories = getListOfDirectories();
                    listOfDirectories.forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("Backing out...\n");
                    isRunning = false;
                    Menu.getInstance().run();
                    break;
                default:
                    System.out.println("Not a valid option");
            }
        }
    }
}

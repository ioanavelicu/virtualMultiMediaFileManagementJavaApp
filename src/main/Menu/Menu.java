package main.Menu;

import main.Constants.Constants;
import main.Exceptions.ExceptionTheSameDirectory;

import javax.print.attribute.standard.MediaName;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu extends AMenu{
    private static Menu instance = null;

    private Menu() {
        this.options.put(Options.MANAGEDIRECTORIES,"1. Manage directories");
        this.options.put(Options.MANAGEFILES,"2. Manage files");
        this.options.put(Options.CHANGELANGUAGE,"3. Change language");
        this.options.put(Options.EXIT,"4. Exit");
    }

    public static Menu getInstance() {
        if(instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    private void writeInFile(String filePath) throws IOException {
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
                    System.out.println("DIRECTORIES OPTIONS");
                    MenuDirectories menuDirectories = new MenuDirectories();
                    menuDirectories.run();
                    break;
                case 2:
                    System.out.println("FILES OPTIONS");
                    MenuFiles menuFiles = new MenuFiles();
                    menuFiles.run();
                    break;
                case 3:
                    System.out.println("3");
                    break;
                case 4:
                    try {
                        writeInFile(Constants.getFilePath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Saving data...\nExiting...");
                    System.exit(0);
                    isRunning = false;
                    break;
                default:
                    System.out.println("default");
            }
        }
    }
}

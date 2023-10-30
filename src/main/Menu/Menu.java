package main.Menu;

import main.Constants.Constants;

import java.io.IOException;
import java.util.Scanner;

public class Menu extends AMenu{
    private static Menu instance = null;

    private Menu() {
        this.options.put(Options.MANAGEDIRECTORIES,"1. Manage directories");
        this.options.put(Options.MANAGEFILES,"2. Manage files");
        this.options.put(Options.STATISTICS,"3. Get statistics");
        this.options.put(Options.CHANGELANGUAGE,"4. Change language");
        this.options.put(Options.EXIT,"5. Exit");
    }

    public static Menu getInstance() {
        if(instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            try {
                StaticMethods.getStatisticsFromApp();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.options.values().stream().sorted().forEach(System.out::println);
            int optiune = scanner.nextInt();
            scanner.nextLine();
            switch (optiune) {
                case 1:
                    System.out.println("\n**Directories options:");
                    MenuDirectories menuDirectories = new MenuDirectories();
                    menuDirectories.run();
                    break;
                case 2:
                    System.out.println("\n**Files options:");
                    MenuFiles menuFiles = new MenuFiles();
                    menuFiles.run();
                    break;
                case 3:
                    try {
                        StaticMethods.getStatisticsFromApp();
                        System.out.println("\n**Check the file Statistics.txt\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 4:
                    System.out.println("4");
                    break;
                case 5:
                    try {
                        StaticMethods.savingDataInFile(Constants.getFilePath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Saving data...\nExiting...");
                    System.out.println("*** GOOD BYE ***");
                    System.exit(0);
                    isRunning = false;
                    break;
                default:
                    System.out.println("!! Not a valid option !!");
            }
        }
    }
}

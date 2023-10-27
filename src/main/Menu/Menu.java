package main.Menu;

import main.Exceptions.ExceptionTheSameDirectory;

import javax.print.attribute.standard.MediaName;
import java.io.IOException;
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

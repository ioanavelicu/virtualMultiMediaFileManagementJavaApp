package main;

import main.Constants.Constants;
import main.Menu.Menu;
import main.Menu.StaticMethods;

import java.io.IOException;

/**
 * Clasa care contine metoda main()
 * Se preia instan de meniu principal Menu, se populeaza listele de directoare si fisiere folosind metodele
 * populateListOfDirectories() si pupulateListOfFiles() din clasa StaticMethods si se apeleaza metoda run() pentru a porni
 * meniul
 * @see StaticMethods
 * @see Constants
 * @see Menu*/
public class Main {
    public static void main(String[] args) {
        System.out.println("*** WELCOME ***");
        Menu menu = Menu.getInstance();
        try {
            StaticMethods.populateListOfDirectories(Constants.getFilePath());
            StaticMethods.populateListOfFiles(Constants.getFilePath());
            menu.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
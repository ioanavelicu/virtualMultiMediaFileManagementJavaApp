package main;

import main.Constants.Constants;
import main.Menu.Menu;
import main.Menu.StaticMethods;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        String language, country;
//        language = new String("en");
//        country = new String("US");
//        Locale locale = new Locale(language, country);
//        ResourceBundle properties = ResourceBundle.getBundle("messages", locale);
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
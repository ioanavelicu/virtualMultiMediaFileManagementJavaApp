package main.Menu;

import main.Constants.Constants;

import java.io.IOException;
import java.util.Scanner;

/**
 * Clasa extinde clasa abstracta AMenu pentru a putea respecta structura unui meniu
 * Este Singleton, intrucat este nevoie doar de o instanta de meniu la nivelul aplicatiei
 * Reprezinta meniul principal al aplicatiei*/
public class Menu extends AMenu{
    /**
     * Declararea instantei de Menu si initializarea acesteia cu null*/
    private static Menu instance = null;

    /**
     * Constructor default
     * Este privat pentru a respecta regulile de implementare ale design patternului Singleton
     * In constructor sunt adaugate optiunile pe care le va avea meniul*/
    private Menu() {
        this.options.put(Options.MANAGEDIRECTORIES,"1. Manage directories");
        this.options.put(Options.MANAGEFILES,"2. Manage files");
        this.options.put(Options.STATISTICS,"3. Get statistics");
        this.options.put(Options.EXIT,"4. Exit");
    }

    /**
     * Metoda pentru returnarea instantei de Menu
     * Daca instanta nu a mai fost utilizata, aceasta este intai initializata
     * @return instanta de menu*/
    public static Menu getInstance() {
        if(instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    /**
     * Metoda care implementeaza functionalitatea meniului prin structura SWITCH prin care se poate alege ce optiune
     * se va executa
     * Meniul ruleaza pana cand se alege optiunea de iesire, deoarece se foloseste structura repetitiva WHILE ce are un
     * paramentru cu valoarea TRUE la inceputul rularii, iar acesta devine FALSE la alegerea optiunii EXIT
     * Pentru a selecta optiunea, se tasteaza numarul acesteia si se apasa tasta ENTER
     * Raspunsurile sunt preluate de la tastatura printr-un obiect de tip Scanner
     * Optiunile sunt afisate din lista de optiuni a meniului
     *
     * PRIMA OPTIUNE - optiunuile pentru directoare
     * Este preluata instanta de MenuDirectories care reprezinta meniul cu optiunile pentru directoare si este
     * apelata metoda run() pentru acest meniu
     * @see MenuDirectories
     *
     * A DOUA OPTIUNE - optiunile pentru fisiere
     * Este preluata instanta de MenuFiles care reprezinta meniul cu optiunile pentru fisiere si este apelata metoda
     * run() pentru aceste meniu
     * @see MenuFiles
     *
     * A TREIA OPTIUNE - statistici pe baza directoarelor si fisierelor din acestea
     * Prin apelarea metodei getStatistics() din clasa StaticMehods
     * @see StaticMethods
     * Statisticile sunt apoi scrise in fisierul text Statistics.txt
     *
     * A CINCEA OPTIUNE - inchiderea aplicatiei
     * La inchiderea aplicatiei, datele sunt salvate in fisierul text cu date de intratre pentru a fi disponibile la
     * urmatoarea rulare a aplicatiei
     * Salvarea se efectueaza prin apelarea metodei savingDataInFile() din clasa StaticMethods ce are ca parametru calea
     * absoluta a fisierului in care vor fi salvate datele preluata din clasa Constants
     * @see Constants
     * @see StaticMethods
     *
     * In cazul in care este introdus de la tastatura un numar care nu reprezinta nicio optiune, se intra in cazul
     * default*/
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\nChoose one option:");
            this.options.values().stream().sorted().forEach(System.out::println);
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    System.out.println("\n**Directories options:");
                    MenuDirectories menuDirectories = MenuDirectories.getInstance();
                    menuDirectories.run();
                    break;
                case 2:
                    System.out.println("\n**Files options:");
                    MenuFiles menuFiles = MenuFiles.getInstance();
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

package main.Menu;

import main.Directory.Directory;
import main.Exceptions.ExceptionDirectoryDoesNotExist;
import main.Exceptions.ExceptionIncorrectPath;
import main.Exceptions.ExceptionDirectoryAlreadyExists;
import main.Exceptions.ExceptionInvalidName;
import main.Interfaces.IAdder;
import main.Interfaces.IRemover;
import main.Interfaces.IRenamer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clasa extinde clasa abstracta AMenu pentru a putea respecta structura unui meniu
 * Este Singleton, intrucat este nevoie doar de o instanta de meniu la nivelul aplicatiei
 * Reprezinta meniul cu optiunile pentru directoare*/
public class MenuDirectories extends AMenu implements IAdder, IRemover, IRenamer {
    /**
     * Declararea instantei de MenuDirectories si initializarea acesteia cu null*/
    private static MenuDirectories instance = null;

    /**
     * Lista de directoare care va fi populata din fisierul cu date de intrare*/
    protected static List<Directory> listOfDirectories = new ArrayList<>();

    /**
     * Constructor default
     * Este privat pentru a respecta regulile de implementare ale design patternului Singleton
     * In constructor sunt adaugate optiunile pe care le va avea meniul*/
    private MenuDirectories() {
        this.options.put(Options.ADD, "1. Add a new directory");
        this.options.put(Options.REMOVE, "2. Remove directory");
        this.options.put(Options.RENAME, "3. Rename directory");
        this.options.put(Options.LIST, "4. List directories");
        this.options.put(Options.BACK, "5. Back");
    }

    /**
     * Metoda pentru returnarea instantei de Menu
     * Daca instanta nu a mai fost utilizata, aceasta este intai initializata
     * @return instanta de MenuDirectories*/
    public static MenuDirectories getInstance() {
        if(instance == null) {
            instance = new MenuDirectories();
        }
        return instance;
    }

    /**
     * Metoda pentru returnarea listei de directoare de la nivelul meniului*/
    protected static List<Directory> getListOfDirectories() {
        return listOfDirectories;
    }

    /**
     * Metoda pentru adaugarea unui nou director in lista de directoare
     * @param name reprezinta numele noului director
     * @param pathAdd reprezinta calea unde va fi introdus noul director*/
    @Override
    public void add(String pathAdd, String name){
        if(pathAdd.length() == 3) {
            listOfDirectories.add(new Directory(name, pathAdd + name));
        } else {
            listOfDirectories.add(new Directory(name, pathAdd + '\\' + name));
        }
        System.out.println("The new directory has been created.\n");
    }

    /**
     * Metoda pentru stergerea unui director din lista de directoare
     * @param pathRemove reprezinta calea absoluta a directorului care urmeaza a fi sters
     * Directorul este sters si din lista de directoare, iar apoi se sterg si fisierele aferene acestui director
     * din lista de fisiere*/
    @Override
    public void remove(String pathRemove, String name) {
        listOfDirectories.removeIf(directory -> directory.getPath().equals(pathRemove));
        listOfDirectories.removeIf(directory -> directory.getPath().startsWith(pathRemove + '\\'));
        MenuFiles.listOfFiles.removeIf(file -> file.getRootDirectoryPath().startsWith(pathRemove));
        System.out.println("The directory has been deleted.\n");
    }

    /**
     * Metoda pentru redenumire unui director din lista de directoare
     * @param pathRename reprezinta calea unde se afla directorul care trebuie sa fie redenumite
     * @param newName reprezinta numele nou al directorului
     * Directorul este redenumit atat in lista de directoare din cadrul meniului, cat si in cadrul fisierelor din lista
     * de fisiere ce il au ca director sursa*/
    @Override
    public void rename(String pathRename, String newName, String oldName) throws ExceptionDirectoryDoesNotExist {
        listOfDirectories.stream()
                .filter(directory -> directory.getPath().startsWith(pathRename))
                .forEach(directory -> {
                    char symbol = '\\';
                    int index = pathRename.lastIndexOf(symbol);
                    String oldPath = directory.getPath().substring(pathRename.length());
                    String newPath = pathRename.substring(0,index) + "\\" + newName + oldPath;
                    directory.getListOfFiles().forEach(file -> file.setRootDirectoryPath(newPath));
                    if (oldPath.startsWith("\\")) {
                        MenuFiles.listOfFiles.stream()
                                .filter(file -> file.getRootDirectoryPath().startsWith(pathRename))
                                .forEach(file -> file.setRootDirectoryPath(newPath));
                    }
                    if (oldPath.isEmpty()) {
                        MenuFiles.listOfFiles.stream()
                                .filter(file -> file.getRootDirectoryPath().equals(pathRename))
                                .forEach(file -> file.setRootDirectoryPath(newPath));
                        directory.setName(newName);
                    }
                    directory.setPath(newPath);
                });

    }

    /**
     * Metoda care implementeaza functionalitatea meniului prin structura SWITCH prin care se poate alege ce optiune
     * se va executa
     * Meniul ruleaza pana cand se alege optiunea de iesire, deoarece se foloseste structura repetitiva WHILE ce are un
     * paramentru cu valoarea TRUE la inceputul rularii, iar acesta devine FALSE la alegerea optiunii BACK
     * Pentru a selecta optiunea, se tasteaza numarul acesteia si se apasa tasta ENTER
     * Raspunsurile sunt preluate de la tastatura printr-un obiect de tip Scanner
     * Optiunile sunt afisate din lista de optiuni a meniului
     *
     * PRIMA OPTIUNE - adaugarea unui nou director
     * Dupa ce se preia de la tastatura calea in care se doreste sa fie introdus noul director, se fac doua verificari:
     * calea are formatul corect si calea exista. In cazul in care calea nu exista, se arunca exceptia
     * ExceptionDirectoryDoesNotExist
     * @see ExceptionDirectoryDoesNotExist
     * Pasul urmator este introducerea de la tastatura a numelui noului director, uramand sa se verifice daca directorul
     * deja exista folosind metoda checkDirectoryAlreadyExists
     * @see StaticMethods pentru metodele de verificare checkPathIsCorrect si checkPathAddExists
     * In cazul in care exista deja directorul in calea aleasa, se va arunca exceptia ExceptionTheSameDirectory
     * @see ExceptionDirectoryAlreadyExists
     * Daca se respecta toate conditiile, atunci se va apela metoda add()
     *
     * A DOUA OPTIUNE - stergerea unui director
     * Se afiseaza lista de directoare astfel incat sa se poate selecta cu usurinta directorul care urmeaza a fi sters
     * Se preia apoi de la tastatura calea directorului care va fi sters. Calea preluata de la tastatura este verificata
     * daca are formatul corect si daca exista. In cazul in care calea nu exista, se arunca exceptia
     * ExceptionDirectoryDoesNotExist
     * @see ExceptionDirectoryDoesNotExist
     * In cazul in care se preia de la tastatura ca se doreste stergerea, se apeleaza metoda remove()
     *
     * A TREIE OPTIUNE - rednumirea unui director
     * Se afiseaza lista de directoare astfel incat sa se poate selecta cu usurinta directorul care urmeaza a fi redenumit
     * Se preia apoi de la tastatura calea directorului care va fi redenumit. Calea preluata de la tastatura este verificata
     * daca are formatul corect si daca exista. In cazul in care calea nu exista, se arunca exceptia
     * ExceptionDirectoryDoesNotExist
     * @see ExceptionDirectoryDoesNotExist
     * Se preia apoi de la tastatura noul nume al directorului si se verifica daca directorul redenumit deja exista. In
     * cazul in care exista, se va arunca exceptia ExceptionTheSameDirectory, altfel se apeleaza metoda rename()
     * @see ExceptionDirectoryAlreadyExists
     *
     * A PATRA OPTIUNE - vizualizarea listei de directoare
     * Se preia lista de directoare cu metoda getListOfDirectories() si se afiseaza
     *
     * A CINCEA OPTIUNE - inapoi
     * Intoarce aplicatia in meniul principal prin apelarea metodei run() pe instanta de Menu si se opreste rularea
     * meniului pentru directoare prin setarea pe FALSE a parametrului din structura repetitiva WHILE
     *
     * In cazul in care este introdus de la tastatura un numar care nu reprezinta nicio optiune, se intra in cazul
     * default*/
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Choose one option:");
            this.options.values().stream().sorted().forEach(System.out::println);
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    System.out.println("\nType the path of the new directory: ");
                    String pathAdd = scanner.nextLine();
                    try {
                        StaticMethods.checkPathIsCorrect(pathAdd);
                        if(StaticMethods.checkPathAlreadyExists(pathAdd)) {
                            System.out.println("Type the name of the new directory:");
                            String name = scanner.nextLine();
                            if(StaticMethods.checkDirectoryAlreadyExists(pathAdd, name)) {
                                throw new ExceptionDirectoryAlreadyExists("There is already a directory named that way in the path you mentioned.\n");
                            } else {
                                add(pathAdd, name);
                            }
                        } else {
                            throw new ExceptionDirectoryDoesNotExist("The path does not exist\n");
                        }
                    } catch (ExceptionDirectoryDoesNotExist | ExceptionDirectoryAlreadyExists | ExceptionIncorrectPath e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("\nThe list of directories you may choose from:");
                    listOfDirectories.forEach(System.out::println);
                    System.out.println("\nType the path of the directory you want to remove:");
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
                            throw new ExceptionDirectoryDoesNotExist("The directory you want to remove does not exist.\n");
                        }
                    } catch (ExceptionIncorrectPath | ExceptionDirectoryDoesNotExist e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    //daca pun la nume "" merge, nu ar trebui
                    System.out.println("\nThe list of directories that can be renamed:");
                    listOfDirectories.forEach(System.out::println);
                    System.out.println("\nType the path of the directory you want to rename");
                    String pathRename = scanner.nextLine();
                    try {
                        StaticMethods.checkPathIsCorrect(pathRename);
                        if(StaticMethods.checkPathAlreadyExists(pathRename)) {
                            System.out.println("Type the new name of the directory:");
                            String newName = scanner.nextLine();
                            if(!newName.isBlank()) {
                                if(StaticMethods.checkDirectoryAlreadyExists(pathRename, newName)) {
                                    throw new ExceptionDirectoryAlreadyExists("There is already a directory named that way in the path you mentioned.\n");
                                } else {
                                    rename(pathRename, newName, "");
                                }
                            } else {
                                throw new ExceptionInvalidName("The name is not valid!\n");
                            }
                        } else {
                            throw new ExceptionDirectoryDoesNotExist("The directory you want to rename does not exist.\n");
                        }
                    } catch (ExceptionIncorrectPath | ExceptionDirectoryDoesNotExist | ExceptionDirectoryAlreadyExists |
                             ExceptionInvalidName e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("\nThe list of directories paths:");
                    listOfDirectories = getListOfDirectories();
                    listOfDirectories.forEach(System.out::println);
                    System.out.println("\n");
                    break;
                case 5:
                    System.out.println("\nBacking out...");
                    isRunning = false;
                    Menu.getInstance().run();
                    break;
                default:
                    System.out.println("!! Not a valid option !!\n");
            }
        }
    }
}

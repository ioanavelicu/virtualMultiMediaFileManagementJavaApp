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

/**
 * Clasa extinde clasa abstracta AMenu pentru a putea respecta structura unui meniu
 * Este Singleton, intrucat este nevoie doar de o instanta de meniu la nivelul aplicatiei
 * Reprezinta meniul cu optiunile pentru fisiere*/
public class MenuFiles extends AMenu implements IAdder, IRemover, IRenamer, IMover {
    /**
     * Declararea instantei de MenuFiles si initializarea acesteia cu null*/
    private static MenuFiles instance = null;

    /**
     * Lista de fisiere care va fi populata din fisierul cu date de intrare*/
    protected static List<File> listOfFiles = new ArrayList<>();

    /**
     * Constructor default
     * Este privat pentru a respecta regulile de implementare ale design patternului Singleton
     * In constructor sunt adaugate optiunile pe care le va avea meniul*/
    private MenuFiles() {
        this.options.put(Options.ADD, "1. Add a new file");
        this.options.put(Options.REMOVE, "2. Remove file");
        this.options.put(Options.RENAME, "3. Rename file");
        this.options.put(Options.MOVE, "4. Move file");
        this.options.put(Options.LIST, "5. List files from a directory by path");
        this.options.put(Options.BACK, "6. Back");
    }

    /**
     * Metoda pentru returnarea instantei de Menu
     * Daca instanta nu a mai fost utilizata, aceasta este intai initializata
     * @return instanta de MenuFiles*/
    public static MenuFiles getInstance() {
        if(instance == null) {
            instance = new MenuFiles();
        }
        return instance;
    }

    /**
     * Metoda pentru returnarea listei de fisiere de la nivelul meniului*/
    private List<File> getListOfFiles() {
        return listOfFiles;
    }

    /**
     * Metoda pentru adaugarea unui nou fisier
     * @param path reprezinta calea absoluta a directorului in care va fi adaugat fisierul
     * @param fileName reprezinta numele noului fisier
     * Dupa ce este creat, fisierul este adaugat atat in lista de fisiere a directorului, cat si in lista de fisiere
     * de la nivelul meniului*/
    @Override
    public void add(String path, String fileName) {
        List<Directory> directories = MenuDirectories.getListOfDirectories();
        int index = fileName.lastIndexOf('.');
        String extension = fileName.substring(index + 1);
        String name = fileName.substring(0, index);
//        String directoryName = path.substring(path.lastIndexOf('\\'));
        Directory directory = directories.stream()
                .filter(dr -> dr.getPath().equals(path))
                .findAny().get();
        File file = new File(directory.getPath(), extension, name);
        directory.getListOfFiles().add(file);
        listOfFiles.add(file);
        System.out.println("The new file has been created\n");
    }

    /**
     * Metoda pentru stergerea unui fisier
     * @param path reprezinta calea absoluta a directorului in care este fisierul de sters
     * @param fileName reprezinta numele fisierului care urmeaza sa fie sters
     * Fisierul este sters atat din lista de fisiere de la nivelul meniului, cat si din lista de fisiere a directorului
     * in care se afla*/
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

    /**
     * Metoda de redenumire a unui fisier
     * @param path reprezinta calea absoluta a directorului in care se afla fisierul
     * @param newName reprezinta noul nume al fisierului
     * @param fileRename reprezinta numele fisierului care trebuie schimbat
     * Fisierul este redenumit atat in lista de fisiere de la nivelul meniului, cat si in lista de fisiere a
     * directorului in care se afla*/
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

    /**
     * Metoda pentru mutarea unui fisier
     * @param oldPath reprezinta calea absoluta a directorului in care se gaseste fisierul inainte de mutare
     * @param newPath reprezinta calea absoluta a directorului in care va fi mutat fisierul
     * @param fileName reprezinta numele fisierului care va fi mutat*/
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

    /**
     * Metoda care implementeaza functionalitatea meniului prin structura SWITCH prin care se poate alege ce optiune
     * se va executa
     * Meniul ruleaza pana cand se alege optiunea de iesire, deoarece se foloseste structura repetitiva WHILE ce are un
     * paramentru cu valoarea TRUE la inceputul rularii, iar acesta devine FALSE la alegerea optiunii BACK
     * Pentru a selecta optiunea, se tasteaza numarul acesteia si se apasa tasta ENTER
     * Raspunsurile sunt preluate de la tastatura printr-un obiect de tip Scanner
     * Optiunile sunt afisate din lista de optiuni a meniului
     *
     * PRIMA OPTIUNE - adaugarea unui nou fisier
     * Initial, se afiseaza lista cu caile directoarelor, astfel incat sa se poata alege cu usurinta calea in care sa
     * se introduca noul fisier. Dupa ce se preia de la tastatura calea in care se doreste sa fie introdus noul fisier,
     * se fac doua verificari:
     * calea are formatul corect si calea exista. In cazul in care calea nu exista, se arunca exceptia
     * ExceptionDirectoryDoesNotExist
     * @see ExceptionDirectoryDoesNotExist
     * Pasul urmator este introducerea de la tastatura a numelui noului fisier, uramand sa se verifice daca acesta
     * deja exista in directorul respectiv folosind metoda checkFileAlreadyExists() si sa se verifice daca fisierul are
     * una dintre extensiile predefinite de aplicatie folosind metoda checkFileHasCorrectExtension()
     * @see Constants pentru vectorul de extensii
     * In cazul in care fisierul nu are o extensie corecta, se va arunca exceptia ExceptionIncorrectFileExtension
     * @see ExceptionIncorrectFileExtension
     * In cazul in care exista deja fisierul in calea aleasa, se va arunca exceptia ExceptionFileAlreadyExists
     * @see ExceptionFileAlreadyExists
     * Daca se respecta toate conditiile, atunci se va apela metoda add()
     *
     * A DOUA OPTIUNE - stergerea unui fisier
     * Se afiseaza lista cu caile directoarelor astfel incat sa se poate selecta cu usurinta calea in care se afla
     * fisierul care urmeaza a fi sters. Se preia apoi de la tastatura calea directorului in care se afla fisierul
     * care va fi sters. Calea preluata de la tastatura este verificata daca are formatul corect si daca exista.
     * In cazul in care calea nu exista, se arunca exceptia ExceptionDirectoryDoesNotExist
     * @see ExceptionDirectoryDoesNotExist
     * Pasul urmator este introducerea de la tastatura a numelui fisierului, uramand sa se verifice daca acesta
     * deja exista in directorul respectiv folosind metoda checkFileAlreadyExists() si sa se verifice daca fisierul are
     * una dintre extensiile predefinite de aplicatie folosind metoda checkFileAlreadyExists()
     * @see Constants pentru vectorul de extensii
     * In cazul in care fisierul nu are o extensie corecta, se va arunca exceptia ExceptionIncorrectFileExtension
     * @see ExceptionIncorrectFileExtension
     * In cazul in care nu exista fisierul in calea aleasa, se va arunca exceptia ExceptionFileDoesNotExist
     * @see ExceptionFileDoesNotExist
     * Daca se respecta toate conditiile, atunci se va apela metoda remove()
     *
     * A TREIE OPTIUNE - rednumirea unui fisier
     * Se afiseaza lista de directoare astfel incat sa se poate selecta cu usurinta calea in care se afla fisierul care
     * urmeaza a fi sters. Se preia apoi de la tastatura calea directorului in care se afla fisierul care va fi
     * redenumit. Calea preluata de la tastatura este verificata daca are formatul corect si daca exista.
     * In cazul in care calea nu exista, se arunca exceptia ExceptionDirectoryDoesNotExist
     * @see ExceptionDirectoryDoesNotExist
     * Pasul urmator este introducerea de la tastatura a numelui fisierului care va fi redenumit, uramand sa se verifice
     * daca acesta deja exista in directorul respectiv folosind metoda checkFileAlreadyExists() si sa se verifice daca
     * fisierul are una dintre extensiile predefinite de aplicatie folosind metoda checkFileAlreadyExists()
     * @see Constants pentru vectorul de extensii
     * In cazul in care fisierul nu are o extensie corecta, se va arunca exceptia ExceptionIncorrectFileExtension
     * @see ExceptionIncorrectFileExtension
     * In cazul in care nu exista fisierul in calea aleasa, se va arunca exceptia ExceptionFileDoesNotExist
     * @see ExceptionFileDoesNotExist
     * Daca se respecta toate conditiile, atunci se va apela metoda rename()
     *
     * A PATRA OPTIUNE - mutarea unui fisier
     * Se afiseaza lista de directoare astfel incat sa se poate selecta cu usurinta calea in care se afla fisierul care
     * urmeaza a fi mutat. Se preia apoi de la tastatura calea directorului in care se afla fisierul care va fi
     * mutat. Calea preluata de la tastatura este verificata daca are formatul corect si daca exista.
     * In cazul in care calea nu exista, se arunca exceptia ExceptionDirectoryDoesNotExist
     * @see ExceptionDirectoryDoesNotExist
     * Pasul urmator este introducerea de la tastatura a numelui fisierului care va fi redenumit, uramand sa se verifice
     * daca acesta deja exista in directorul respectiv folosind metoda checkFileAlreadyExists() si sa se verifice daca
     * fisierul are una dintre extensiile predefinite de aplicatie folosind metoda checkFileAlreadyExists()
     * @see Constants pentru vectorul de extensii
     * In cazul in care fisierul nu are o extensie corecta, se va arunca exceptia ExceptionIncorrectFileExtension
     * @see ExceptionIncorrectFileExtension
     * In cazul in care nu exista fisierul in calea aleasa, se va arunca exceptia ExceptionFileDoesNotExist
     * @see ExceptionFileDoesNotExist
     * Urmatorul pas consta in preluarea de la tastatura a caii absolute a directorului unde urmeaza sa fie mutat
     * fisierul. Calea preluata de la tastatura este verificata daca are formatul corect si daca exista. In cazul in
     * care calea nu exista, se arunca exceptia ExceptionDirectoryDoesNotExist
     * @see ExceptionDirectoryDoesNotExist
     * Daca se respecta toate conditiile, atunci se va apela metoda rename()
     *
     * A CINCEA OPTIUNE - vizualizarea listei de directoare
     * Se preia lista de fisiere cu metoda getListOfFiles() si se afiseaza
     *
     * A SASEA OPTIUNE - inapoi
     * Intoarce aplicatia in meniul principal prin apelarea metodei run() pe instanta de Menu si se opreste rularea
     * meniului pentru directoare prin setarea pe FALSE a parametrului din structura repetitiva WHILE
     *
     * In cazul in care este introdus de la tastatura un numar care nu reprezinta nicio optiune, se intra in cazul
     * default*/
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
                            if(StaticMethods.checkFileHasCorrectExtension(StaticMethods.getExtension(fileName))) {
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
                case 3:
                    System.out.println("\nThe list of files that can be renamed:");
                    getListOfFiles().forEach(System.out::println);
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
                case 4:
                    //afiseaza caile eligibile
                    System.out.println("\nThe list of files that can be moved:");
                    listOfFiles.forEach(System.out::println);
                    System.out.println("\nType the path of the file you want to move:");
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
                    listOfFiles = getListOfFiles();
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

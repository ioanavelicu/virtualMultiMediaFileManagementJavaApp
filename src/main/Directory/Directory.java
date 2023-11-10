package main.Directory;

import main.File.File;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care defineste caracteristicile un obiect de tip Directory
 * Contine costructor default si cu parametri, metode accesor si metoda toString()*/
public class Directory {
    /**
     * Atribut care reprezinta calea absoluta a directorului*/
    private String path;

    /**
     * Atribut care reprezinta numele directorului*/
    private String name;

    /**
     * Atribut care reprezinta lista de fisiere care exista in director*/
    private List<File> listOfFiles;

    /**
     * Constructor default*/
    public Directory() {
    }

    /**
     * Constructor cu doi parametrii
     * @param name reprezinta numele directorului care va fi creat
     * @param path reprezinta calea absoluta unde va fi introdus obiectul direcor
     * Se initializaeaza lista de fisiere a directorului cu new ArrayList() pentru a evita eventuale erori*/
    public Directory(String name, String path) {
        this.path = path;
        this.name = name;
        this.listOfFiles = new ArrayList<>();
    }

    /**
     * Metoda accesor pentru returnarea listei de fisiere a unui director*/
    public List<File> getListOfFiles() {
        return listOfFiles;
    }

    /**
     * Metoda accesor pentru setarea listei de fisiere a unui director*/
    public void setListOfFiles(List<File> listOfFiles) {
        this.listOfFiles = listOfFiles;
    }

    /**
     * Metoda accesor pentru returnarea caii absolute a directorului*/
    public String getPath() {
        return path;
    }

    /**
     * Metoda accesor pentru returnarea numelui directorului*/
    public String getName() {
        return name;
    }

    /**
     * Metoda accesor pentru setarea caii absolute a unui director*/
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Metoa accesro pentru setarea numelui unui director*/
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metoda toString() pentru afisarea la consoloa a unui obiect de tip Directory*/
    @Override
    public String toString() {
        return "Directory details: " +
                "path: " + path +
                ", name: " + name ;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null || getClass() != obj.getClass()) {
//            return false;
//        }
//        Directory other = (Directory) obj;
//        return name.equals(other.name) && Objects.equals(path, other.path);
//    }
}

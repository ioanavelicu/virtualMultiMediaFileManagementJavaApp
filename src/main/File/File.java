package main.File;

/**
 * Clasa care defineste caracteristicile unui obiect de tip File
 * Contine costructor defaul si cu parametri, metode accesor si metoda toString()*/
public class File {
    /**
     * Atribut care reprezinta extensia fisierului*/
    private String extension;

    /**
     * Atribut care reprezinta numele fisierului*/
    private String name;

    /**
     * Atribut care reprezinta calea absoluta a directorului in care se afla fisierul*/
    private String rootDirectoryPath;

    /**
     * Constructor cu toti parametrii
     * @param rootDirectoryPath reprezinta calea absoluta a directorului in care va fi introdus fisierul
     * @param extension reprezinta extensia fisierului
     * @param name reprezinta numele fisierului*/
    public File(String rootDirectoryPath, String extension, String name) {
        this.rootDirectoryPath = rootDirectoryPath;
        this.extension = extension;
        this.name = name;
    }

    /**
     * Metoda accesor care returneaza calea absoluta a directorului in care se afla fisierul*/
    public String getRootDirectoryPath() {
        return rootDirectoryPath;
    }

    /**
     * Metoda accesor care returneaza extensia fisierului*/
    public String getExtension() {
        return extension;
    }

    /**
     * Metoda accesor care returneaza numele fisierului*/
    public String getName() {
        return name;
    }

    /**
     * Metoda accesor care seteaza extensia fisierului*/
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * Metoda accesor care seteaza numele fisierului*/
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metoda accesor care seteaza calea absoluta a directorului in care se regaseste fisierul*/
    public void setRootDirectoryPath(String rootDirectoryPath) {
        this.rootDirectoryPath = rootDirectoryPath;
    }

    /**
     * Metoda toString() pentru afisarea la consoloa a unui obiect de tip File*/
    @Override
    public String toString() {
        return "File details: " +
                "name: " + name +
                ", extension: " + extension +
                ", root directory path: " + rootDirectoryPath;

    }
}

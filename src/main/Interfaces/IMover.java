package main.Interfaces;

/**
 * Interata cu metoda pentru mutare*/
public interface IMover {
    /**
     * Metoda pentru mutare
     * @param oldPath reprezinta calea directorului inainte de mutare
     * @param newPath reprezinta calea directorului in care urmeaza sa se efectueze mutarea
     * @param fileName reprezinta numele fisierului care urmeaza sa fie mutat*/
    void move(String oldPath, String newPath, String fileName);
}

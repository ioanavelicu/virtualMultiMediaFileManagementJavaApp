package main.Interfaces;

import main.Exceptions.ExceptionDirectoryDoesNotExist;

/**
 * Interfata cu metoda pentru stergere*/
public interface IRemover {
    /**
     * Metoda pentru stergere
     * @param info reprezinta informatii necesare pentru adaugare cum ar fi calea directorului
     * @param name reprezinta numele fisierului care urmeaza a fi sters, daca este cazul*/
    void remove(String info, String name) throws ExceptionDirectoryDoesNotExist;
}

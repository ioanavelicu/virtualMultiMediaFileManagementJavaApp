package main.Interfaces;

import main.Exceptions.ExceptionDirectoryDoesNotExist;

/**
 * Interfata cu metoda pentru redenumire*/
public interface IRenamer {
    /**
     * Metoda pentru redenumire
     * @param info reprezinta informatii necesare pentru adaugare cum ar fi calea directorului
     * @param newName reprezinta numele nou al fisierului sau directorului
     * @param oldName reprezinta numele vechi al fisierului, daca este cazul*/
    void rename(String info, String newName, String oldName) throws ExceptionDirectoryDoesNotExist;
}

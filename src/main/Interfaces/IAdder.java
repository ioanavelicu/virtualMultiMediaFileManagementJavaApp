package main.Interfaces;

import main.Exceptions.ExceptionTheSameDirectory;

/**
 * Interfata cu metoda pentru adaugare*/
public interface IAdder {
    /**
     * Metoda pentru adaugare
     * @param info reprezinta informatii necesare pentru adaugare cum ar fi calea directorului
     * @param name reprezinta numele obiectului care urmeaza sa fie adaugat, director sau fisier*/
    void add(String info, String name) throws ExceptionTheSameDirectory;
}

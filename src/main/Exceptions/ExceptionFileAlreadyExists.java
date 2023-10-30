package main.Exceptions;

/**
 * Clasa care defisneste exceptia aruncata atunci cand se doreste crearea unui nou fisier care deja exista
 * intr-un anumit director*/
public class ExceptionFileAlreadyExists extends Exception{
    /**
     * Constructor default*/
    public ExceptionFileAlreadyExists() {
        super();
    }

    /**
     * Constructor care primeste drept parametru un String reprezentant mesajul erorii
     * @param message mesajul de eroare*/
    public ExceptionFileAlreadyExists(String message) {
        super(message);
    }
}

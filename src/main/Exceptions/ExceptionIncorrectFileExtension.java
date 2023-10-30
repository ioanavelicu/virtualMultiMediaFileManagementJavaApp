package main.Exceptions;

/**
 * Clasa care defineste exceptia aruncata atunci cand se adauga un fisier care nu are ca extensie
 * una dintre extensiile predefinite
 * @see main.Constants.Constants*/
public class ExceptionIncorrectFileExtension extends Exception {
    /**
     * Constructor default*/
    public ExceptionIncorrectFileExtension() {
        super();
    }

    /**
     * Construstor care primeste drept paramatru un String reprezentand mesajul erorii
     * @param message mesajul de eroare*/
    public ExceptionIncorrectFileExtension(String message) {
        super(message);
    }
}

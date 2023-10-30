package main.Exceptions;

/**
 * Clasa care defineste exceptia aruncata atunci cand calea introdusa pentru un director nu este corecta
 * din punctul de vedere al structurii*/
public class ExceptionIncorrectPath extends Exception {
    /**
     * Constructor default*/
    public ExceptionIncorrectPath() {
        super();
    }

    /**
     * Construstor care primeste drept paramatru un String reprezentand mesajul erorii
     * @param message mesajul de eroare*/
    public ExceptionIncorrectPath(String message) {
        super(message);
    }
}

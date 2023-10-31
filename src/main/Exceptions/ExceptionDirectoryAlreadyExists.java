package main.Exceptions;

/**
 * Clasa care defineste exceptia aruncata atunci cand se creeaza un director cu aceeasi cale absoluta ca un
 * alt director deja existent*/
public class ExceptionDirectoryAlreadyExists extends Exception {
    /**
     * Constructor default*/
    public ExceptionDirectoryAlreadyExists() {
        super();
    }

    /**
     * Construstor care primeste drept paramatru un String reprezentand mesajul erorii
     * @param message mesajul de eroare*/
    public ExceptionDirectoryAlreadyExists(String message) {
        super(message);
    }
}

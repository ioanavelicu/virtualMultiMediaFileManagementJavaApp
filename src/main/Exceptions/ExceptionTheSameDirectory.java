package main.Exceptions;

/**
 * Clasa care defineste exceptia aruncata atunci cand se creeaza un director cu aceeasi cale absoluta ca un
 * alt director deja existent*/
public class ExceptionTheSameDirectory extends Exception {
    /**
     * Constructor default*/
    public ExceptionTheSameDirectory() {
        super();
    }

    /**
     * Construstor care primeste drept paramatru un String reprezentand mesajul erorii
     * @param message mesajul de eroare*/
    public ExceptionTheSameDirectory(String message) {
        super(message);
    }
}

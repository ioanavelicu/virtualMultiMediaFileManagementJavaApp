package main.Exceptions;

/**
 * Clasa care defineste exceptia aruncata atunci cand calea introdusa de utilizator nu este regasita in lista de
 * directoare preluate din fisierul sursa Directories.txt*/
public class ExceptionDirectoryDoesNotExist extends Exception{
    /**
     * Constructor default*/
    public ExceptionDirectoryDoesNotExist() {
        super();
    }

    /**
     * Construstor care primeste drept paramatru un String reprezentand mesajul erorii
     * @param message mesajul de eroare*/
    public ExceptionDirectoryDoesNotExist(String message) {
        super(message);
    }
}

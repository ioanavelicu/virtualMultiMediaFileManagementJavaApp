package main.Exceptions;

/**Clasa care defineste exceptia aruncata atunci cand se incearca mutarea, redenumirea sau stergerea unui fisier
 * care nu exista in directorul respectiv*/
public class ExceptionFileDoesNotExist extends Exception{
    /**
     * Constructor default*/
    public ExceptionFileDoesNotExist() {
        super();
    }

    /**
     * Constructor care primeste drept parametru un String reprezentant mesajul erorii
     * @param message mesajul de eroare*/
    public ExceptionFileDoesNotExist(String message) {
        super(message);
    }
}

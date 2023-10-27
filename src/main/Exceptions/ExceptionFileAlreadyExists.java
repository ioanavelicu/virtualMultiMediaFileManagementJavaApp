package main.Exceptions;

public class ExceptionFileAlreadyExists extends Exception{
    public ExceptionFileAlreadyExists() {
        super();
    }

    public ExceptionFileAlreadyExists(String message) {
        super(message);
    }
}

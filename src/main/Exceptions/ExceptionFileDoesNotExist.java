package main.Exceptions;

public class ExceptionFileDoesNotExist extends Exception{
    public ExceptionFileDoesNotExist() {
        super();
    }

    public ExceptionFileDoesNotExist(String message) {
        super(message);
    }
}

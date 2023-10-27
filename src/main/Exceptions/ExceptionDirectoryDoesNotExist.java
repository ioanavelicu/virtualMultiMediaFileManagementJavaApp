package main.Exceptions;

public class ExceptionDirectoryDoesNotExist extends Exception{
    public ExceptionDirectoryDoesNotExist() {
        super();
    }

    public ExceptionDirectoryDoesNotExist(String message) {
        super(message);
    }
}

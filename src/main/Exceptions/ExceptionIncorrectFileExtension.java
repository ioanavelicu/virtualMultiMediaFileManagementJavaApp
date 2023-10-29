package main.Exceptions;

public class ExceptionIncorrectFileExtension extends Exception{
    public ExceptionIncorrectFileExtension() {
        super();
    }

    public ExceptionIncorrectFileExtension(String message) {
        super(message);
    }
}

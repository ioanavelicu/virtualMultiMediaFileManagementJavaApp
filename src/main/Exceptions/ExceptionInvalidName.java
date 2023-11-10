package main.Exceptions;

public class ExceptionInvalidName extends Exception{
    public ExceptionInvalidName() {
        super();
    }

    public ExceptionInvalidName(String message) {
        super(message);
    }
}

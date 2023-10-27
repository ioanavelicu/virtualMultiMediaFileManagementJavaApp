package main.Interfaces;

import main.Exceptions.ExceptionDirectoryDoesNotExist;

public interface IRemover {
    void remove(String info, String name) throws ExceptionDirectoryDoesNotExist;
}

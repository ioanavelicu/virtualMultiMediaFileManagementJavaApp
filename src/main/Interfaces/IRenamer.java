package main.Interfaces;

import main.Exceptions.ExceptionDirectoryDoesNotExist;

public interface IRenamer {
    void rename(String info, String newName, String oldName) throws ExceptionDirectoryDoesNotExist;
}

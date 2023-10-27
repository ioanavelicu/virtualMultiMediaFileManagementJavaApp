package main.Interfaces;

import main.Exceptions.ExceptionTheSameDirectory;

public interface IAdder {
    void add(String info, String name) throws ExceptionTheSameDirectory;
}

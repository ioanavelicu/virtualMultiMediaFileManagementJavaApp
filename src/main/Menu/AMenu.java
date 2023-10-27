package main.Menu;

import main.Exceptions.ExceptionTheSameDirectory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class AMenu {
    protected Map<Options, String> options = new HashMap<Options, String>();

    abstract void run() throws ExceptionTheSameDirectory;
}

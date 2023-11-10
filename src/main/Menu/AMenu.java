package main.Menu;

import main.Exceptions.ExceptionDirectoryAlreadyExists;

import java.util.HashMap;
import java.util.Map;

/**
 * Clasa abstracta pentru definirea structurii unor obiecte asemanatoare unui meniu*/
public abstract class AMenu {
    /**
     * Atribut care prezinta optiunile pe care le poate avea un meniu.
     * Acestea sunt organizate intr-un obiect de tip Map avand cheia un obiect de tip Option si valorea un String
     * reprezentand mesajul optiunii respective
     * Options este o enumeratie
     * @see Options*/
    protected Map<Options, String> options = new HashMap<Options, String>();

    /**
     * Metoda abstracta pentru a incepe rularea meniului si a functionalitatilor sale
     * Urmeaza sa fie implementata in clasele care extind AMeniu*/
    abstract void run();
}

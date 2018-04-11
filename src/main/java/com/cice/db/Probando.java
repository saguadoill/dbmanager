package com.cice.db;

import java.util.List;

/**
 * Clase creada para probar el funcionamiento de la clase Manager
 */
public class Probando {

    public static void main(String[] args) {

        Manager m = new Manager();

        List<String> lista = m.ejecutarSelect("SELECT * FROM prueba");

        for (String s: lista) {
            System.out.println(s);
        }

        List<String> lista2 = m.ejecutarSelect("SELECT nombre FROM prueba");

        for (String s: lista2) {
            System.out.println(s);
        }

        lista2.clear();
        lista2.addAll(m.ejecutarSelect("SELECT id FROM prueba"));

        for (String s: lista2) {
            System.out.println(s);
        }
    }
}

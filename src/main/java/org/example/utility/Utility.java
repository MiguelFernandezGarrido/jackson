package org.example.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Utility {
    public static final Scanner SC = new Scanner(System.in);

    public static int showMenu() {
        int option = 0;
        boolean error = false;

        do {
            try {
                System.out.println("=========================================");
                System.out.println("1. Leer fichero (Magic)");
                System.out.println("2. Escribir fichero (Magic)");
                System.out.println("3. Leer fichero completo (Magic)");
                System.out.println("4. Escribir fichero completo (Magic)");
                System.out.println("5. Salir");
                System.out.println("=========================================");
                System.out.print("Elige una opción: ");
                option = Integer.parseInt(SC.nextLine());
                error = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingresa un número válido.");
            }
        } while (!error);

        return option;
    }

    public static void checkFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
//                Cerramos el programa
                System.out.println("Error al crear el fichero");
                System.exit(0);
            }
        }
    }

    public static void checkFileComplete(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
                BufferedWriter bf = new BufferedWriter(new FileWriter(file));
                bf.write("[]");
                bf.close();
            } catch (IOException e) {
//                Cerramos el programa
                System.out.println("Error al crear el fichero");
                System.exit(0);
            }
        }
    }

    public static String readString(String s) {
        System.out.print(s.isEmpty() ? "Sin mensaje" : s);
        return SC.nextLine();
    }
}

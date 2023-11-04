package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.Magic;
import org.example.utility.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    final static String URL_BASE = "https://api.scryfall.com/cards/search?q=name=";

    public static void main(String[] args) {

//        Manejando el fichero
        File cardFile = new File("src/main/resources/card.json");
        File cardListFile = new File("src/main/resources/cardList.json");
//        Si no existen los ficheros los creamos
        Utility.checkFile(cardFile);
        Utility.checkFileComplete(cardListFile);

        int option;
        do {

            option = Utility.showMenu();
            switch (option) {
                case 1 -> readJsonFileMagic(cardFile);
                case 2 -> writeJsonFileMagic(cardFile);
                case 3 -> readJsonFileMagicComplete(cardListFile);
                case 4 -> writeJsonFileMagicComplete(cardListFile);
                case 5 -> System.out.println("Adiós");
                default -> System.out.println("Opción incorrecta");
            }
        } while (option != 5);
    }

    private static void writeJsonFileMagicComplete(File file) {
        // Crear una nueva persona para agregar al archivo

        String search = Utility.readString("Dime el nombre de la carta que desea buscar: ");
        List<Magic> magics = getMagics(search);

        // Utilizando la librería Jackson para escribir el fichero sin borrar los datos
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Leer el contenido actual del archivo (si existe)
            List<Magic> magicsDisk;
            magicsDisk = mapper.readValue(file, new TypeReference<>() {
            });
            magicsDisk.addAll(magics);
            // Escribir la lista actualizada en el archivo
            mapper.writeValue(file, magicsDisk);
        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero");
        }
    }

    /**
     * Method to get a Magic from the user
     *
     * @return Magic
     */
    private static List<Magic> getMagics(String search) {
        try {
            URL url = new URL(URL_BASE+search);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            StringBuilder infoString = null;

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Ha ocurrido un error " + responseCode);
            } else {
                infoString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    infoString.append(scanner.nextLine());
                }
                scanner.close();
            }
            JSONObject jsonResponse = new JSONObject(infoString.toString());

            JSONArray jsonArray = jsonResponse.getJSONArray("data");
            List<Magic> magics = new ArrayList<>();

            // Iterate through the JSON array and create Magic objects
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject card = jsonArray.getJSONObject(i);

                String power;
                String oracle_text;
                try {
                    power = card.getString("power");
                } catch (Exception e) {
                    power = "No tiene poder";
                }
                try {
                    oracle_text = card.getString("oracle_text");
                } catch (Exception e) {
                    oracle_text = "No tiene descripción";
                }
                // Create a Magic object and populate its fields
                Magic magic = new Magic(card.getString("id"), card.getString("name"),
                        power, oracle_text);

                // Add the Magic object to the list
                magics.add(magic);
            }
            return magics;

        } catch (Exception e) {
            System.out.println("Ha habido un problema al obtener las Magic");
        }
        return new ArrayList<>();
    }

    private static void readJsonFileMagicComplete(File file) {
        //        Utilizando la librería jackson
        ObjectMapper mapper = new ObjectMapper();

//        Leyendo y mostrando el fichero
        try {
//            Obteniendo los objetos persona del fichero
            List<Magic> magics = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Magic.class));
            magics.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero");
        }
    }

    private static void writeJsonFileMagic(File file) {
//        Una nueva Magic para escribir en el fichero
//        Tomamos el nombre por teclado
        String search= Utility.readString("Dime el nombre de las cartas que deseas buscar: ");
        List<Magic> magics = getMagics(search);
        Magic magic = magics.get(numRandom(magics.size()));

//        Utilizando la librería jackson para escribir el fichero
        ObjectMapper mapper = new ObjectMapper();

//        Escribiendo en el fichero
        try {
            mapper.writeValue(file, magic);
        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero");
        }
    }

    private static int numRandom(int size) {
        return (int) (Math.random() * size);
    }

    private static void readJsonFileMagic(File file) {

//        Utilizando la librería jackson
        ObjectMapper mapper = new ObjectMapper();

//        Leyendo y mostrando el fichero
        try {
//            Obteniendo el objeto persona del fichero
            Magic magic = mapper.readValue(file, Magic.class);
            System.out.println(magic);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero");
        }
    }


}
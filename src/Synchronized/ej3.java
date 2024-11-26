package Synchronized;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;

public class ej3 {

    static class Database {
        static Path path;
        static Database database;
        static  Database getDatabase(){
            if(database == null){
                synchronized (Database.class){
                    if (database == null){
                        database = new Database();
                    }

                }

            }
            return database;
        }

        Database() {
            path = Path.of("db.sqlito-");
            System.out.println("Database path: " + path);
        }

       synchronized void write(String data) {
            // Debe escribir el string en el fichero 'caracter a caracter'
            for (int i = 0; i < data.length(); i++) {
                try {
                    Files.writeString(path, data.substring(i, i + 1), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (Exception _) {
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100; i++) {
                executor.submit(ej3::writeHolaquetal);
            }
        }

        // mostrar la """base de datos""" 
        try (var lines = Files.lines(Database.path)) {
            System.out.println("Total: " + lines.peek(System.out::println).count() + " lineas");
        }
    }

    static void writeHolaquetal() {
        Database database = Database.getDatabase();
        database.write("hola que tal\n");
    }
}
package Synchronized;

import java.util.concurrent.Executors;

public class ej2 {
    public static void main(String[] args) {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(ej2::rojoynegro);
            executor.submit(ej2::rojoynegro);
            executor.submit(ej2::rojoynegro);
            executor.submit(ej2::rojoynegro);
        }
    }

    static void rojoynegro() {

        synchronized (){
            synchronized () {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        System.out.print("♥️");
                    }
                    System.out.println();
                }
            }
        }
        synchronized (){
            synchronized () {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        System.out.print("♠️");
                    }
                    System.out.println();
                }
            }
        }
        synchronized () {
            synchronized () {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        System.out.print("♦️");
                    }
                    System.out.println();
                }
            }
        }
        synchronized () {
            synchronized () {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        System.out.print("♣️");
                    }
                    System.out.println();
                }
            }
        }
    }
}
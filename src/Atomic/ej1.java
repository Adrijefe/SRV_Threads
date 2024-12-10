package Atomic;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;

public class ej1 {

    static class Post {
        int meGustas = 0;
        int rePublicaciones = 0;

        synchronized void meGusta() {
            meGustas++;

        }

       synchronized void rePublicar() {
                rePublicaciones++;

        }
    }

    public static void main(String[] args) {
        var startTime = LocalDateTime.now();

        Post post = new Post();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 5_000_000; i++) {
                executor.submit(post::meGusta);
                executor.submit(post::rePublicar);
            }
        }

        System.out.println(post.meGustas);
        System.out.println(post.rePublicaciones);

        System.out.println("Tiempo tardado: " + ChronoUnit.MILLIS.between(startTime, LocalDateTime.now()));
    }
}
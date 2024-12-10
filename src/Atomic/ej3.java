package Atomic;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ej3{

    static class Post {

        AtomicInteger meGustas = new AtomicInteger();
        AtomicInteger rePublicaciones = new AtomicInteger();
        void meGusta() {
            meGustas.incrementAndGet();



        }

        void rePublicar() {
            rePublicaciones.incrementAndGet();


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
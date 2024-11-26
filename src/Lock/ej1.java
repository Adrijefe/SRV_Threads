package Lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ej1 {

    static class Libro {
        ArrayList<String> paginas;
        Lock lock = new ReentrantLock();

        Libro(List<String> paginas) {
            this.paginas = new ArrayList<>(paginas);
        }

        void copiarPaginas(Libro libroOrigen) {
            for (String pagina : libroOrigen.obtenerPaginasInterioresNoVacias()) {
                int penultimaPosicion = Math.max(0, this.paginas.size() - 1);
                this.paginas.add(penultimaPosicion, pagina);
            }
        }

        List<String> obtenerPaginasInterioresNoVacias() {
            List<String> paginas = new ArrayList<>();
            lock.lock();

            for (String pagina : this.paginas.subList(1, this.paginas.size() - 1)) { // Excluye portada y contraportada
                if(!pagina.isEmpty()) {
                    paginas.add(pagina);
                    lock.lock();

                }
            }
            return paginas;
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            final Libro libroA = new Libro(List.of("Portada-A", "A1", "A2", "", "A3", "A4", "Contraportada-A"));
            final Libro libroB = new Libro(List.of("Portada-B", "B1", "B2", "B3", "", "B4", "Contraportada-B"));

            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                executor.submit(() -> libroA.copiarPaginas(libroB));
                executor.submit(() -> libroB.copiarPaginas(libroA));
            }

            System.out.println(libroA.paginas);
            System.out.println(libroB.paginas);
            System.out.println();
        }
    }
}
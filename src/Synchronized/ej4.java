package Synchronized;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

class Cuenta {
    int fondos;
    final int numero;

    Cuenta(int numero) {
        this.numero = numero;
        this.fondos = 500;
    }

   synchronized void sumar(int cantidad) {
        fondos += cantidad;
    }

    synchronized void restar(int cantidad) {
        fondos -= cantidad;
    }
}

class Banco {
    public void transferirDinero(Cuenta origen, Cuenta destino, int cantidad) {
        synchronized (origen) {
                if (origen.fondos > cantidad) {
                    destino.sumar(cantidad);
                    origen.restar(cantidad);

                }
        }

    }
}

public class ej4 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            simularDosCuentas();
        }

        for (int i = 0; i < 100; i++) {
            simularCienCuentas();
       }
    }

    static void simularDosCuentas() {
        Cuenta cuenta1 = new Cuenta(1);
        Cuenta cuenta2 = new Cuenta(2);

        Banco banco = new Banco();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> banco.transferirDinero(cuenta1, cuenta2, 200));
            executor.submit(() -> banco.transferirDinero(cuenta1, cuenta2, 200));
            executor.submit(() -> banco.transferirDinero(cuenta1, cuenta2, 200));
        }

        if (cuenta1.fondos < 0) {
            System.out.println("\uD83D\uDCA9 " + cuenta1.fondos);
        }
    }

    static void simularCienCuentas() {
        List<Cuenta> cuentas = IntStream.range(0, 100).mapToObj(Cuenta::new).toList();

        Banco banco = new Banco();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1000; i++) {
                executor.submit(() ->
                        banco.transferirDinero(
                                cuentas.get(ThreadLocalRandom.current().nextInt(cuentas.size())),
                                cuentas.get(ThreadLocalRandom.current().nextInt(cuentas.size())),
                                ThreadLocalRandom.current().nextInt(500)
                        ));
            }
        }

        cuentas.stream()
                .filter(c -> c.fondos < 0)
                .forEach(c -> System.out.println("\uD83E\uDD2E " + c.fondos));
    }
}

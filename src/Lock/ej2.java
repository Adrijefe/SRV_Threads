package Lock;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ej2 {
    static class Database {
        String data = "";
        Lock lock = new ReentrantLock(true);
        void write(String value) {
            lock.lock();
            for (int i = 0; i < 2000; i++) {
                this.data += value;
            }
            this.data += "\n";
            lock.unlock();
        }
    }

    static class Writer implements Runnable {
        int numberOfWrites = 0;
        final Database database;
        final String letra;

        Writer(Database database, String letra) {
            this.database = database;
            this.letra = letra;
        }

        @Override
        public void run() {
            while (true) {
                database.write(letra);
                numberOfWrites++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var database = new Database();

        var writer1 = new Writer(database, "a");
        var writer2 = new Writer(database, "b");
        var writer3 = new Writer(database, "c");
        var writer4 = new Writer(database, "d");

        var executor = Executors.newVirtualThreadPerTaskExecutor();

        executor.submit(writer1);
        executor.submit(writer2);
        executor.submit(writer3);
        executor.submit(writer4);

        executor.awaitTermination(1, TimeUnit.SECONDS);

        System.out.println(database.data);

        System.out.println(writer1.numberOfWrites);
        System.out.println(writer2.numberOfWrites);
        System.out.println(writer3.numberOfWrites);
        System.out.println(writer4.numberOfWrites);
    }
}

package Synchronized;

import java.util.concurrent.Executors;

 class ej1 {
    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            wtf();
        }
    }

    static void wtf() {
        Counter counter = new Counter();

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(counter::increment);
            executor.submit(counter::decrement);
        }
//        var t1 = Thread.startVirtualThread(counter::increment);
//        var t2 = Thread.startVirtualThread(counter::decrement);
//        t1.join();
//        t2.join();

        if (counter.value != 0)
            System.out.println(counter.value);
    }
}

class Counter {
    int value = 0;

    synchronized public void increment() {
        value++;
    }

     synchronized public void decrement() {
        value--;
    }
}
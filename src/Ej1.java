import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ej1 {
    public static void main(String[] args) throws InterruptedException {
        var mithread = Thread.startVirtualThread(()->{
            int i = 0;
            while(true) System.out.println(i++);
            });

        var mithread2 = Thread.startVirtualThread(()->{
            int i = 0;
            while(true) System.out.println("Hola cara  culo");
        });

        mithread.join();
        mithread2.join();



        }

}
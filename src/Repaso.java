import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;



    class UniqueIDGerenador{
        AtomicLong a = new AtomicLong();
        long generateID(){
            return a.incrementAndGet();
        }

    }
public class Repaso {

    public static void main(String[] args) {
        UniqueIDGerenador uniqueIDGerenador = new UniqueIDGerenador();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()){
            for (int i = 0; i < 100; i++) {
                executor.submit(()-> {
                    for (int j = 0; j < 500; j++) {
                        System.out.println(uniqueIDGerenador.generateID());
                    }
                });
            }
        }
    }

    }


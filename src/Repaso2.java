import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

class AccessControl{
    AtomicLong connected = new AtomicLong();

    void connect() {
        connected.updateAndGet(current -> {
            if (current < 5){
                return current +1;
            }
            return current;
        });

    }
    void disconnect() {
        connected.updateAndGet(current -> {
            if (current > 0){
                return current-1;
            }
            return current;
        });
    }
}

public class Repaso2 {
    public static void main(String[] args) {
        AccessControl ac = new AccessControl();
        try(var executor = Executors.newCachedThreadPool()){
            for (int i = 0; i < 100; i++) {
                executor.submit(() -> {
                    for (int j = 0; j < 500; j++) {
                        ac.connect();
                        ac.disconnect();
                    }
                });
            }
        }
    }
}

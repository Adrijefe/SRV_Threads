import java.util.concurrent.Executors;

 class Main{

    static Object object = new Object();
    static class Contador {

        int contador;
        void sumar(){
            if (contador == 0){
                synchronized (object){
                    contador++;

                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            Contador contador = new Contador();
            Contador contador2 = new Contador();

            try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
                executor.submit(()-> contador.sumar());
                executor.submit(()-> contador2.sumar());

            }

        }
    }
}

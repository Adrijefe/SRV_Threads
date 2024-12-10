import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Ej2 {
        public static void main(String[] args) throws InterruptedException, ExecutionException {
                try (var executor = Executors.newVirtualThreadPerTaskExecutor()){
                    var weather = executor.submit(() ->  fetch("https://apipa.vercel.app/api/w/weather"));

                var temperature = executor.submit(() -> fetch("https://apipa.vercel.app/api/w/temperature"));

                var wind = executor.submit(() -> fetch("https://apipa.vercel.app/api/w/wind"));

                var moon  = executor.submit(() -> fetch("https://apipa.vercel.app/api/w/moon"));
                System.out.println(weather.get());
                System.out.println(temperature.get());
                System.out.println(wind.get());
                System.out.println(moon.get());

            }



        }

        static String fetch(String url) {
            try(var inputStream = new URI(url).toURL().openStream()) {
                return new String(inputStream.readAllBytes());
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }


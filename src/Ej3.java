import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Ej3 {
    public static void main(String[] args) throws IOException, InterruptedException {
        var start = LocalDateTime.now();

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            executor.submit(() -> {
                generateRandomFile("myfile1", 100_000_000);
                zipFile("myfile1");
            });
            executor.submit(() -> {
                generateRandomFile("myfile2", 100_000_000);
                zipFile("myfile2");
            });
        }

//


        System.out.println("Tiempo tardado: " + ChronoUnit.MILLIS.between(start, LocalDateTime.now()));
    }

    public static void zipFile(String filePath) {
        try (
                var fis = Files.newInputStream(Path.of(filePath));
                var zipOut = new ZipOutputStream(Files.newOutputStream(Path.of(filePath + ".zip")))
        ) {
            zipOut.putNextEntry(new ZipEntry(Path.of(filePath).getFileName().toString()));

            var buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zipOut.write(buffer, 0, bytesRead);
            }
            zipOut.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void generateRandomFile(String filePath, int size) {
        try (
                var bw = Files.newBufferedWriter(Path.of(filePath))
        ) {
            ThreadLocalRandom.current().ints(size, 32, 127)
                    .forEach(c -> {
                        try {
                            bw.write(c);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
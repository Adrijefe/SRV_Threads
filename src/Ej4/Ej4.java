package Ej4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Ej4 {
    static final boolean debug = false;

    public static void main(String[] args) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> new Server().start());
            executor.submit(() -> new Client().start());
        }
    }


    static class Server {
        void start() {
            try {
                ServerSocket serverSocket = new ServerSocket(7777);
                if (debug) System.out.println("Servidor iniciado " + serverSocket);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    if (debug) System.out.println("Cliente conectado " + clientSocket);
                    Thread.startVirtualThread(() -> {
                        try {
                            var socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                            if (debug) System.out.println("Esperando mensajes de " + clientSocket + "...");
                            socketReader.lines().forEach(System.out::println);
                        } catch (Exception _) {
                        }
                    });
                }
            } catch (Exception _) {
                if (debug) System.out.println("No pudo iniciar el server.");
            }
        }
    }

    static class Client {
        PrintWriter[] servidores = new PrintWriter[254];
        boolean[] intentando = new boolean[254];

        void start() {
            try {
                Scanner scanner = new Scanner(System.in);

                Thread.startVirtualThread(() -> {
                    try {
                        while (true) {
                            for (int i = 1; i < 255; i++) {
                                if (servidores[i - 1] == null && !intentando[i - 1]) {
                                    int ii = i;
                                    Thread.startVirtualThread(() -> {
                                        try {
                                            intentando[ii - 1] = true;
                                            if (debug) System.out.println("Intentando conectar a 10.2.1." + ii);
                                            Socket socket = new Socket("10.2.1." + ii, 7777);
                                            if (debug) System.out.println("Conectado al servidor " + socket);

                                            var socketWriter = new PrintWriter(socket.getOutputStream(), true);

                                            servidores[ii - 1] = socketWriter;
                                        } catch (Exception _) {
                                            if (debug) System.out.println("No se pudo conectar a 10.2.1." + ii);
                                            intentando[ii - 1] = false;
                                        }
                                    });
                                }
                            }
                            Thread.sleep(500);
                        }
                    } catch (Exception _) {
                    }
                });


                System.out.println("<< \uD83D\uDCAC P2P Chat \uD83D\uDCAC >>");
                System.out.print("Username: ");
                String username = scanner.nextLine();

                while (true) {
                    String mensaje = scanner.nextLine();
                    for (var servidor : servidores) {
                        if (servidor != null) {
                            servidor.println("\033[34m" + username + "\033[0m:" + mensaje);
                        }
                    }
                    if (debug) System.out.println("Mensaje enviado");
                }
            } catch (Exception _) {
            }
        }
    }
}
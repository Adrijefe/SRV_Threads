package Ej4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Ej4 {
    public static void main(String[] args) throws InterruptedException {

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            executor.submit(()->new Server().start());
            executor.submit(()->new Client().start());
        }
    }


    static class Server {
        void start() {
            try {
                ServerSocket serverSocket = new ServerSocket(7777);
                System.out.println("Servidor iniciado " + serverSocket);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                  //  System.out.println("Cliente conectado " + clientSocket);
                    Thread.startVirtualThread(() -> {
                        try {
                            var socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        //    System.out.println("Esperando mensajes de " + clientSocket + "...");
                            socketReader.lines().forEach(System.out::println);
                        } catch (Exception _) {
                        }
                    });
                }
            } catch (Exception _) {
                System.out.println("ERROR");
            }
        }
    }

    static class Client {
        PrintWriter[] servidores = new PrintWriter[254];

        void start() {
            try {
                Scanner scanner = new Scanner(System.in);

                Thread.startVirtualThread(() -> {

                        while (true) {
                            for (int i = 1; i < 255; i++) {
                                if (servidores[i-1] == null) {
                                    final int ii = i;
                                    Thread.startVirtualThread(()->{
                                        try {
                                            Socket socket = new Socket("10.2.1." + ii, 7777);
                                            System.out.println("Conectado al servidor " + socket);

                                            var socketWriter = new PrintWriter(socket.getOutputStream(), true);

                                            servidores[ii - 1] = socketWriter;
                                        }catch (Exception _){}
                                    });

                                }
                            }

                        }
                });

                while (true) {
                    System.out.println("Escribe un mensaje:");
                    String mensaje = scanner.nextLine();
                    for(var servidor: servidores) {
                        if (servidor != null) {
                            servidor.println( mensaje);
                        }
                    }
                    System.out.println("Mensaje enviado");
                }
            } catch (Exception _) {
            }
        }
    }
}
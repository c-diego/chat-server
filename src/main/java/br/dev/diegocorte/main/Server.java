package br.dev.diegocorte.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *
 * @author c-diego
 */
public class Server {

    private Connections connections;
    private final ExecutorService pool;

    public Server() {
        connections = Connections.getInstance();
        pool = Executors.newFixedThreadPool(10);
    }

    public void start(int port) {

        try (ServerSocket listener = new ServerSocket(port)) {
             System.out.println("Server started on port " + port);

             while (true) {
                 acceptConnection(listener);
             }

        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void acceptConnection(ServerSocket listener) {
        try {
            Socket client = listener.accept();
            ConnectionHandler handler = new ConnectionHandler(client);
            connections.addConnection(handler);
            pool.execute(handler);

        } catch (IOException e) {
            System.err.println("Error accepting the connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

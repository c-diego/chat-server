package br.dev.diegocorte.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

    private Connections connections;
    private final Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String username;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
        connections = Connections.getInstance();
    }

    @Override
    public void run() {

        try {

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            output.print("Username: ");
            output.flush();

            username = input.readLine();
            System.out.println(username + " joined");

            connections.broadcast(String.format("%s joined.", username));

            String msg;
            while ((msg = input.readLine()) != null) {
                connections.broadcast(String.format("%s: %s.", username, msg));
            }

        } catch (IOException e) {

        } finally {
            quit();
            System.out.println(username + " left");
            connections.broadcast(String.format("%s left.", username));
        }
    }

    private void quit() {
        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException e) {
        }
    }

    public void send(String msg) {
        output.println(msg);
    }
}

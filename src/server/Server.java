package server;

/**
 *
 * @author diego
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final ArrayList<ConnectionHandler> connections;
    private final ExecutorService pool;
    private ServerSocket listener;

    public Server() {
        connections = new ArrayList<>();
        pool = Executors.newFixedThreadPool(10);
    }

    public void start(int port) {

        try {
            listener = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            ConnectionHandler handler;

            while (true) {
                handler = new ConnectionHandler(listener.accept());
                connections.add(handler);
                pool.execute(handler);
            }

        } catch (IOException e) {
            System.err.println(e);
        }

    }

    private void broadcast(String msg) {
        for (ConnectionHandler c : connections) {
            if (c != null) {
                c.send(msg);
            }
        }
    }

    private class ConnectionHandler implements Runnable {

        private final Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String username;

        ConnectionHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try {

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.print("Username: ");
                out.flush();
                username = in.readLine();
                System.out.println(username + " joined");
                broadcast(username + " joined");

                String msg;
                while ((msg = in.readLine()) != null) {
                    broadcast(username + ": " + msg);
                }

            } catch (IOException e) {

            } finally {
                quit();
                System.out.println(username + " left");
                broadcast(username + " left");
            }
        }

        private void quit() {
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
            }
        }

        private void send(String msg) {
            out.println(msg);
        }
    }
    
    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Usage: java Server port");
            System.exit(1);
        }
        (new Server()).start(Integer.valueOf(args[0]));
    }

}

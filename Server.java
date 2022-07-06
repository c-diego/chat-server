/**
 *
 * @author c-diego
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws Exception {

        try ( ServerSocket listener = new ServerSocket(5090)) {
            System.out.println("Server is online");
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute((new ServiceSocket(listener.accept())));
            }
        }
    }

    private static class ServiceSocket implements Runnable {

        private Socket socket;

        ServiceSocket(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println(socket + " connected");

            try {
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                while (in.hasNextLine()) {
                    out.println(in.nextLine().toUpperCase());
                }

            } catch (IOException e) {
                System.out.println("Error:" + socket);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
                System.out.println(socket + " left");
            }
        }
    }

}


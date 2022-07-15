package client;

/**
 *
 * @author diego
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private String msg;
    private Socket socket;
    private PrintWriter out;
    public Scanner in;
    
    public Cliente(String ip, int port) {

        try {
            
            socket = new Socket(ip, port);

            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException ex) {
            System.exit(1);
        }
    }

    public void send(String msg) {
        if (out != null) {
            if (!socket.isClosed()) {
                out.println(msg);
            }
        }
    }
}

package br.dev.diegocorte.main;

import java.util.ArrayList;
import java.util.List;

public class Connections {

    private static Connections instance;
    private List<ConnectionHandler> connections;


    private Connections() {
        connections = new ArrayList<>();
    }

    public static Connections getInstance() {
        if (instance == null)
            instance = new Connections();
        return instance;
    }
    public void addConnection(ConnectionHandler handler) {
        connections.add(handler);
    }

    public void broadcast(String msg) {
        for (ConnectionHandler con : connections) {
            if (con != null) {
                con.send(msg);
            }
        }
    }
}

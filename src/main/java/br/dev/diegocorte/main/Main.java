package br.dev.diegocorte.main;

public class Main {

    public static void main(String[] args) {
        int port = 3202;

        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }

        (new Server()).start(port);
    }

}

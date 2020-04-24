package br.pro.turing.masiot.rmlserver;

public class RMLServerApplication {

    public static void main(String[] args) {
        new RMLGatewayStarter(args).start();
        new RMLServer().start();
    }
}

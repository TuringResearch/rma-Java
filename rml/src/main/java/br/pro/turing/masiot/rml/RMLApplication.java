package br.pro.turing.masiot.rml;

public class RMLApplication {

    public static void main(String[] args) {
        new RMLGatewayStarter(args).start();
        new RMLServer().start();
    }
}

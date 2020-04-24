package artifact;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArtifactClient implements NodeConnectionListener {

    private static final Logger LOGGER = Logger.getLogger(ArtifactClient.class.getSimpleName());
    private static String gatewayIP = "127.0.0.1";
    private static int gatewayPort = 5500;
    private MrUdpNodeConnection connection;

    public static void main(String[] args) {
        new ArtifactClient();
    }

    public ArtifactClient() {
        InetSocketAddress address = new InetSocketAddress(gatewayIP, gatewayPort);
        try {
            connection = new MrUdpNodeConnection();
            connection.addNodeConnectionListener(this);
            connection.connect(address);

            runArtifact();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro de entrada e saída durante a conexão.", e);
        }
    }

    private void runArtifact() {
        Random random = new Random();
        while (true) {
            long t1 = System.currentTimeMillis();

            ApplicationMessage message = new ApplicationMessage();
            String serializableContent = String.valueOf(random.nextInt(100));
            message.setContentObject(serializableContent);

            try {
                connection.sendMessage(message);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Erro de entrada e saída durante o envio da mensagem.", e);
            }

            long duration = System.currentTimeMillis() - t1;
            try {
                Thread.sleep(duration < 1000 ? 1000 - duration : 0);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Thread interrompida.", e);
            }
        }
    }

    @Override
    public void connected(NodeConnection nodeConnection) {
        ApplicationMessage message = new ApplicationMessage();
        String serializableContent = "I'm a Simulated Artifact";
        message.setContentObject(serializableContent);

        try {
            connection.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newMessageReceived(NodeConnection nodeConnection, Message message) {
    }

    @Override
    public void reconnected(NodeConnection nodeConnection, SocketAddress socketAddress, boolean b, boolean b1) {
    }

    @Override
    public void disconnected(NodeConnection nodeConnection) {
    }

    @Override
    public void unsentMessages(NodeConnection nodeConnection, List<Message> list) {
    }

    @Override
    public void internalException(NodeConnection nodeConnection, Exception e) {
    }
}

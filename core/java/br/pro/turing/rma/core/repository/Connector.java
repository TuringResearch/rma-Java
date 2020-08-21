package br.pro.turing.rma.core.repository;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;

public class Connector {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 27020;
    private static final String DATABASE_NAME = "vcdb";

    private static Connector instance;

    private Jongo jongo;

    /**
     * Construtor.
     */
    private Connector() {
        DB db = new MongoClient(IP, PORT).getDB(DATABASE_NAME);
        this.jongo = new Jongo(db);
    }

    /**
     * @return {@link #instance}
     */
    public static Connector getInstance() {
        if (Connector.instance == null) {
            Connector.instance = new Connector();
        }
        return Connector.instance;
    }

    /**
     * @return {@link #jongo}
     */
    public Jongo getJongo() {
        return this.jongo;
    }
}

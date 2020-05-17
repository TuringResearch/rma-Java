package br.pro.turing.masiot.core.model;

import java.io.Serializable;

/**
 * Connection state of a component connected in the RML.
 */
public enum ConnectionState implements Serializable {

    /** Online state. */
    ONLINE("ONLINE"),

    /** Offline state. */
    OFFLINE("OFFLINE");

    /** State of connection. */
    String state;

    /**
     * Constructor.
     *
     * @param state State of connection.
     */
    ConnectionState(String state) {
        this.state = state;
    }

    public static ConnectionState get(String connectionState) {
        for (ConnectionState value : values()) {
            if (value.getState().equals(connectionState)) {
                return value;
            }
        }
        return null;
    }

    /**
     * @return {@link #state}
     */
    public String getState() {
        return this.state;
    }
}

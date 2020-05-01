package br.pro.turing.masiot.core.model;

import java.io.Serializable;

/**
 * Connection state of a component connected in the RML.
 */
public enum ConnectionState implements Serializable {

    /** Online state. */
    ONLINE,

    /** Offline state. */
    OFFLINE
}

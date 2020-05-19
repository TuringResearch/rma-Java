package br.pro.turing.masiot.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * An Action is a model that represents command request made by a client to be executed by a resource.
 */
public class Action implements Serializable {

    /** Serial version ID for serialization. */
    private static final long serialVersionUID = 1L;

    /** Class name to be used by JSON strings. */
    @JsonIgnore
    private final String className = getClass().getName();

    /** ID. */
    private ObjectId _id;

    /** Instant time of the action request. */
    private Date instant;

    /** Device name. */
    private String deviceName;

    /** Resource name. */
    private String resourceName;

    /** Command ID of this action. */
    private String command;

    /**
     * MongoDB constructor.
     */
    private Action() {
    }

    /**
     * Constructor.
     *
     * @param instant      {@link #instant}
     * @param deviceName   {@link #deviceName}
     * @param resourceName {@link #resourceName}
     * @param command      {@link #command}
     */
    public Action(LocalDateTime instant, String deviceName, String resourceName, String command) {
        this._id = new ObjectId();
        this.setInstant(instant);
        this.deviceName = deviceName;
        this.resourceName = resourceName;
        this.command = command;
    }

    /**
     * @return {@link #serialVersionUID}
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @return {@link #_id}
     */
    public ObjectId get_id() {
        return this._id;
    }

    /**
     * @param _id {@link #_id}
     */
    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    /**
     * @return {@link #instant}
     */
    public LocalDateTime getInstant() {
        return LocalDateTime.ofInstant(this.instant.toInstant(), ZoneId.systemDefault());
    }

    /**
     * @param instant {@link #instant}
     */
    public void setInstant(LocalDateTime instant) {
        this.instant = Date.from(instant.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @return {@link #deviceName}
     */
    public String getDeviceName() {
        return this.deviceName;
    }

    /**
     * @param deviceName {@link #deviceName}
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * @return {@link #resourceName}
     */
    public String getResourceName() {
        return this.resourceName;
    }

    /**
     * @param resourceName {@link #resourceName}
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * @return {@link #command}
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * @param command {@link #command}
     */
    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Action action = (Action) o;
        return Objects.equals(this.className, action.className) && Objects.equals(this._id, action._id) && Objects
                .equals(this.instant, action.instant) && Objects.equals(this.deviceName, action.deviceName) && Objects
                .equals(this.resourceName, action.resourceName) && Objects.equals(this.command, action.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.className, this._id, this.instant, this.deviceName, this.resourceName, this.command);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Action.class.getSimpleName() + "[", "]").add("instant=" + this.instant).add(
                "deviceName='" + this.deviceName + "'").add("resourceName='" + this.resourceName + "'").add(
                "command='" + this.command + "'").toString();
    }
}
package br.pro.turing.masiot.core.model;

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

    /** ID. */
    private ObjectId _id;

    /** Instant time of the action request. */
    private Date instant;

    /** Command ID of this action. */
    private ObjectId commandId;

    /**
     * MongoDB constructor.
     */
    private Action() {
    }

    /**
     * Constructor.
     *
     * @param instant   {@link #instant}
     * @param commandId {@link #commandId}
     */
    public Action(LocalDateTime instant, ObjectId commandId) {
        this._id = new ObjectId();
        this.setInstant(instant);
        this.commandId = commandId;
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
     * @return {@link #commandId}
     */
    public ObjectId getCommandId() {
        return this.commandId;
    }

    /**
     * @param commandId {@link #commandId}
     */
    public void setCommandId(ObjectId commandId) {
        this.commandId = commandId;
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
        return Objects.equals(this._id, action._id) && Objects.equals(this.instant, action.instant) && Objects.equals(
                this.commandId, action.commandId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._id, this.instant, this.commandId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Action.class.getSimpleName() + "[", "]").add("id='" + this._id + "'").add(
                "instant=" + this.instant).add("commandId='" + this.commandId + "'").toString();
    }
}
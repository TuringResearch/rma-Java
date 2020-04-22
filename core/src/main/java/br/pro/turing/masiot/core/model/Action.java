package br.pro.turing.masiot.core.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectId _id;

    private LocalDateTime instant;

    private ObjectId commandId;

    private Action() {
    }

    public Action(LocalDateTime instant, ObjectId commandId) {
        this._id = new ObjectId();
        this.instant = instant;
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
        return this.instant;
    }

    /**
     * @param instant {@link #instant}
     */
    public void setInstant(LocalDateTime instant) {
        this.instant = instant;
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
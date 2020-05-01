package br.pro.turing.masiot.core.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Command is the model that represents a execution that a resource can do.
 */
public class Command implements Serializable {

    /** Serial version ID for serialization. */
    private static final long serialVersionUID = 1L;

    /** ID. */
    private ObjectId _id;

    /** Content of the command. */
    private String command;

    /** Description of the command. */
    private String description;

    /**
     * MongoDB constructor.
     */
    private Command() {
    }

    /**
     * Constructor.
     *
     * @param command     {@link #command}
     * @param description {@link #description}
     */
    public Command(String command, String description) {
        this._id = new ObjectId();
        this.command = command;
        this.description = description;
    }

    /**
     * @return {@link #serialVersionUID}
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param _id {@link #_id}
     */
    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    /**
     * @return {@link #_id}
     */
    public ObjectId get_id() {
        return this._id;
    }

    /**
     * @param command {@link #command}
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return {@link #description}
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description {@link #description}
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Command command1 = (Command) o;
        return Objects.equals(this._id, command1._id) && Objects.equals(this.command, command1.command) && Objects
                .equals(this.description, command1.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._id, this.command, this.description);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Command.class.getSimpleName() + "[", "]").add("id='" + this._id + "'").add(
                "command='" + this.command + "'").add("description='" + this.description + "'").toString();
    }
}

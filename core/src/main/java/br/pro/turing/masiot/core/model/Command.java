package br.pro.turing.masiot.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Command is the model that represents a execution that a resource can do.
 */
public class Command implements Serializable {

    /** Serial version ID for serialization. */
    private static final long serialVersionUID = 1L;

    /** Class name to be used by JSON strings. */
    @JsonIgnore
    private final String className = getClass().getName();

    /** Content of the command. */
    @MongoId
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
     * @param command {@link #command}
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return {@link #command}
     */
    public String getCommand() {
        return this.command;
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
        return Objects.equals(this.command, command1.command) && Objects
                .equals(this.description, command1.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.command, this.description);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Command.class.getSimpleName() + "[", "]").add(
                "command='" + this.command + "'").add("description='" + this.description + "'").toString();
    }
}

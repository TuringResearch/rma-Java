package br.pro.turing.masiot.core.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class Environment implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectId _id;

    private String name;

    private String description;

    private Integer capacity;

    private Environment() {
    }

    public Environment(String name, String description, Integer capacity) {
        this._id = new ObjectId();
        this.name = name;
        this.description = description;
        this.capacity = capacity;
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
     * @return {@link #name}
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name {@link #name}
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * @return {@link #capacity}
     */
    public Integer getCapacity() {
        return this.capacity;
    }

    /**
     * @param capacity {@link #capacity}
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Environment that = (Environment) o;
        return Objects.equals(this._id, that._id) && Objects.equals(this.name, that.name) && Objects.equals(
                this.description, that.description) && Objects.equals(this.capacity, that.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._id, this.name, this.description, this.capacity);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Environment.class.getSimpleName() + "[", "]").add("id='" + this._id + "'").add(
                "name='" + this.name + "'").add("description='" + this.description + "'").add(
                "capacity=" + this.capacity).toString();
    }
}
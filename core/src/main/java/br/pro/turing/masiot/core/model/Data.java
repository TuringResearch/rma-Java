package br.pro.turing.masiot.core.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Data is a model that encapsulates a value of a resource in an instant of time.
 */
public class Data implements Serializable {

    /** Serial version ID for serialization. */
    private static final long serialVersionUID = 1L;

    /** ID. */
    private ObjectId _id;

    /** Instant of time when the data is created. */
    private LocalDateTime instant;

    /** Resource ID of this data. */
    private ObjectId resourceId;

    /** Value of this data. */
    private String value;

    /**
     * MongoDB constructor.
     */
    private Data() {
    }

    /**
     * Constructor.
     *
     * @param instant    {@link #instant}
     * @param resourceId {@link #resourceId}
     * @param value      {@link #value}
     */
    public Data(LocalDateTime instant, ObjectId resourceId, String value) {
        this._id = new ObjectId();
        this.instant = instant;
        this.resourceId = resourceId;
        this.value = value;
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
     * @return {@link #resourceId}
     */
    public ObjectId getResourceId() {
        return this.resourceId;
    }

    /**
     * @param resourceId {@link #resourceId}
     */
    public void setResourceId(ObjectId resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * @return {@link #value}
     */
    public String getValue() {
        return this.value;
    }

    /**
     * @param value {@link #value}
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Data data = (Data) o;
        return Objects.equals(this._id, data._id) && Objects.equals(this.instant, data.instant) && Objects.equals(
                this.resourceId, data.resourceId) && Objects.equals(this.value, data.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._id, this.instant, this.resourceId, this.value);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Data.class.getSimpleName() + "[", "]").add("id='" + this._id + "'").add(
                "instant=" + this.instant).add("resourceId='" + this.resourceId + "'").add("value='" + this.value + "'")
                .toString();
    }
}

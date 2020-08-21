package br.pro.turing.rma.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Data is a model that encapsulates a value of a resource in an instant of time.
 */
public class Data implements Serializable {

    /** Serial version ID for serialization. */
    private static final long serialVersionUID = 1L;

    /** Class name to be used by JSON strings. */
    @JsonIgnore
    private final String className = getClass().getName();

    /** ID. */
    private ObjectId _id;

    /** Instant of time when the data is created. */
    private Date instant;

    /** Device Name. */
    private String deviceName;

    /** Resource name of this data. */
    private String resourceName;

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
     * @param instant      {@link #instant}
     * @param deviceName   {@link #deviceName}
     * @param resourceName {@link #resourceName}
     * @param value        {@link #value}
     */
    public Data(LocalDateTime instant, String deviceName, String resourceName, String value) {
        this._id = new ObjectId();
        this.setInstant(instant);
        this.deviceName = deviceName;
        this.resourceName = resourceName;
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
        return Objects.equals(this.className, data.className) && Objects.equals(this._id, data._id) && Objects.equals(
                this.instant, data.instant) && Objects.equals(this.deviceName, data.deviceName) && Objects.equals(
                this.resourceName, data.resourceName) && Objects.equals(this.value, data.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.className, this._id, this.instant, this.deviceName, this.resourceName, this.value);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Data.class.getSimpleName() + "[", "]").add("className='" + this.className + "'")
                .add("_id=" + this._id).add("instant=" + this.instant).add("deviceName='" + this.deviceName + "'").add(
                        "resourceName='" + this.resourceName + "'").add("value='" + this.value + "'").toString();
    }
}

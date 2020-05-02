package br.pro.turing.masiot.core.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Resource is a Device element able to represent sensors and actuators. When resources works as a sensor, there cannot
 * be commands, but when resources works as actuators, they can have commands. The Resource data are collected by a
 * microcontroller through a port.
 */
public class Resource implements Serializable {

    /** Serial version ID for serialization. */
    private static final long serialVersionUID = 1L;

    /** ID. */
    private ObjectId _id;

    /**
     * Unique resource name. This name works as a user name and therefore there cannot be spaces on its content.
     * Besides, this name can be used by RML application to idetify a resource.
     */
    private String resourceName;

    /** Resource name. This name can have spaces and can have repetitions. */
    private String name;

    /** Description of this resource. */
    private String description;

    /** Port to access microcontroller. */
    private String port;

    /** Data unit of this resource. */
    private String dataUnit;

    /**
     * The wait time in millisecond between a value and another. This attribute defines the frequency of
     * collecting data indirectly.
     */
    private Integer waitTimeInMillis;

    /** Command list for the case that this resource is representing a actuator. */
    private List<Command> commandList = new ArrayList<>();

    /**
     * MongoDB constructor.
     */
    private Resource() {
    }

    /**
     * Constructor.
     *
     * @param resourceName     {@link #resourceName}
     * @param name             {@link #name}
     * @param description      {@link #description}
     * @param port             {@link #port}
     * @param dataUnit         {@link #dataUnit}
     * @param waitTimeInMillis {@link #waitTimeInMillis}
     */
    public Resource(String resourceName, String name, String description, String port, String dataUnit,
                    Integer waitTimeInMillis) {
        this._id = new ObjectId();
        this.resourceName = resourceName;
        this.name = name;
        this.description = description;
        this.port = port;
        this.dataUnit = dataUnit;
        this.waitTimeInMillis = waitTimeInMillis;
        this.setCommandList(null);
    }

    /**
     * Constructor.
     *
     * @param resourceName     {@link #resourceName}
     * @param name             {@link #name}
     * @param description      {@link #description}
     * @param port             {@link #port}
     * @param dataUnit         {@link #dataUnit}
     * @param waitTimeInMillis {@link #waitTimeInMillis}
     * @param commandList      {@link #commandList}
     */
    public Resource(String resourceName, String name, String description, String port, String dataUnit,
                    Integer waitTimeInMillis, List<Command> commandList) {
        this._id = new ObjectId();
        this.resourceName = resourceName;
        this.name = name;
        this.description = description;
        this.port = port;
        this.dataUnit = dataUnit;
        this.waitTimeInMillis = waitTimeInMillis;
        this.setCommandList(commandList);
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
     * @return {@link #port}
     */
    public String getPort() {
        return this.port;
    }

    /**
     * @param port {@link #port}
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return {@link #dataUnit}
     */
    public String getDataUnit() {
        return this.dataUnit;
    }

    /**
     * @param dataUnit {@link #dataUnit}
     */
    public void setDataUnit(String dataUnit) {
        this.dataUnit = dataUnit;
    }

    /**
     * @return {@link #waitTimeInMillis}
     */
    public Integer getWaitTimeInMillis() {
        return this.waitTimeInMillis;
    }

    /**
     * @param waitTimeInMillis {@link #waitTimeInMillis}
     */
    public void setWaitTimeInMillis(Integer waitTimeInMillis) {
        this.waitTimeInMillis = waitTimeInMillis;
    }

    /**
     * @return {@link #commandList}
     */
    public List<Command> getCommandList() {
        return this.commandList;
    }

    /**
     * @param commandList {@link #commandList}
     */
    public void setCommandList(List<Command> commandList) {
        this.commandList.clear();
        if (commandList != null) {
            this.commandList = commandList;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resource resource = (Resource) o;
        return Objects.equals(this._id, resource._id) && Objects.equals(this.resourceName, resource.resourceName)
                && Objects.equals(this.name, resource.name) && Objects.equals(this.description, resource.description)
                && Objects.equals(this.port, resource.port) && Objects.equals(this.waitTimeInMillis,
                resource.waitTimeInMillis) && Objects.equals(this.commandList, resource.commandList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._id, this.resourceName, this.name, this.description, this.port, this.waitTimeInMillis,
                this.commandList);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Resource.class.getSimpleName() + "[", "]").add("_id=" + this._id).add(
                "resourceName='" + this.resourceName + "'").add("name='" + this.name + "'").add(
                "description='" + this.description + "'").add("port='" + this.port + "'").add(
                "waitTimeInMillis='" + this.waitTimeInMillis + "'").add("commandList=" + this.commandList).toString();
    }
}

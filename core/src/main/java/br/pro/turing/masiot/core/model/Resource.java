package br.pro.turing.masiot.core.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectId _id;

    private String resourceName;

    private String name;

    private String description;

    private String port;

    private Integer waitTimeInMillis;

    private List<Command> commandList = new ArrayList<>();

    private Resource() {
    }

    public Resource(String resourceName, String name, String description, String port, Integer waitTimeInMillis) {
        this._id = new ObjectId();
        this.resourceName = resourceName;
        this.name = name;
        this.description = description;
        this.port = port;
        this.waitTimeInMillis = waitTimeInMillis;
        this.setCommandList(null);
    }

    public Resource(String resourceName, String name, String description, String port, Integer waitTimeInMillis,
                    List<Command> commandList) {
        this._id = new ObjectId();
        this.resourceName = resourceName;
        this.name = name;
        this.description = description;
        this.port = port;
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

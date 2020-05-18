package br.pro.turing.masiot.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * In the Device Layer of the Resource Management Architecture (RMA). (1) Devices are IoT Objects able of connecting,
 * registering and login in the RML when they starts. (2) Devices are parts of a specific environment, where they
 * informs its available resources. (3) Devices gathers data from its resources and send them to the RML. (4) Devices
 * receives actions from RML that must be executed by the device's actuators.
 */
public class Device implements Serializable {

    /** Serial version ID for serialization. */
    private static final long serialVersionUID = 1L;

    /** Class name to be used by JSON strings. */
    @JsonIgnore
    private final String className = getClass().getName();

    /**
     * Unique device name. This name works as a user name and therefore there cannot be spaces on its content.
     * Besides, this name can be used by RML application to idetify a devide.
     */
    @MongoId
    private String deviceName;

    /** UUID of this device in RML. */
    private String UUID;

    /** UUID of the gateway used by this device to access the RML. */
    private String gatewayUUID;

    /** Device name. This name can have spaces and can have repetitions. */
    private String name;

    /** Description of this device. */
    private String description;

    /**
     * Delay between a cycle and another. Consequently, this cycle represents the time between a RML communication
     * and another.
     */
    private Long cycleDelayInMillis;

    /** Resources list of this device. */
    private List<Resource> resourceList = new ArrayList<>();

    /** Environment ID of this device. */
    private ObjectId environmentId;

    /** State of connection. */
    private String connectionState = ConnectionState.OFFLINE.getState();

    /**
     * MongoDB constructor.
     */
    private Device() {
    }

    /**
     * Constructor.
     *
     * @param deviceName         {@link #deviceName}
     * @param name               {@link #name}
     * @param description        {@link #description}
     * @param cycleDelayInMillis {@link #cycleDelayInMillis}
     * @param resourceList       {@link #resourceList}
     */
    public Device(String deviceName, String name, String description, Long cycleDelayInMillis,
                  List<Resource> resourceList) {
        this.deviceName = deviceName;
        this.name = name;
        this.description = description;
        this.cycleDelayInMillis = cycleDelayInMillis;
        this.setResourceList(resourceList);
    }

    /**
     * @return {@link #serialVersionUID}
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @return {@link #UUID}
     */
    public String getUUID() {
        return this.UUID;
    }

    /**
     * @param UUID {@link #UUID}
     */
    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    /**
     * @return {@link #gatewayUUID}
     */
    public String getGatewayUUID() {
        return this.gatewayUUID;
    }

    /**
     * @param gatewayUUID {@link #gatewayUUID}
     */
    public void setGatewayUUID(String gatewayUUID) {
        this.gatewayUUID = gatewayUUID;
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
     * @return {@link #cycleDelayInMillis}
     */
    public Long getCycleDelayInMillis() {
        return this.cycleDelayInMillis;
    }

    /**
     * @param cycleDelayInMillis {@link #cycleDelayInMillis}
     */
    public void setCycleDelayInMillis(Long cycleDelayInMillis) {
        this.cycleDelayInMillis = cycleDelayInMillis;
    }

    /**
     * @return {@link #resourceList}
     */
    public List<Resource> getResourceList() {
        return this.resourceList;
    }

    /**
     * @param resourceList {@link #resourceList}
     */
    public void setResourceList(List<Resource> resourceList) {
        this.resourceList.clear();
        if (resourceList != null) {
            this.resourceList = resourceList;
        }
    }

    /**
     * @return {@link #environmentId}
     */
    public ObjectId getEnvironmentId() {
        return this.environmentId;
    }

    /**
     * @param environmentId {@link #environmentId}
     */
    public void setEnvironmentId(ObjectId environmentId) {
        this.environmentId = environmentId;
    }

    /**
     * @return {@link #connectionState}
     */
    public String getConnectionState() {
        return this.connectionState;
    }

    /**
     * @param connectionState {@link #connectionState}
     */
    public void setConnectionState(String connectionState) {
        this.connectionState = connectionState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Device device = (Device) o;
        return Objects.equals(this.UUID, device.UUID) && Objects.equals(
                this.gatewayUUID, device.gatewayUUID) && Objects.equals(this.deviceName, device.deviceName) && Objects
                .equals(this.name, device.name) && Objects.equals(this.description, device.description) && Objects
                .equals(this.resourceList, device.resourceList) && Objects.equals(this.environmentId,
                device.environmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.UUID, this.gatewayUUID, this.deviceName, this.name, this.description,
                this.resourceList, this.environmentId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Device.class.getSimpleName() + "[", "]").add(
                "UUID='" + this.UUID + "'").add("gatewayUUID='" + this.gatewayUUID + "'").add(
                "deviceName='" + this.deviceName + "'").add("name='" + this.name + "'").add(
                "description='" + this.description + "'").add("resourceList=" + this.resourceList).add(
                "environmentId=" + this.environmentId).toString();
    }
}

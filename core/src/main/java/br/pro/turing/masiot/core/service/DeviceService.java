package br.pro.turing.masiot.core.service;

import br.pro.turing.masiot.core.model.ConnectionState;
import br.pro.turing.masiot.core.model.Data;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.repository.DataRepository;
import br.pro.turing.masiot.core.repository.DeviceRepository;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Singleton Device service.
 */
public class DeviceService {

    /** Singleton instance. */
    private static DeviceService instance;

    /** Device repository. */
    private DeviceRepository deviceRepository;

    /** Data repository. */
    private DataRepository dataRepository;

    /**
     * Constructor.
     */
    private DeviceService() {
        this.deviceRepository = DeviceRepository.getInstance();
        this.dataRepository = DataRepository.getInstance();
    }

    /**
     * @return {@link #instance}
     */
    public static DeviceService getInstance() {
        if (DeviceService.instance == null) {
            DeviceService.instance = new DeviceService();
        }
        return instance;
    }

    /**
     * Find connection state by device.
     *
     * @param device Device.
     * @return Connection state.
     */
    public ConnectionState findCurrentConnectionState(Device device) {
        for (Resource resource : device.getResourceList()) {
            final List<Data> byResourceAndGte = this.dataRepository.findByResourceAndGte(device, resource,
                    LocalDateTime.now().plusSeconds(-10));
            if (byResourceAndGte != null & !byResourceAndGte.isEmpty()) {
                return ConnectionState.ONLINE;
            }
        }
        return ConnectionState.OFFLINE;
    }

    /**
     * Save a device.
     *
     * @param device Device.
     * @return Device saved.
     */
    public Device save(Device device) {
        return this.deviceRepository.save(device);
    }

    public Iterable<Device> saveAll(List<Device> deviceList) {
        return this.deviceRepository.saveAll(deviceList);
    }

    public List<Device> findAll() {
        return this.deviceRepository.findAll();
    }

    /**
     * Find Device bu deviceName.
     *
     * @param deviceName Device name.
     * @return Device found.
     */
    public Device findById(String deviceName) {
        return this.deviceRepository.findById(deviceName);
    }

    /**
     * Find device by command ID.
     *
     * @param command Command ID.
     * @return Device found.
     */
    public Device findByCommand(String command) {
        return this.deviceRepository.findByCommand(command);
    }
}

package br.pro.turing.masiot.core.service;

import br.pro.turing.masiot.core.model.Data;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.repository.DataRepository;
import br.pro.turing.masiot.core.repository.DeviceRepository;

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
     * Find Device by deviceName.
     *
     * @param deviceName Device name.
     * @return Device found.
     */
    public Device findById(String deviceName) {
        return this.deviceRepository.findById(deviceName);
    }

    /**
     * Find device by name.
     *
     * @param deviceName Device name.
     * @return Device.
     */
    public Device findByDeviceName(String deviceName) {
        return this.deviceRepository.findByDeviceName(deviceName);
    }

    /**
     * Update the date of the last update of this device.
     *
     * @param deviceName Device name.
     * @return Device.
     */
    public void updateLast(String deviceName) {
        this.deviceRepository.updateLast(deviceName);
    }
}

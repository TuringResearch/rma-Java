package br.pro.turing.rma.core.service;

import br.pro.turing.rma.core.model.Device;
import br.pro.turing.rma.core.repository.DataRepository;
import br.pro.turing.rma.core.repository.DeviceRepository;

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

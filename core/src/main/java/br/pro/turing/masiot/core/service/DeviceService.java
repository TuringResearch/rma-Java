package br.pro.turing.masiot.core.service;

import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.repository.DeviceRepository;
import org.bson.types.ObjectId;

public class DeviceService {
    private static DeviceService instance;

    private DeviceRepository deviceRepository;

    private DeviceService() {
        this.deviceRepository = DeviceRepository.getInstance();
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

    public Device save(Device device) {
        return this.deviceRepository.save(device);
    }

    public Device findByDeviceName(String deviceName) {
        return this.deviceRepository.findByDeviceName(deviceName);
    }

    public Device findByCommandId(ObjectId commandId) {
        return this.deviceRepository.findByCommandId(commandId);
    }
}

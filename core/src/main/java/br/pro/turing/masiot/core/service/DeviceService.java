package br.pro.turing.masiot.core.service;

import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.repository.DeviceRepository;

public class DeviceService {
    private static DeviceService instance;

    private DeviceRepository actionRepository;

    private DeviceService() {
        this.actionRepository = DeviceRepository.getInstance();
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
        return this.actionRepository.save(device);
    }

    public Device findByDeviceName(String deviceName) {
        return this.actionRepository.findByDeviceName(deviceName);
    }
}

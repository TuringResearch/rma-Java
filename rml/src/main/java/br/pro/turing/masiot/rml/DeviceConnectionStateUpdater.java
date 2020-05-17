package br.pro.turing.masiot.rml;

import br.pro.turing.masiot.core.model.ConnectionState;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.service.ServiceManager;
import br.pro.turing.masiot.core.utils.LoggerUtils;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DeviceConnectionStateUpdater implements Runnable {

    /** Logger. */
    private static final Logger LOGGER = LoggerUtils.initLogger(DeviceConnectionStateUpdater.class.getClassLoader()
                    .getResourceAsStream("br/pro/turing/masiot/rml/rml.logging.properties"),
            DeviceConnectionStateUpdater.class.getSimpleName());

    private Map<ObjectId, LocalDateTime> deviceCurrentUpdateMap;

    private Map<ObjectId, Device> deviceMap;

    public DeviceConnectionStateUpdater() {
        this.deviceCurrentUpdateMap = new HashMap<>();
        this.deviceMap = new HashMap<>();
    }

    @Override
    public void run() {
        while (true) {
            final long t1 = System.currentTimeMillis();

            List<Device> devicesChanged = new ArrayList<>();
            this.deviceCurrentUpdateMap.forEach((objectId, localDateTime) -> {
                Device device = this.deviceMap.get(objectId);
                if (localDateTime.until(LocalDateTime.now(), ChronoUnit.MILLIS) > device.getCycleDelayInMillis()
                        && device.getConnectionState().equals(ConnectionState.ONLINE.getState())) {
                    device.setConnectionState(ConnectionState.OFFLINE.getState());
                    devicesChanged.add(device);
                } else if (localDateTime.until(LocalDateTime.now(), ChronoUnit.MILLIS) <= device.getCycleDelayInMillis()
                        && device.getConnectionState().equals(ConnectionState.OFFLINE.getState())) {
                    device.setConnectionState(ConnectionState.ONLINE.getState());
                    devicesChanged.add(device);
                }
            });
            if (!devicesChanged.isEmpty()) {
                ServiceManager.getInstance().deviceService.saveAll(devicesChanged);
            }

            final long deltaTime = System.currentTimeMillis() - t1;
            try {
                Thread.sleep(deltaTime < 5000 ? 5000 - deltaTime : 0);
            } catch (InterruptedException e) {
                LOGGER.severe(e.getMessage());
            }
        }
    }

    public void pingDeviceByResourceId(ObjectId resourceId) {
        this.deviceMap.values().stream().filter(device1 -> device1.getResourceList().stream().map(Resource::get_id)
                .anyMatch(objectId -> objectId.equals(resourceId))).findFirst().ifPresent(this::pingDevice);
    }

    public void pingDevice(Device device) {
        if (!this.deviceMap.containsKey(device.get_id())) {
            this.deviceMap.put(device.get_id(), device);
            this.deviceCurrentUpdateMap.put(device.get_id(), LocalDateTime.now());
        } else {
            this.deviceCurrentUpdateMap.replace(device.get_id(), LocalDateTime.now());
        }
    }
}

package br.pro.turing.rma.core.repository;

import br.pro.turing.rma.core.model.Device;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DeviceRepository implements MongoRepository<Device, ObjectId> {

    private static DeviceRepository instance;

    private MongoCollection collection;

    private DeviceRepository() {
        this.collection = Connector.getInstance().getJongo().getCollection("device");
    }

    /**
     * @return {@link #instance}
     */
    public static DeviceRepository getInstance() {
        if (DeviceRepository.instance == null) {
            DeviceRepository.instance = new DeviceRepository();
        }
        return DeviceRepository.instance;
    }

    @Override
    public <S extends Device> S save(S var1) {
        this.collection.save(var1);
        return var1;
    }

    @Override
    public <S extends Device> Iterable<S> saveAll(Iterable<S> var1) {
        var1.forEach(this::save);
        return var1;
    }

    @Override
    public Optional<Device> findById(ObjectId var1) {
        return Optional.of(this.collection.findOne(var1).as(Device.class));
    }

    @Override
    public boolean existsById(ObjectId var1) {
        return this.collection.findOne(var1).as(Device.class) != null;
    }

    @Override
    public List<Device> findAll() {
        final MongoCursor<Device> commandCursor = collection.find().as(Device.class);
        List<Device> commandList = new ArrayList<>();
        commandCursor.forEach(commandList::add);
        return commandList;
    }

    @Override
    public long count() {
        return this.collection.count();
    }

    @Override
    public void deleteById(ObjectId var1) {
        this.collection.remove(var1);
    }

    @Override
    public void delete(Device var1) {

    }

    @Override
    public void deleteAll(Iterable<? extends Device> var1) {

    }

    @Override
    public void deleteAll() {

    }

    public Device findById(String id) {
        return collection.findOne("{_id: '" + id + "'}").as(Device.class);
    }

    public Device findByDeviceName(String deviceName) {
        return collection.findOne("{deviceName: '" + deviceName + "'}").as(Device.class);
    }

    public Device findByCommand(String command) {
        return collection.findOne("{'resourceList.commandList': {$elemMatch: {_id: #}}}", command).as(Device.class);
    }

    /**
     * Update the date of the last update of this device.
     *
     * @param deviceName Device name.
     */
    public void updateLast(String deviceName) {
        Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        collection.update("{_id: #}", deviceName).with("{$set:{lastUpdate: #}}",
                date);
    }
}

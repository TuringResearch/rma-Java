package br.pro.turing.rma.core.repository;

import br.pro.turing.rma.core.model.Data;
import br.pro.turing.rma.core.model.Device;
import br.pro.turing.rma.core.model.Resource;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DataRepository implements MongoRepository<Data, ObjectId> {

    private static DataRepository instance;

    private MongoCollection collection;

    private DataRepository() {
        this.collection = Connector.getInstance().getJongo().getCollection("data");
    }

    /**
     * @return {@link #instance}
     */
    public static DataRepository getInstance() {
        if (DataRepository.instance == null) {
            DataRepository.instance = new DataRepository();
        }
        return DataRepository.instance;
    }

    @Override
    public <S extends Data> S save(S var1) {
        final WriteResult save = this.collection.save(var1);
        var1.set_id((ObjectId) save.getUpsertedId());
        return var1;
    }

    @Override
    public <S extends Data> Iterable<S> saveAll(Iterable<S> var1) {
        var1.forEach(this::save);
        return var1;
    }

    @Override
    public Optional<Data> findById(ObjectId var1) {
        return Optional.of(this.collection.findOne(var1).as(Data.class));
    }

    @Override
    public boolean existsById(ObjectId var1) {
        return this.collection.findOne(var1).as(Data.class) != null;
    }

    @Override
    public List<Data> findAll() {
        final MongoCursor<Data> commandCursor = collection.find().as(Data.class);
        List<Data> commandList = new ArrayList<>();
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
    public void delete(Data var1) {
        this.collection.remove(var1.get_id());
    }

    @Override
    public void deleteAll(Iterable<? extends Data> var1) {

    }

    @Override
    public void deleteAll() {

    }

    public List<Data> findByResourceAndGte(Device device, Resource resource, LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        final MongoCursor<Data> dataCursor =
                collection.find("{'deviceName': #, 'resourceName': #, 'instant': {'$gte': #}}",
                        device.getDeviceName(), resource.getResourceName(), date).as(Data.class);
        List<Data> dataList = new ArrayList<>();
        dataCursor.forEach(dataList::add);
        return dataList;
    }
}


package br.pro.turing.rma.core.repository;

import br.pro.turing.rma.core.model.Environment;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnvironmentRepository implements MongoRepository<Environment, ObjectId> {

    private static EnvironmentRepository instance;

    private MongoCollection collection;

    private EnvironmentRepository() {
        this.collection = Connector.getInstance().getJongo().getCollection("environment");
    }

    /**
     * @return {@link #instance}
     */
    public static EnvironmentRepository getInstance() {
        if (EnvironmentRepository.instance == null) {
            EnvironmentRepository.instance = new EnvironmentRepository();
        }
        return EnvironmentRepository.instance;
    }

    @Override
    public <S extends Environment> S save(S var1) {
        final WriteResult save = this.collection.save(var1);
        var1.set_id((ObjectId) save.getUpsertedId());
        return var1;
    }

    @Override
    public <S extends Environment> Iterable<S> saveAll(Iterable<S> var1) {
        var1.forEach(this::save);
        return var1;
    }

    @Override
    public Optional<Environment> findById(ObjectId var1) {
        return Optional.of(this.collection.findOne(var1).as(Environment.class));
    }

    @Override
    public boolean existsById(ObjectId var1) {
        return this.collection.findOne(var1).as(Environment.class) != null;
    }

    @Override
    public List<Environment> findAll() {
        final MongoCursor<Environment> commandCursor = collection.find().as(Environment.class);
        List<Environment> commandList = new ArrayList<>();
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
    public void delete(Environment var1) {
        this.collection.remove(var1.get_id());
    }

    @Override
    public void deleteAll(Iterable<? extends Environment> var1) {

    }

    @Override
    public void deleteAll() {

    }

}


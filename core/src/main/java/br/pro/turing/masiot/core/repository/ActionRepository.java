package br.pro.turing.masiot.core.repository;

import br.pro.turing.masiot.core.model.Action;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActionRepository implements MongoRepository<Action, ObjectId> {

    private static ActionRepository instance;

    private MongoCollection collection;

    private ActionRepository() {
        this.collection = Connector.getInstance().getJongo().getCollection("action");
    }

    /**
     * @return {@link #instance}
     */
    public static ActionRepository getInstance() {
        if (ActionRepository.instance == null) {
            ActionRepository.instance = new ActionRepository();
        }
        return ActionRepository.instance;
    }

    @Override
    public <S extends Action> S save(S var1) {
        final WriteResult save = this.collection.save(var1);
        var1.set_id((ObjectId) save.getUpsertedId());
        return var1;
    }

    @Override
    public <S extends Action> Iterable<S> saveAll(Iterable<S> var1) {
        var1.forEach(this::save);
        return var1;
    }

    @Override
    public Optional<Action> findById(ObjectId var1) {
        return Optional.of(this.collection.findOne(var1).as(Action.class));
    }

    @Override
    public boolean existsById(ObjectId var1) {
        return this.collection.findOne(var1).as(Action.class) != null;
    }

    @Override
    public List<Action> findAll() {
        final MongoCursor<Action> commandCursor = collection.find().as(Action.class);
        List<Action> commandList = new ArrayList<>();
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
    public void delete(Action var1) {
        this.collection.remove(var1.get_id());
    }

    @Override
    public void deleteAll(Iterable<? extends Action> var1) {

    }

    @Override
    public void deleteAll() {

    }

}

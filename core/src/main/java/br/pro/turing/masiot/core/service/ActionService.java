package br.pro.turing.masiot.core.service;

import br.pro.turing.masiot.core.model.Action;
import br.pro.turing.masiot.core.repository.ActionRepository;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;

public class ActionService {
    private static ActionService instance;

    private ActionRepository actionRepository;

    private ActionService() {
        this.actionRepository = ActionRepository.getInstance();
    }

    /**
     * @return {@link #instance}
     */
    public static ActionService getInstance() {
        if (ActionService.instance == null) {
            ActionService.instance = new ActionService();
        }
        return instance;
    }

    public Action save(Action action) {
        return this.actionRepository.save(action);
    }
}

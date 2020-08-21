package br.pro.turing.rma.core.service;

import br.pro.turing.rma.core.model.Action;
import br.pro.turing.rma.core.repository.ActionRepository;

/**
 * Singleton Action service.
 */
public class ActionService {

    /** Singleton instance. */
    private static ActionService instance;

    /** Action repository. */
    private ActionRepository actionRepository;

    /**
     * Constructor.
     */
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

    /**
     * Save action.
     *
     * @param action Action.
     * @return Action saved.
     */
    public Action save(Action action) {
        return this.actionRepository.save(action);
    }
}

package br.pro.turing.masiot.core.service;

import br.pro.turing.masiot.core.repository.ActionRepository;

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
}

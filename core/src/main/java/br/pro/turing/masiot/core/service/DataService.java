package br.pro.turing.masiot.core.service;

import br.pro.turing.masiot.core.repository.DataRepository;

public class DataService {
    private static DataService instance;

    private DataRepository dataRepository;

    private DataService() {
        this.dataRepository = DataRepository.getInstance();
    }

    /**
     * @return {@link #instance}
     */
    public static DataService getInstance() {
        if (DataService.instance == null) {
            DataService.instance = new DataService();
        }
        return instance;
    }
}

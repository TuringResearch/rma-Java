package br.pro.turing.masiot.core.service;

import br.pro.turing.masiot.core.model.Data;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.repository.DataRepository;

/**
 * Singleton Data service.
 */
public class DataService {

    /** Singleton instance. */
    private static DataService instance;

    /** Data repository. */
    private DataRepository dataRepository;

    /**
     * Constructor.
     */
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

    /**
     * Save a set of Data.
     *
     * @param dataIterable Set of Data.
     * @return Set of Data saved.
     */
    public Iterable<Data> saveAll(Iterable<Data> dataIterable) {
        return this.dataRepository.saveAll(dataIterable);
    }

    public Data findLastByResource(Resource resource) {
        return this.dataRepository.findLastByResource(resource);
    }
}

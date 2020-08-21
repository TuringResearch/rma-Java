package br.pro.turing.rma.core.service;

import br.pro.turing.rma.core.model.Data;
import br.pro.turing.rma.core.model.Device;
import br.pro.turing.rma.core.model.Resource;
import br.pro.turing.rma.core.repository.DataRepository;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * Find a data list by resource and time.
     *
     * @param resource      Resource
     * @param localDateTime Local date and time.
     * @return Data list.
     */
    public List<Data> findByResourceAndGte(Device device, Resource resource, LocalDateTime localDateTime) {
        return this.dataRepository.findByResourceAndGte(device, resource, localDateTime);
    }
}

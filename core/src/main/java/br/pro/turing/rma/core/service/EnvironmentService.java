package br.pro.turing.rma.core.service;

import br.pro.turing.rma.core.repository.EnvironmentRepository;

/**
 * Singleton Environment service.
 */
public class EnvironmentService {

    /** Singleton instance. */
    private static EnvironmentService instance;

    /** Environment repository. */
    private EnvironmentRepository environmentRepository;

    /**
     * Constructor.
     */
    private EnvironmentService() {
        this.environmentRepository = EnvironmentRepository.getInstance();
    }

    /**
     * @return {@link #instance}
     */
    public static EnvironmentService getInstance() {
        if (EnvironmentService.instance == null) {
            EnvironmentService.instance = new EnvironmentService();
        }
        return instance;
    }
}

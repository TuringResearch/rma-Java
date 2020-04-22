package br.pro.turing.masiot.core.service;

import br.pro.turing.masiot.core.repository.EnvironmentRepository;

public class EnvironmentService {
    private static EnvironmentService instance;

    private EnvironmentRepository environmentRepository;

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

package br.pro.turing.masiot.core.service;

public class ServiceManager {
    private static ServiceManager instance;

    public ActionService actionService;

    public DataService dataService;

    public DeviceService deviceService;

    public EnvironmentService environmentService;

    private ServiceManager() {
        this.actionService = ActionService.getInstance();
        this.dataService = DataService.getInstance();
        this.deviceService = DeviceService.getInstance();
        this.environmentService = EnvironmentService.getInstance();
    }

    public static ServiceManager getInstance() {
        if (ServiceManager.instance == null) {
            ServiceManager.instance = new ServiceManager();
        }
        return ServiceManager.instance;
    }
}

package br.pro.turing.rma.core.service;

/**
 * Service Manager singleton. The unique instance of this class must be called to get access of all services of the
 * core.
 */
public class ServiceManager {

    /** Intance of this singleton. */
    private static ServiceManager instance;

    /** {@link ActionService} object. */
    public ActionService actionService;

    /** {@link DataService} object. */
    public DataService dataService;

    /** {@link DeviceService} object. */
    public DeviceService deviceService;

    /** {@link EnvironmentService} object. */
    public EnvironmentService environmentService;

    /** {@link JsonService} object. */
    public JsonService jsonService;

    /**
     * Constructor.
     */
    private ServiceManager() {
        this.actionService = ActionService.getInstance();
        this.dataService = DataService.getInstance();
        this.deviceService = DeviceService.getInstance();
        this.environmentService = EnvironmentService.getInstance();
        this.jsonService = JsonService.getInstance();
    }

    /**
     * @return {@link #instance}
     */
    public static ServiceManager getInstance() {
        if (ServiceManager.instance == null) {
            ServiceManager.instance = new ServiceManager();
        }
        return ServiceManager.instance;
    }
}

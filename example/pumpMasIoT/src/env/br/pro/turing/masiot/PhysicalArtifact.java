package br.pro.turing.masiot;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.pro.turing.javino.Javino;
import br.pro.turing.masiot.core.model.Action;
import br.pro.turing.masiot.core.model.Data;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.devicelayer.IoTObject;
import cartago.Artifact;

public abstract class PhysicalArtifact extends Artifact {
	
	private Javino javino;
	
	private String port;
	
	private int attemptsAfterFailure;
	
	private int waitTimeout;
	
	private boolean javinoIsBusy = false;
	
    public PhysicalArtifact() {
    	super();
        this.javino = new Javino();
        this.port = definePort();
        if (this.port == null) {
        	this.port = "";
        }
        this.attemptsAfterFailure = defineAttemptsAfterFailure();
        this.waitTimeout = defineWaitTimeout();
    }
    
    protected abstract String definePort();
    
    protected abstract int defineAttemptsAfterFailure();
    
    protected abstract int defineWaitTimeout();
    
    public void runAsIoTObject(String gatewayIP, int gatewayPort) {
    	try {
    		String configFilePath = PhysicalArtifact.class.getClassLoader().getResource("br/pro/turing/masiot/deviceConfiguration" + ".json").getFile();
        	Device artifactDevice = IoTObject.buildDeviceByConfigFile(configFilePath);
        	final IoTObject ioTObject = new IoTObject(artifactDevice) {
				@Override
				protected void onAction(Action action) {
					PhysicalArtifact.this.send(action.getCommand());
				}
				
				@Override
				protected ArrayList<Data> buildDataBuffer() {
					ArrayList<Data> dataList = new ArrayList<Data>(); 
					
					// Mapeando recursos com os textos que representam valores.
					Map<Resource, String> resourceValueMap = new HashMap<Resource, String>();
					for (Resource r : this.getDevice().getResourceList()) {
						final LocalDateTime now = LocalDateTime.now();
	                    String value = IoTObject.DATE_TIME_FORMATTER.format(now) + IoTObject.SPLIT_TIME;
						resourceValueMap.put(r, value);
					}
                    
					// Lendo do microcontrolador e associando as medidas aos recursos.
					String resourcesData = PhysicalArtifact.this.getResourceData();
					String[] resourcesDataArray = resourcesData.split(";");
                    for (int i = 0; i < resourcesDataArray.length; i++) {
						String[] keyValueArray = resourcesDataArray[i].split("\\(");
						final String resourceName = keyValueArray[0];
						String v = keyValueArray[1].replace(")", "");
						Resource r = resourceValueMap.keySet().stream().filter(r1 -> resourceName.equals(r1.getResourceName())).findFirst().orElse(null);
						if (resourceValueMap.containsKey(r)) {
							resourceValueMap.replace(r, resourceValueMap.get(r) + v + IoTObject.SPLIT_VALUE);
						}
					}
                    
                    // Removendo o último SPLIT_VALUE de cada recurso.
                    for (Resource r : this.getDevice().getResourceList()) {
                    	resourceValueMap.computeIfAbsent(r, k -> resourceValueMap.get(r).substring(0, resourceValueMap.get(k).length() - 1));
                    	dataList.addAll(this.extractValue(r, resourceValueMap.get(r)));
                    }
                    
                    return dataList;
				}
			};
        	ioTObject.connect(gatewayIP, gatewayPort);
        	log("Connected in RML as an IoT device");
    	} catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
    }
    
    public String read() {
    	while (javinoIsBusy) {
    		try {
    			Thread.sleep(10);
    		} catch (InterruptedException e) {
    		}
		}
    	javinoIsBusy = true;
    	boolean hasData = this.javino.listenArduino(this.port);
    	String result = hasData ? this.javino.getData() : "";
    	javinoIsBusy = false;
    	return result;
    	
    }
    
    public void send(String message) {
    	while (javinoIsBusy) {
    		try {
    			Thread.sleep(10);
    		} catch (InterruptedException e) {
    		}
		}
    	javinoIsBusy = true;
    	int failureCount = 0;
    	while (!this.javino.sendCommand(this.port, message) && failureCount < this.attemptsAfterFailure) {
            try {
                Thread.sleep(this.waitTimeout);
                failureCount++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    	javinoIsBusy = false;
    }
    
    public String getResourceData() {
    	while (javinoIsBusy) {
    		try {
    			Thread.sleep(10);
    		} catch (InterruptedException e) {
    		}
		}
    	javinoIsBusy = true;
    	boolean hasData = this.javino.requestData(this.port, "getResourceData");
    	String result = hasData ? this.javino.getData() : "";
    	javinoIsBusy = false;
    	return result;
    }
}

package br.pro.turing;

import br.pro.turing.javino.Javino;
import cartago.*;

public abstract class PhysicalArtifact extends Artifact {
	
	private Javino javino;
	
	private String port;
	
	private int attemptsAfterFailure;
	
	private int waitTimeout;
	
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
    
    public String read() {
    	boolean hasData = javino.listenArduino(port);
    	return hasData ? javino.getData() : "";
    }
    
    public void send(String message) {
    	int failureCount = 0;
    	while (!javino.sendCommand(port, message) && failureCount < this.attemptsAfterFailure) {
            try {
                Thread.sleep(this.waitTimeout);
                failureCount++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String requestData(String message) {
    	boolean hasData = javino.requestData(port, message);
    	return hasData ? javino.getData() : "";
    }
}


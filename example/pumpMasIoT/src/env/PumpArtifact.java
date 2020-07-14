import br.pro.turing.masiot.PhysicalArtifact;
import cartago.OPERATION;
import cartago.ObsProperty;

public class PumpArtifact extends PhysicalArtifact {

    void init() {
    	super.runAsIoTObject("127.0.0.1", 5500);
    	super.defineObsProperty("temperature", 0);
    }

    @OPERATION
    void readTemperatureValue() {
//    	String temperatureString = super.getResourceData();
//    	temperatureString = temperatureString.split("\\(")[1].replace(");", "");
//    	ObsProperty prop = getObsProperty("temperature");
//    	prop.updateValue(Float.parseFloat(temperatureString));
    }
    
    @OPERATION
    void turnOnMotor() {
        super.send("on");
    }

    @OPERATION
    void turnOffMotor() {
    	super.send("off");
    }

    @OPERATION
    void blockMotor() {
    	super.send("cannotOp");
    }

    @OPERATION
    void freeMotor() {
    	super.send("canOp");
    }

	@Override
	protected String definePort() {
		return "COM6";
	}

	@Override
	protected int defineAttemptsAfterFailure() {
		return 3;
	}

	@Override
	protected int defineWaitTimeout() {
		return 1000;
	}
}


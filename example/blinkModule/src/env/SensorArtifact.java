import br.pro.turing.PhysicalArtifact;
import cartago.*;

public class SensorArtifact extends PhysicalArtifact {
	void init() {
		defineObsProperty("luminosity", 0);
	}
	
	@OPERATION
	void percepts() {
		String data = requestData("getPercepts");
		String[] datas = data.replace(");", "").split("\\(");
		ObsProperty prop = getObsProperty(datas[0]);
		prop.updateValue(Integer.parseInt(datas[1]));
	}
	@Override
	protected String definePort() {
		return "COM4";
	}
	@Override
	protected int defineAttemptsAfterFailure() {
		return 0;
	}
	@Override
	protected int defineWaitTimeout() {
		return 0;
	}
}


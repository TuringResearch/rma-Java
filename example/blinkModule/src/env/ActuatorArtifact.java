import br.pro.turing.PhysicalArtifact;
import cartago.*;

public class ActuatorArtifact extends PhysicalArtifact {
	void init() {
	}
	@OPERATION
	void turnOnLight() {
		send("turnOnLight");
	}
	@OPERATION
	void turnOffLight() {
		send("turnOffLight");
	}
	@Override
	protected String definePort() {
		return "COM5";
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


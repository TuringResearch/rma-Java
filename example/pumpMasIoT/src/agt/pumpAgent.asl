!start.

+!start : true <- 
	!operate.
	
+!operate <-
	.print("Starting the Engine");
	turnOnMotor;
	readTemperatureValue;
	.wait(5000);
	
	.print("Stopping the Engine");
	turnOffMotor;
	readTemperatureValue;
	.wait(3000);
	
	.print("Start the Engine");
	turnOnMotor;
	readTemperatureValue;
	.wait(5000);
	
	.print("Blocking the Engine");
	blockMotor;
	readTemperatureValue;
	.wait(3000);
	
	.print("Unlocking the Engine");
	freeMotor;
	readTemperatureValue;
	.wait(3000);
	
	!operate.
	
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

///{ include("$moiseJar/asl/org-obedient.asl") }

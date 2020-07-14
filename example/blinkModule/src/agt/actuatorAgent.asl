!start.

/* Plans */

+!turnOnLight : true <- 
	turnOnLight.

+!turnOffLight : true <- 
	turnOffLight.

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organization
//{ include("$moiseJar/asl/org-obedient.asl") }

